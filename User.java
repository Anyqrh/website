package website;

/*
 * �û���
 */

public class User {
	private int userID = 0; // �û����(�Զ����)
	private String userName = null; // �û��س�
	private String userPassword = null; // MD5����
	private String userEmail = null; // ����
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
