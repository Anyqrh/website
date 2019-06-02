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

	// 该方法返回一个boolean值，true为不存在，false为存在，前端提供String uName(用户名)参数。
	public boolean selectUIDexist(String uName) {
		boolean flag = true;
		Connection con = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select count(*) from website.user where uName = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, uName);
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
	 * 前端提供参数：String uName(用户名), String MD5(MD5加密过后的密文)，String uEmail(邮件)。
	 */
	public boolean addUser01(String uName, String MD5, String uEmail) {
		boolean flag = false;
		list = new ArrayList<User>();
		try {
			flag = selectUIDexist(uName);  // 再次查询该用户是否已存在，flag为true时，该用户不存在，否则相反
			if(flag) {   
				User user = new User();
				user.setuName(uName);
				user.setMD5(MD5);
				user.setuEmail(uEmail);
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
	 * 前端提供参数：String uName(用户名), int yzm(验证码)。
	 * 作用：将前端传过来的验证码进行验证，是否与发送出去的验证码是否一致，并且判断该客户呢称是否匹配
	 * 		若是，则进行存储
	 */
	public boolean addUser02(String uName, int yzm) {
		boolean flag = false;
		Connection con = null;
		PreparedStatement ps = null;
		if(yzm == this.yzm && uName == list.get(1).getuName()) {
			try {
				con = getConnection();
				String sql = "insert into user(uName, MD5, uEmail) values(?,?,?)";
				ps = con.prepareStatement(sql);
				ps.setString(1, list.get(1).getuName());
				ps.setString(2, list.get(1).getMD5());
				ps.setString(3, list.get(1).getuEmail());
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
			thread.setName(uName);
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
	 * 前端提供参数：String uName(用户名), String MD5(MD5加密过后的密文)。
	 * 作用：将前端传过来的用户名和密码进行验证,获取用户信息
	 * 注意： 在此方法执行前最好先查询是否该用户呢称是否存在，即先调用public boolean selectUIDexist(String uName)方法
	 */ 
	@SuppressWarnings("null")
	public Userinfo findUserinfo(String uName, String MD5) {
		Userinfo userinfo = null;
		Connection con = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select uName, uSex, uAvatar, uBirthday, uTel from userinfo where uName = ? and MD5 = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, uName);
			ps.setString(2, MD5);
			rs = ps.executeQuery();
			while(rs.next()) {
				userinfo.setuName(rs.getString("uName"));
				userinfo.setuSex(rs.getString("uSex"));
				userinfo.setuAvatar(rs.getString("uAvatar"));
				userinfo.setuBirthday(rs.getDate("uBirthday"));
				userinfo.setuTel(rs.getString("uTel"));
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
	 * 前端提供参数：String uName(用户名)。
	 * 作用：以前端传过来的用户名来查询该用户，最终将该用户删除。
	 * 注意： 用此方法之前再确认是否真的删除。
	 */ 
	public boolean deleteUser(String uName) {
		boolean flag = false;
		Connection con = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "delete from user where uName = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, uName);
			int i = ps.executeUpdate(); 
			if(i > 0) flag = true;    
		}catch(SQLException e) {
			System.out.println("deleteUser方法出错!!!");
			new DAOException(e.getMessage());
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