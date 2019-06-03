package website;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOrealize implements UserDAO{
	List<User> list = null;
	int yzm = 0;  // ��֤��

	// �÷�������һ��booleanֵ��trueΪ�����ڣ�falseΪ���ڣ�ǰ���ṩString userName(�û���)������
	//  �жϸ��û��Ƿ����
	public boolean selectUIDexist(String userName) {
		boolean flag = true;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) from website.user where userName = ?";
			ps = JdbcUtil.getPreparedStatement(sql,userName);
			rs = ps.executeQuery();
			if (rs.getInt(1) > 0)
				flag = false; // �ж�userID�Ƿ����
		} catch (Exception e) {
				try {
					throw new DAOException(e.getMessage(),e);
				} catch (DAOException e1) {
					// TODO Auto-generated catch block
					System.out.println("�쳣������λ��UserDAOrealize.java��");
					e1.printStackTrace();
				}
		} finally {
			JdbcUtil.closeConnection(ps, rs);
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
					System.out.println("�쳣������λ��UserDAOrealize.java��");
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select userName, userSex, userAvatarPath, userBirthday, userTel from userinfo where userName = ? and userPassword = ?";
			ps = JdbcUtil.getPreparedStatement(sql, userName, userPassword);
			rs = ps.executeQuery();
			userinfo = JdbcUtil.addUserinfo(rs, userinfo);  // �����û���Ϣ
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
	 * �÷�������һ��booleanֵ��trueΪ�Ѿ�ɾ����falseΪɾ�����ɹ�
	 * ǰ���ṩ������String userName(�û���)��
	 * ���ã���ǰ�˴��������û�������ѯ���û������ս����û�ɾ����
	 * ע�⣺ �ô˷���֮ǰ��ȷ���û��Ƿ����ɾ����
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
			System.out.println("deleteUser��������!!!");
			new DAOException(e.getMessage());     // �����쳣������
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



/* �Ƚ��Ͻ����ͷŷ�ʽ try{
 * 
 * }finally { try { if(rs != null) rs.close(); }finally { try { if(st != null)
 * st.close(); }finally { if(con != null) con.close(); } } }
 */