package cn.mldn.mysql.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.mldn.mysql.dao.IUserDAO;
import cn.mldn.mysql.dbc.JdbcUtil;
import cn.mldn.mysql.exception.DAOException;
import cn.mldn.mysql.factory.ServiceFactory;
import cn.mldn.mysql.vo.User;
import cn.mldn.mysql.vo.Userinfo;



public class UserDAOImpl implements IUserDAO{
	
	Connection con = null;
	List<User> userList = null;
	List<Userinfo> userinfoList = null;
	Integer yzm = 0;  // 验证码

	// 该方法返回一个boolean值，true为存在，false为不存在，前端提供String userName(用户名)参数。
	//  判断该用户是否存在
	public boolean selectUserExist(String userName) {
		boolean flag = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) from website.user where user.userName = ?";
			ps = JdbcUtil.getPreparedStatement(sql,userName);
			rs = ps.executeQuery();
			while(rs.next()) 
				if(rs.getInt(1) > 0)
				flag = true; // 判断userID是否存在
		} catch (Exception e) {
					System.out.println("异常处理报错，位于UserDAOImpl.java中selectUIDexist");
					e.printStackTrace();
				
		} finally {
			JdbcUtil.closeConnection(JdbcUtil.getConnection(),ps, rs);
		}
		return flag;
	}

	
	/*
	 * 该方法返回一个boolean值，true为不存在且数据已保存，false为存在
	 * 传入参数：User user, Userinfo userinfo
	作用：
		将数据暂时保存到集合userList和userinfoList中
	 */
	public boolean doInsertUser01(User user, Userinfo userinfo) {
		boolean flag = false;
		userList = new ArrayList<User>(); //  暂时存放个人信息
		userinfoList = new ArrayList<Userinfo>();
		try {
			flag = !selectUserExist(user.getUserName());  // 再次查询该用户是否已存在，flag为true时，该用户存在，否则相反
			if(flag) {   
				User usersave = new User();
				usersave.setUserName(user.getUserName());
				usersave.setUserPassword(user.getUserPassword());
				usersave.setUserEmail(user.getUserEmail());
				userList.add(usersave);
				Userinfo userinfosave = new Userinfo();
				userinfosave.setUserName(userinfo.getUserName());
				userinfosave.setUserSex(userinfo.getUserSex());
				userinfosave.setUserAvatarPath(userinfo.getUserAvatarPath());
				userinfosave.setUserBirthday(userinfo.getUserBirthday());
				userinfosave.setUserTel(userinfo.getUserTel());
				userinfoList.add(userinfosave);
			}
		}catch(Exception e) {
			try {
				System.out.println("异常处理报错，位于UserDAOImpl.java中doInsertUser01");
				throw new DAOException(e.getMessage(),e);
			} catch (DAOException e1) {
				System.out.println("异常处理报错，位于UserDAOImpl.java中doInsertUser01");
				e1.printStackTrace();
			}
		}
		return flag;
	}
	
	
	
	/*
	 * UserDAOImpl.java.doInsertUser02()
	 * 该方法返回一个boolean值，true为验证码正确，false为不正确
	 * 前端提供参数：null 
	 * 作用：将数据存入mysql中
	 * mysql中的存储过程p_addUser的作用：					
	 		* 创建用户信息
	 		* 增加user信息,后增加userinfo表
	 		* 增加成功返回1，不成功返回0，执行异常返回-1
	 		* 	in userName varchar(45),
 				in userPassword varchar(45),
 				in userEmail varchar(45),
 				in userinfoName varchar(10),
 				in userSex varchar(1),
 				in userAvatarPath varchar(60),
 				in userBirthday date,
 				in userTel varchar(11),
 				out n integer
	 */
	public boolean doInsertUser02() {
		con = JdbcUtil.getConnection();
		CallableStatement ps = null;
			try {
				String sql = "{call p_addUser(?,?,?,?,?,?,?,?,?)}";
				ps = JdbcUtil.getPreparedStatementCall(sql, userList.get(0).getUserName(),
						userList.get(0).getUserPassword(),userList.get(0).getUserEmail(),
						userinfoList.get(0).getUserName(),userinfoList.get(0).getUserSex(),
						userinfoList.get(0).getUserAvatarPath(),userinfoList.get(0).getUserBirthday(),
						userinfoList.get(0).getUserTel());
				int i = ps.executeUpdate();
				int n = Integer.parseInt(ps.getString(9));
				if(i > 0 || n >= -1) {
					switch(n) {
					case -1: System.out.println("mysql的存储过程p_addUser有问题"); break;
					case 0: System.out.println("该用户已存在"); break;
					case 1: System.out.println("添加成功"); return true;
					default: System.out.println("UserDAOImpl.java中doInsertUser02方法出现未知故障");
					}
				}	
			}catch(Exception e) {
				try {
					System.out.println("异常处理报错，位于UserDAOImpl.java中");
					throw new DAOException(e.getMessage(),e);
				} catch (DAOException e1) {
					System.out.println("UserDAOImpl中出现问题！！！");
					e1.printStackTrace();
				}
			}finally {
				userList = null;
				userinfoList = null;
				if(ps != null)
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}finally {
						if(con != null)
							try {
								con.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
					}
			}
			
		return false;
	}
	

	
	/*
	 * 获取验证码是否正确
	 */
	public boolean getEmailVeracity(String userEmail, Integer yzm) {
		if(yzm == this.yzm && userList.get(1).getUserEmail() == userEmail) return true;
		return false;
	}
	
	/*
	 * 发送邮箱
	 */
	public boolean sendEmail(String userEmail) {
		return false;
	}
	
	
	
	/*
	 * 该方法返回一个Userinfo值，
	 * 前端提供参数：String userName(用户名), String userPassword(MD5加密过后的密文)。
	 * 作用：将前端传过来的用户名和密码进行验证,   查找用户信息，获取用户信息,   
	 * 注意： 在此方法执行前最好先查询是否该用户呢称是否存在，即先调用public boolean selectUIDexist(String uName)方法
	 */ 

	public Userinfo findByUserName(String userName) {
		Userinfo userinfo = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			userinfo = new Userinfo();
			String sql = "select userName, userSex, userAvatarPath, userBirthday, userTel from userinfo where userinfo.userID = (select userID from website.user where userName = ?)";
			ps = JdbcUtil.getPreparedStatement(sql, userName);
			rs = ps.executeQuery();
			userinfo = JdbcUtil.addUserinfo(rs, userinfo);  // 增加用户信息
		}catch(SQLException e) {
			System.out.println("异常处理报错，位于UserDAOImpl.java中的findByUserName");
			e.printStackTrace();
		}finally {
			JdbcUtil.closeConnection(JdbcUtil.getConnection(),ps, rs);
		}
			return userinfo;
		
	}
	
	/*
	 * 该方法返回一个boolean值，true为已经删除，false为删除不成功
	 * 前端提供参数：String userName(用户名)。
	 * 作用：以前端传过来的用户名来查询该用户，最终将该用户删除。
	 * 注意： 用此方法之前再确认用户是否真的删除。
	 * mysql存储过程：
     *		删除用户信息
     *		先删除userinfo表信息,后删除user表信息
     * 		删除成功返回1，该用户不存在返回0，执行异常返回-1
     * 		 传人参数：in userName varchar(45),
					out n int
	 */ 
	public boolean doDeleteUser(String userName) {
		 CallableStatement ps = null;
		 int i = 0;
		try {
			String sql = "{call p_deleteUser(?,?)}";
			ps = JdbcUtil.getPreparedStatementCall(sql, userName);
			i = ps.executeUpdate(); 
			int s = Integer.parseInt(ps.getString(2));
			if(i > 0 || s >= -1) {
				switch(s) {
				case -1: System.out.println("p_deleteUser的存储过程出现异常"); break;
				case 0:  System.out.println("该用户名不存在"); break;
				case 1:  System.out.println("用户名为："+userName+"删除成功"); return true;
				default: System.out.println("UserDAOImpl.java中deleteUser方法出现未知故障");
				}
			}   
		}catch(Exception e) {
			System.out.println("deleteUser方法出错!!!");
			new DAOException(e.getMessage());     // 调用异常处理方法
			e.printStackTrace();
		}finally {
				if(ps != null)
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}finally {
						if(con != null)
							try {
								con.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
					}   
		}
		return false;
	}
	
	
	/* 修改密码
	 * 该方法返回一个
	 * mysql存储过程：
		修改成功返回3，新密码和旧密码一样返回2,旧密码输入错误返回1,该用户不存在返回0，执行异常返回-1
		此存储过程需传入的参数
			in userName varchar(45),
			in olduserPassword varchar(45),
			in newuserPassword varchar(45),
			out n int
	*/
	public boolean doUpdateUserPassword(String userName, String olduserPassword, String newuserPassword) {
		CallableStatement ps = null;
		int n = 0;
		try {
			String sql = "call p_updateUserPassword(?,?,?,?,?)";
			ps = JdbcUtil.getPreparedStatementCall(sql, userName, olduserPassword, newuserPassword, java.sql.Types.VARCHAR);
			int i = ps.executeUpdate();
			n = Integer.parseInt(ps.getString(5));
			if(i > 0 || n >= -1) {
				switch(n) {
				case -1: System.out.println("p_updateUserPassword存储过程出现异常"); break;
				case 0:	 System.out.println("该用户不存在"); break;
				case 1:  System.out.println("旧密码输入错误"); break;
				case 2:	 System.out.println("新密码和旧密码一样");
				case 3:  System.out.println("修改成功"); return true;
				default: System.out.println("userDAOrealize.java中updateUserPassword方法出现未知故障");
				}
			}
		} catch (SQLException e) {
			System.out.println("异常处理报错，位于UserDAOrealize.java中updateUserPassword");
			e.printStackTrace();
		}finally {
				if(ps != null)
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}finally {
						if(con != null)
							try {
								con.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
					}
		}
		return false;
	}
	
	// 获取手机号码，
	public int getUserTel(String userName) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int tel = 0;
		try {
			String sql = "select userTel from userinfo where userName = ?";
			ps = JdbcUtil.getPreparedStatement(sql, userName);
			rs = ps.executeQuery();
			tel = Integer.parseInt(rs.getString(1));
			return tel;
		}catch(SQLException e) {
			System.out.println("异常处理报错，位于UserDAOrealize.java中getUserTel");
			e.printStackTrace();
		}finally {
			JdbcUtil.closeConnection(JdbcUtil.getConnection(),ps, rs);
		}
		return tel;
	}
	
	
	
	// 批处理增加user表的信息
	public boolean batchInsertUser(List<User>[] m) {
		boolean flag = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "insert into website.user(userName,userPassword,userEmail) values(?,?,?)";
			for(int i = 0; i < m.length; i++) {
				ps = JdbcUtil.getPreparedStatement(sql,m[i]);
				ps.addBatch();
			}
			int [] i = ps.executeBatch();
			if(i.length == m.length) 
				flag = true;
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.closeConnection(JdbcUtil.getConnection(),ps, rs);
		}
		return flag;
	}
	
	

	@SuppressWarnings("null")
	public Map<String, Object> S(){
		con = JdbcUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from user");
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			for(int i = 1; i <= count; i++)
			System.out.println(rsmd.getColumnName(i));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(st == null)
					st.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				
					try {
						if(rs == null)
							rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}finally {
						if(con == null)
							try {
								con.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
					}
			}
		}
		
		return null;
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		//UserDAOImpl u = new UserDAOImpl();
		User user = new User();
		user.setUserName("李四他爷的账号");
		user.setUserPassword("李四的密码");
		user.setUserEmail("李四的邮箱");
		Userinfo userinfo = new Userinfo();
		userinfo.setUserName("李四");
		userinfo.setUserSex("男");
		userinfo.setUserAvatarPath("李四的头像路径");
		
		userinfo.setUserBirthday(null);
		userinfo.setUserTel("张三的手机号码");
		//u.userList = null;
		//u.userinfoList = null;
		//System.out.println(new UserDAOImpl().selectUserExist("李四他爷的账号"));
		//System.out.println(u.doInsertUser01(user, userinfo));
		//System.out.println(u.doInsertUser02());
		ServiceFactory.getServiceFactory().getUserServiceImpl().insertUser(user, userinfo);
		//System.out.println(ServiceFactory.getServiceFactory().getUserServiceImpl().insertUser(user, userinfo));
		System.out.println(new UserDAOImpl().doDeleteUser("李四他爷的账号"));
	}
	

}
