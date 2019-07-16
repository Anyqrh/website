package cn.mldn.mysql.dao;



import cn.mldn.mysql.vo.User;
import cn.mldn.mysql.vo.Userinfo;

public interface IUserDAO {
	
		/**�û����Ҳ���
		 * @param userName  �û����� Ψһ��
		 * @return �������true���ʾ���ڣ����false���ʾ������
		 */
		public boolean selectUserExist(String userName); 
		
		// ��������ʱ����ڼ�����
		public boolean doInsertUser01(User user, Userinfo userinfo);
		
		// ���û���ӵ�mysql��
		public boolean doInsertUser02();  
		// �û����룬��ȡ�û���Ϣ
		public Userinfo findByUserName(String userName);
		// ɾ���û�
		public boolean doDeleteUser(String userName);   
		// �޸�����
		public boolean doUpdateUserPassword(String userName, String olduserPassword, String newuserPassword);
		// ��ȡ�ֻ����룬
		public int getUserTel(String userName);
		// ��������
		public boolean sendEmail(String userEmail);
		// ��ȡ��֤���Ƿ���ȷ
		public boolean getEmailVeracity(String userEmail, Integer yzm);

}
