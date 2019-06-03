package website;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOrealize implements UserDAO{
	List<User> list = null;
	int yzm = 0;  // 验证码

	// 该方法返回一个boolean值，true为不存在，false为存在，前端提供String userName(用户名)参数。
	//  判断该用户是否存在
	public boolean selectUIDexist(String userName) {
		boolean flag = true;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) from website.user where userName = ?";
			ps = JdbcUtil.getPreparedStatement(sql,userName);
			rs = ps.executeQuery();
			if (rs.getInt(1) > 0)
				flag = false; // 判断userID是否存在
		} catch (Exception e) {
				try {
					throw new DAOException(e.getMessage(),e);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					System.out.println("异常处理报错，位于UserDAOrealize.java中");
					e1.printStackTrace();
				}
		} finally {
			JdbcUtil.closeConnection(ps, rs);
		}
		return flag;
	}

	
	/*
	 * 该方法返回一个boolean值，true为不存在且数据以保存，false为存在
	 * 前端提供参数：String userName(用户名), String userPassword(MD5加密过后的密文)，String userEmail(邮件)。
	 */
	public boolean addUser01(String userName, String userPassword, String userEmail) {
		boolean flag = false;
		list = new ArrayList<User>();
		try {
			flag = selectUIDexist(userName);  // 再次查询该用户是否已存在，flag为true时，该用户不存在，否则相反
			if(flag) {   
				User user = new User();
				user.setUserName(userName);
				user.setUserPassword(userPassword);
				user.setUserEmail(userEmail);
				list.add(user);
			}
		}catch(Exception e) {
			try {
				throw new DAOException(e.getMessage(),e);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				System.out.println("异常处理报错，位于UserDAOrealize.java中");
				e1.printStackTrace();
			}
		}
		return flag;
	}
	
	
	
	/*
	 * 该方法返回一个boolean值，true为验证码正确，false为不正确
	 * 前端提供参数：String userName(用户名), int yzm(验证码)。
	 * 作用：将前端传过来的验证码进行验证，是否与发送出去的验证码是否一致，并且判断该客户呢称是否匹配
	 * 		若是，则进行存储
	 */
	public boolean addUser02(String userName, int yzm) {
		boolean flag = false;
		PreparedStatement ps = null;
		if(yzm == this.yzm && userName == list.get(1).getUserName()) {
			try {
				String sql = "insert into user(userName, userPassword, userEmail) values(?,?,?)";
				ps = JdbcUtil.getPreparedStatement(sql, list.get(1).getUserName(),
						list.get(1).getUserPassword(),list.get(1).getUserEmail());
				int i = ps.executeUpdate();
				if(i > 0) {
					flag = true;
				}
			}catch(Exception e) {
				try {
					throw new DAOException(e.getMessage(),e);
				} catch (DAOException e1) {
					System.out.println("异常处理报错，位于UserDAOrealize.java中");
					e1.printStackTrace();
				}
			}finally {
				list = null;
				if(ps != null)
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
		else {
			try {
			Thread thread = new Thread(()->{
				//    技术不够                             / ///////////////////////////////////////////////////////////
			});
			thread.setName(userName);
			thread.start();
			}catch(Exception e) {
				try {
					throw new DAOException(e.getMessage(),e);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					System.out.println("异常处理报错，位于UserDAOrealize.java中");
					e1.printStackTrace();
				}
			}
		}
		return flag;
	}
	
	/*
	 * 该方法返回一个Userinfo值，
	 * 前端提供参数：String userName(用户名), String userPassword(MD5加密过后的密文)。
	 * 作用：将前端传过来的用户名和密码进行验证,   查找用户信息，获取用户信息,   
	 * 注意： 在此方法执行前最好先查询是否该用户呢称是否存在，即先调用public boolean selectUIDexist(String uName)方法
	 */ 
	@SuppressWarnings("null")
	public Userinfo findUserinfo(String userName, String userPassword) {
		Userinfo userinfo = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select userName, userSex, userAvatarPath, userBirthday, userTel from userinfo where userName = ? and userPassword = ?";
			ps = JdbcUtil.getPreparedStatement(sql, userName, userPassword);
			rs = ps.executeQuery();
			userinfo = JdbcUtil.addUserinfo(rs, userinfo);  // 增加用户信息
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.closeConnection(ps, rs);
		}
		if(userinfo != null)
			return userinfo;
		else return null;
	}
	
	/*
	 * 该方法返回一个boolean值，true为已经删除，false为删除不成功
	 * 前端提供参数：String userName(用户名)。
	 * 作用：以前端传过来的用户名来查询该用户，最终将该用户删除。
	 * 注意： 用此方法之前再确认用户是否真的删除。
	 */ 
	public boolean deleteUser(String userName) {
		boolean flag = false;
		PreparedStatement ps = null;
		try {
			String sql = "delete from user where userName = ?";
			ps = JdbcUtil.getPreparedStatement(sql, userName);
			int i = ps.executeUpdate(); 
			if(i > 0) flag = true;    
		}catch(SQLException e) {
			System.out.println("deleteUser方法出错!!!");
			new DAOException(e.getMessage());     // 调用异常处理方法
			e.printStackTrace();
		}finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}   
		}
		return flag;
	}
	
	public boolean updateUserPassword(String userName, String userPassword) {
		boolean falg = false;
		PreparedStatement ps = null;
		try {
			String sql = "update user set userPassword = ? where userName = ?";
			ps = JdbcUtil.getPreparedStatement(sql, userPassword, userName);
			int i = ps.executeUpdate();
			if(i > 0) falg = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return falg;
	}
	
	
	
}



/* 比较严谨的释放方式 try{
 * 
 * }finally { try { if(rs != null) rs.close(); }finally { try { if(st != null)
 * st.close(); }finally { if(con != null) con.close(); } } }
 */