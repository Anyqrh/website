package website;

import java.sql.Connection;


interface UserDAO {
	public Connection getConnection();
	public boolean selectUIDexist(String uName);   // 查找用户是否存在
	public boolean addUser01(String uName, String MD5, String uEmail);
	public boolean addUser02(String uName, int yzm);  
	public boolean deleteUser(String uName);    // 删除用户
	
}


 
