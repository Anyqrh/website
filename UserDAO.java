package website;

interface UserDAO {
	// 查找用户是否存在
	public boolean selectUIDexist(String userName); 
	
	public boolean addUser01(String userName, String userPassword, String userEmail);
	
	public boolean addUser02(String userName, int yzm);  
	// 用户登入，获取用户信息
	public Userinfo findUserinfo(String userName, String userPassword);
	// 删除用户
	public boolean deleteUser(String userName);   
}


 
