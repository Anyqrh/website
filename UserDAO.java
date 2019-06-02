package website;

import java.sql.Connection;


interface UserDAO {
	public Connection getConnection();
	public boolean selectUIDexist(String userName);   // �����û��Ƿ����
	public boolean addUser01(String userName, String userPassword, String userEmail);
	public boolean addUser02(String userName, int yzm);  
	public boolean deleteUser(String userName);    // ɾ���û�
}


 
