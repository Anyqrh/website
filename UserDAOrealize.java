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
	int yzm = 0;  // ��֤��
	
	// ��ȡ���ݿ����ӣ�����һ��Connection�����ʵ��
	public Connection getConnection()  {
		Connection connection = null; // ����Connection�����ʵ��
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/website?characterEncoding=gbk";
			// characterEncoding=gbk Ϊָ�����ݿ����ӵı����ʽ����ֹд��ʱ��������
			String username = "root";
			String password = "qrh20000116";
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	// �÷�������һ��booleanֵ��trueΪ�����ڣ�falseΪ���ڣ�ǰ���ṩString userName(�û���)������
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
				flag = false; // �ж�uID�Ƿ����

		} catch (Exception e) {
				try {
					throw new DAOException(e.getMessage(),e);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					System.out.println("�쳣������λ��UserDAOrealize.java��");
					e1.printStackTrace();
				}
			
		} finally {
			ReleaseConnection.ReleaseConnection(con, st, ps, rs);
		}
		return flag;
	}

	
	/*
	 * �÷�������һ��booleanֵ��trueΪ�������������Ա��棬falseΪ����
	 * ǰ���ṩ������String userName(�û���), String userPassword(MD5���ܹ��������)��String userEmail(�ʼ�)��
	 */
	public boolean addUser01(String userName, String userPassword, String userEmail) {
		boolean flag = false;
		list = new ArrayList<User>();
		try {
			flag = selectUIDexist(userName);  // �ٴβ�ѯ���û��Ƿ��Ѵ��ڣ�flagΪtrueʱ�����û������ڣ������෴
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
				System.out.println("�쳣������λ��UserDAOrealize.java��");
				e1.printStackTrace();
			}
		}
		return flag;
	}
	
	
	
	/*
	 * �÷�������һ��booleanֵ��trueΪ��֤����ȷ��falseΪ����ȷ
	 * ǰ���ṩ������String userName(�û���), int yzm(��֤��)��
	 * ���ã���ǰ�˴���������֤�������֤���Ƿ��뷢�ͳ�ȥ����֤���Ƿ�һ�£������жϸÿͻ��س��Ƿ�ƥ��
	 * 		���ǣ�����д洢
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
					System.out.println("�쳣������λ��UserDAOrealize.java��");
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
				//    ��������                             / ///////////////////////////////////////////////////////////
			});
			thread.setName(userName);
			thread.start();
			}catch(Exception e) {
				try {
					throw new DAOException(e.getMessage(),e);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					System.out.println("�쳣������λ��UserDAOrealize.java��");
					e1.printStackTrace();
				}
			}
		}
		return flag;
	}
	
	/*
	 * �÷�������һ��Userinfoֵ��
	 * ǰ���ṩ������String userName(�û���), String userPassword(MD5���ܹ��������)��
	 * ���ã���ǰ�˴��������û��������������֤,   �����û���Ϣ����ȡ�û���Ϣ,   
	 * ע�⣺ �ڴ˷���ִ��ǰ����Ȳ�ѯ�Ƿ���û��س��Ƿ���ڣ����ȵ���public boolean selectUIDexist(String uName)����
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
	 * �÷�������һ��booleanֵ��trueΪ�Ѿ�ɾ����falseΪɾ�����ɹ�
	 * ǰ���ṩ������String userName(�û���)��
	 * ���ã���ǰ�˴��������û�������ѯ���û������ս����û�ɾ����
	 * ע�⣺ �ô˷���֮ǰ��ȷ���û��Ƿ����ɾ����
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
			System.out.println("deleteUser��������!!!");
			new DAOException(e.getMessage());     // �����쳣������
			e.printStackTrace();
		}finally {
			ReleaseConnection.ReleaseConnection(con, st, ps, rs);
		}
		return flag;
	}
}


/* �Ƚ��Ͻ����ͷŷ�ʽ try{
 * 
 * }finally { try { if(rs != null) rs.close(); }finally { try { if(st != null)
 * st.close(); }finally { if(con != null) con.close(); } } }
 */