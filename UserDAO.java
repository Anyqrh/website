package website;

interface UserDAO {
	// �����û��Ƿ����
	public boolean selectUIDexist(String userName); 
	
	public boolean addUser01(String userName, String userPassword, String userEmail);
	
	public boolean addUser02(String userName, int yzm);  
	// �û����룬��ȡ�û���Ϣ
	public Userinfo findUserinfo(String userName, String userPassword);
	// ɾ���û�
	public boolean deleteUser(String userName);   
}


 
