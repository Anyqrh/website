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
	Integer yzm = 0;  // ��֤��

	// �÷�������һ��booleanֵ��trueΪ���ڣ�falseΪ�����ڣ�ǰ���ṩString userName(�û���)������
	//  �жϸ��û��Ƿ����
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
				flag = true; // �ж�userID�Ƿ����
		} catch (Exception e) {
					System.out.println("�쳣������λ��UserDAOImpl.java��selectUIDexist");
					e.printStackTrace();
				
		} finally {
			JdbcUtil.closeConnection(JdbcUtil.getConnection(),ps, rs);
		}
		return flag;
	}

	
	/*
	 * �÷�������һ��booleanֵ��trueΪ�������������ѱ��棬falseΪ����
	 * ���������User user, Userinfo userinfo
	���ã�
		��������ʱ���浽����userList��userinfoList��
	 */
	public boolean doInsertUser01(User user, Userinfo userinfo) {
		boolean flag = false;
		userList = new ArrayList<User>(); //  ��ʱ��Ÿ�����Ϣ
		userinfoList = new ArrayList<Userinfo>();
		try {
			flag = !selectUserExist(user.getUserName());  // �ٴβ�ѯ���û��Ƿ��Ѵ��ڣ�flagΪtrueʱ�����û����ڣ������෴
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
				System.out.println("�쳣������λ��UserDAOImpl.java��doInsertUser01");
				throw new DAOException(e.getMessage(),e);
			} catch (DAOException e1) {
				System.out.println("�쳣������λ��UserDAOImpl.java��doInsertUser01");
				e1.printStackTrace();
			}
		}
		return flag;
	}
	
	
	
	/*
	 * UserDAOImpl.java.doInsertUser02()
	 * �÷�������һ��booleanֵ��trueΪ��֤����ȷ��falseΪ����ȷ
	 * ǰ���ṩ������null 
	 * ���ã������ݴ���mysql��
	 * mysql�еĴ洢����p_addUser�����ã�					
	 		* �����û���Ϣ
	 		* ����user��Ϣ,������userinfo��
	 		* ���ӳɹ�����1�����ɹ�����0��ִ���쳣����-1
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
					case -1: System.out.println("mysql�Ĵ洢����p_addUser������"); break;
					case 0: System.out.println("���û��Ѵ���"); break;
					case 1: System.out.println("��ӳɹ�"); return true;
					default: System.out.println("UserDAOImpl.java��doInsertUser02��������δ֪����");
					}
				}	
			}catch(Exception e) {
				try {
					System.out.println("�쳣������λ��UserDAOImpl.java��");
					throw new DAOException(e.getMessage(),e);
				} catch (DAOException e1) {
					System.out.println("UserDAOImpl�г������⣡����");
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
	 * ��ȡ��֤���Ƿ���ȷ
	 */
	public boolean getEmailVeracity(String userEmail, Integer yzm) {
		if(yzm == this.yzm && userList.get(1).getUserEmail() == userEmail) return true;
		return false;
	}
	
	/*
	 * ��������
	 */
	public boolean sendEmail(String userEmail) {
		return false;
	}
	
	
	
	/*
	 * �÷�������һ��Userinfoֵ��
	 * ǰ���ṩ������String userName(�û���), String userPassword(MD5���ܹ��������)��
	 * ���ã���ǰ�˴��������û��������������֤,   �����û���Ϣ����ȡ�û���Ϣ,   
	 * ע�⣺ �ڴ˷���ִ��ǰ����Ȳ�ѯ�Ƿ���û��س��Ƿ���ڣ����ȵ���public boolean selectUIDexist(String uName)����
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
			userinfo = JdbcUtil.addUserinfo(rs, userinfo);  // �����û���Ϣ
		}catch(SQLException e) {
			System.out.println("�쳣������λ��UserDAOImpl.java�е�findByUserName");
			e.printStackTrace();
		}finally {
			JdbcUtil.closeConnection(JdbcUtil.getConnection(),ps, rs);
		}
			return userinfo;
		
	}
	
	/*
	 * �÷�������һ��booleanֵ��trueΪ�Ѿ�ɾ����falseΪɾ�����ɹ�
	 * ǰ���ṩ������String userName(�û���)��
	 * ���ã���ǰ�˴��������û�������ѯ���û������ս����û�ɾ����
	 * ע�⣺ �ô˷���֮ǰ��ȷ���û��Ƿ����ɾ����
	 * mysql�洢���̣�
     *		ɾ���û���Ϣ
     *		��ɾ��userinfo����Ϣ,��ɾ��user����Ϣ
     * 		ɾ���ɹ�����1�����û������ڷ���0��ִ���쳣����-1
     * 		 ���˲�����in userName varchar(45),
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
				case -1: System.out.println("p_deleteUser�Ĵ洢���̳����쳣"); break;
				case 0:  System.out.println("���û���������"); break;
				case 1:  System.out.println("�û���Ϊ��"+userName+"ɾ���ɹ�"); return true;
				default: System.out.println("UserDAOImpl.java��deleteUser��������δ֪����");
				}
			}   
		}catch(Exception e) {
			System.out.println("deleteUser��������!!!");
			new DAOException(e.getMessage());     // �����쳣������
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
	
	
	/* �޸�����
	 * �÷�������һ��
	 * mysql�洢���̣�
		�޸ĳɹ�����3��������;�����һ������2,������������󷵻�1,���û������ڷ���0��ִ���쳣����-1
		�˴洢�����贫��Ĳ���
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
				case -1: System.out.println("p_updateUserPassword�洢���̳����쳣"); break;
				case 0:	 System.out.println("���û�������"); break;
				case 1:  System.out.println("�������������"); break;
				case 2:	 System.out.println("������;�����һ��");
				case 3:  System.out.println("�޸ĳɹ�"); return true;
				default: System.out.println("userDAOrealize.java��updateUserPassword��������δ֪����");
				}
			}
		} catch (SQLException e) {
			System.out.println("�쳣������λ��UserDAOrealize.java��updateUserPassword");
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
	
	// ��ȡ�ֻ����룬
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
			System.out.println("�쳣������λ��UserDAOrealize.java��getUserTel");
			e.printStackTrace();
		}finally {
			JdbcUtil.closeConnection(JdbcUtil.getConnection(),ps, rs);
		}
		return tel;
	}
	
	
	
	// ����������user�����Ϣ
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
		user.setUserName("������ү���˺�");
		user.setUserPassword("���ĵ�����");
		user.setUserEmail("���ĵ�����");
		Userinfo userinfo = new Userinfo();
		userinfo.setUserName("����");
		userinfo.setUserSex("��");
		userinfo.setUserAvatarPath("���ĵ�ͷ��·��");
		
		userinfo.setUserBirthday(null);
		userinfo.setUserTel("�������ֻ�����");
		//u.userList = null;
		//u.userinfoList = null;
		//System.out.println(new UserDAOImpl().selectUserExist("������ү���˺�"));
		//System.out.println(u.doInsertUser01(user, userinfo));
		//System.out.println(u.doInsertUser02());
		ServiceFactory.getServiceFactory().getUserServiceImpl().insertUser(user, userinfo);
		//System.out.println(ServiceFactory.getServiceFactory().getUserServiceImpl().insertUser(user, userinfo));
		System.out.println(new UserDAOImpl().doDeleteUser("������ү���˺�"));
	}
	

}
