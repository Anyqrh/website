package website;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAOrealize implements UserDAO{
	List<User> list = null;
	int yzm = 0;  // 验证码
	
	// 获取数据库连接，返回一个Connection对象的实例
	public Connection getConnection()  {
		Connection connection = null; // 声明Connection对象的实例
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/website?characterEncoding=gbk";
			// characterEncoding=gbk 为指定数据库连接的编码格式，防止写入时中文乱码
			String username = "root";
			String password = "qrh20000116";
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	// 该方法返回一个boolean值，true为不存在，false为存在，前端提供String userName(用户名)参数。
	public boolean selectUIDexist(String userName) {
		boolean flag = true;
		Connection con = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select count(*) from website.user where userName = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			if (rs.getInt(1) > 0)
				flag = false; // 判断uID是否存在

		} catch (Exception e) {
				try {
					throw new DAOException(e.getMessage(),e);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					System.out.println("异常处理报错，位于UserDAOrealize.java中");
					e1.printStackTrace();
				}
			
		} finally {
			ReleaseConnection.ReleaseConnection(con, st, ps, rs);
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
		Connection con = null;
		PreparedStatement ps = null;
		if(yzm == this.yzm && userName == list.get(1).getUserName()) {
			try {
				con = getConnection();
				String sql = "insert into user(userName, userPassword, userEmail) values(?,?,?)";
				ps = con.prepareStatement(sql);
				ps.setString(1, list.get(1).getUserName());
				ps.setString(2, list.get(1).getUserPassword());
				ps.setString(3, list.get(1).getUserEmail());
				int i = ps.executeUpdate();
				if(i > 0) {
					flag = true;
				}
			}catch(Exception e) {
				try {
					throw new DAOException(e.getMessage(),e);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					System.out.println("异常处理报错，位于UserDAOrealize.java中");
					e1.printStackTrace();
				}
			}finally {
				list = null;
				try {
					if(ps != null)
						ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					if(con != null)
						try {
							con.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
		Connection con = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select userName, userSex, userAvatarPath, userBirthday, userTel from userinfo where userName = ? and userPassword = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, userPassword);
			rs = ps.executeQuery();
			while(rs.next()) {
				userinfo.setUserName(rs.getString("userName"));
				userinfo.setUserSex(rs.getString("userSex"));
				userinfo.setUserAvatarPath(rs.getString("userAvatarPath"));
				userinfo.setUserBirthday(rs.getDate("userBirthday"));
				userinfo.setUserTel(rs.getString("userTel"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			ReleaseConnection.ReleaseConnection(con, st, ps, rs);
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
		Connection con = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "delete from user where userName = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userName);
			int i = ps.executeUpdate(); 
			if(i > 0) flag = true;    
		}catch(SQLException e) {
			System.out.println("deleteUser方法出错!!!");
			new DAOException(e.getMessage());     // 调用异常处理方法
			e.printStackTrace();
		}finally {
			ReleaseConnection.ReleaseConnection(con, st, ps, rs);
		}
		return flag;
	}
}


/* 比较严谨的释放方式 try{
 * 
 * }finally { try { if(rs != null) rs.close(); }finally { try { if(st != null)
 * st.close(); }finally { if(con != null) con.close(); } } }
 */