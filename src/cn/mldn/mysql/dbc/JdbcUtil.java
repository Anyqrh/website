package cn.mldn.mysql.dbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.mldn.mysql.vo.Userinfo;



public class JdbcUtil {
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/website?characterEncoding=UTF-8&"
			+ "useSSL=true&serverTimezone=Asia/Shanghai";
	// characterEncoding=gbk Ϊָ�����ݿ����ӵı����ʽ����ֹд��ʱ��������
	private static final String USER = "root";
	private static final String PASSWORD = "qrh20000116";
	private static Connection connection = null;

	public static Connection getConnection() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	
		
		// �Ͽ�����
		
		public static void closeConnection(Connection con, Statement st, PreparedStatement ps, ResultSet rs) {
			try {	
				if(rs != null)   // �����(null)ָ���쳣
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
		
	
		public static void closeConnection(Connection con, PreparedStatement ps, ResultSet rs) {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					System.out.println("JdbcUtil�е�closeConnection(PreparedStatement ps, ResultSet rs)������");
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
								}catch(SQLException e) {
									e.printStackTrace();
								}
						}
				}
		}	
			
		
		// Ԥ����     ��ȡPreparedStatementֵ
	
		public static PreparedStatement getPreparedStatement(String sql, Object...objects) {
			PreparedStatement ps = null;
			try {
				connection = JdbcUtil.getConnection();
				ps = connection.prepareStatement(sql);
				for(int i = 0; i < objects.length; i++) {
					ps.setObject(i+1,objects[i]);
				}
			} catch (SQLException e) {
				System.out.println("JdbcUtil��getPreparedStatement(String sql, Object...objects)��������");
				e.printStackTrace();
			}
			return ps;
		}
		
		
		/*
		 * 	CallableStatement
		 * 	ִ�д������������out���Ĵ洢����
		 */
		public static  CallableStatement getPreparedStatementCall(String sql, Object...objects) {
			CallableStatement ps = null;
			connection = JdbcUtil.getConnection();
			try {
				ps = connection.prepareCall(sql);
				for(int i = 0; i < objects.length; i++) {
					ps.setObject(i+1,objects[i]);
				}
				ps.registerOutParameter(objects.length+1, java.sql.Types.VARCHAR);
			} catch (SQLException e) {
				System.out.println("jdbcUtil.java��getPreparedStatementCall�����쳣");
				e.printStackTrace();
			}
			return ps;
		}		
	
		
		//  ����û���Ϣ�����ظ��û���Ϣ
		
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
				System.out.println("JdbcUtil��addUserinfo(ResultSet rs, Userinfo userinfo)������");
			}
			return userinfo;
		}
		
		
		// ��sql�����ӵ���־�ļ�����
		
		public static boolean journalFile(String objects) {
			boolean flag = false;
			PreparedStatement ps = null;
			try {
			String sql = "insert into journalfile(journalFile) value(?)";
			ps = JdbcUtil.getPreparedStatement(sql, objects);
			int i = ps.executeUpdate();
			if(i > 0) return true;
			}catch(SQLException e) {
				e.printStackTrace();;
			}finally {
				if(ps != null)
					try {
						ps.close();
					} catch (SQLException e) {
						System.out.println("JdbcUtil��journalFile(String objects������");
						e.printStackTrace();
					}
			}
			return flag;
		}
}
