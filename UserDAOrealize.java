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

	// �÷�������һ��booleanֵ��trueΪ�����ڣ�falseΪ���ڣ�ǰ���ṩString uName(�û���)������
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
	 * ǰ���ṩ������String uName(�û���), String MD5(MD5���ܹ��������)��String uEmail(�ʼ�)��
	 */
	public boolean addUser01(String uName, String MD5, String uEmail) {
		boolean flag = false;
		list = new ArrayList<User>();
		try {
			flag = selectUIDexist(uName);  // �ٴβ�ѯ���û��Ƿ��Ѵ��ڣ�flagΪtrueʱ�����û������ڣ������෴
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
				System.out.println("�쳣������λ��UserDAOrealize.java��");
				e1.printStackTrace();
			}
		}
		return flag;
	}
	
	
	
	/*
	 * �÷�������һ��booleanֵ��trueΪ��֤����ȷ��falseΪ����ȷ
	 * ǰ���ṩ������String uName(�û���), int yzm(��֤��)��
	 * ���ã���ǰ�˴���������֤�������֤���Ƿ��뷢�ͳ�ȥ����֤���Ƿ�һ�£������жϸÿͻ��س��Ƿ�ƥ��
	 * 		���ǣ�����д洢
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
			thread.setName(uName);
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
	 * ǰ���ṩ������String uName(�û���), String MD5(MD5���ܹ��������)��
	 * ���ã���ǰ�˴��������û��������������֤,��ȡ�û���Ϣ
	 * ע�⣺ �ڴ˷���ִ��ǰ����Ȳ�ѯ�Ƿ���û��س��Ƿ���ڣ����ȵ���public boolean selectUIDexist(String uName)����
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
	 * �÷�������һ��booleanֵ��trueΪ�Ѿ�ɾ����falseΪɾ�����ɹ�
	 * ǰ���ṩ������String uName(�û���)��
	 * ���ã���ǰ�˴��������û�������ѯ���û������ս����û�ɾ����
	 * ע�⣺ �ô˷���֮ǰ��ȷ���Ƿ����ɾ����
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
			System.out.println("deleteUser��������!!!");
			new DAOException(e.getMessage());
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