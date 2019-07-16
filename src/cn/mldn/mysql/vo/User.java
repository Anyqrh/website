package cn.mldn.mysql.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable{
	private Integer userID = 0; // 用户编号(自动编号)
	private String userName = null; // 用户呢称
	private String userPassword = null; // MD5加密
	private String userEmail = null; // 邮箱
	
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	

}
