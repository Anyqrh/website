package website;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 *  公共方法
 */

public class JdbcUtil {
	// 获取连接
	public static Connection getConnection()  {
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
	
	// 断开连接
	public static void closeConnection(Connection con, Statement st, PreparedStatement ps, ResultSet rs) {
		try {	
			if(rs != null)
				rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {	
				if(st != null)
				st.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				if(ps != null)
					try {
					ps.close();
					} catch(SQLException e) {
						e.printStackTrace();
					} finally {
						if(con != null)
							try {
								con.close();
							} catch(SQLException e) {
								e.printStackTrace();
							}
					}
				}
			}
		}
	
	public static void closeConnection(PreparedStatement ps, ResultSet rs) {
		if(rs != null)
			try {
				rs.close();
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
	}	
		
				
				
			
		
	
	
	// 预处理     获取PreparedStatement值
	public static PreparedStatement getPreparedStatement(String sql, Object...objects) {
		PreparedStatement ps = null;
		try {
			Connection con = getConnection();
			ps = con.prepareStatement(sql);
			for(int i = 0; i < objects.length; i++) {
				ps.setObject(i+1,objects[i]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}
	
	
	//  添加用户信息，返回该用户信息
	public static Userinfo addUserinfo(ResultSet rs, Userinfo userinfo) {
		try {
			while(rs.next()) {
				userinfo.setUserName(rs.getString("userName"));
				userinfo.setUserSex(rs.getString("userSex"));
				userinfo.setUserAvatarPath(rs.getString("userAvatarPath"));
				userinfo.setUserBirthday(rs.getDate("userBirthday"));
				userinfo.setUserTel(rs.getString("userTel"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return userinfo;
	}

}
