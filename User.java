package website;

/*
 * 用户表
 */

public class User {
	private int userID = 0; // 用户编号(自动编号)
	private String userName = null; // 用户呢称
	private String userPassword = null; // MD5加密
	private String userEmail = null; // 邮箱
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
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
