package cn.mldn.mysql.service;

import cn.mldn.mysql.vo.User;
import cn.mldn.mysql.vo.Userinfo;

public interface IUserService {
	
	public boolean insertUser(User user, Userinfo userinfo);
	
	public boolean deleteUser(String userName);
	
	public boolean updateUserPassword(String userName, String olduserPassword, String newuserPassword);
	
	
}
