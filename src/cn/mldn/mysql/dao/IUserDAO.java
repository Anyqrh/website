package cn.mldn.mysql.dao;



import cn.mldn.mysql.vo.User;
import cn.mldn.mysql.vo.Userinfo;

public interface IUserDAO {
	
		/**用户查找操作
		 * @param userName  用户名， 唯一的
		 * @return 如果返回true则表示存在，如果false则表示不存在
		 */
		public boolean selectUserExist(String userName); 
		
		// 将数据暂时存放在集合中
		public boolean doInsertUser01(User user, Userinfo userinfo);
		
		// 将用户添加到mysql中
		public boolean doInsertUser02();  
		// 用户登入，获取用户信息
		public Userinfo findByUserName(String userName);
		// 删除用户
		public boolean doDeleteUser(String userName);   
		// 修改密码
		public boolean doUpdateUserPassword(String userName, String olduserPassword, String newuserPassword);
		// 获取手机号码，
		public int getUserTel(String userName);
		// 发送邮箱
		public boolean sendEmail(String userEmail);
		// 获取验证码是否正确
		public boolean getEmailVeracity(String userEmail, Integer yzm);

}
