package website;

import java.sql.Date;



/*
 * �û���Ϣ��
 * 
 */

public class Userinfo {
	private int userID = 0; // �û����(����user���uID)
	private String userName = null; // �û���ʵ����
	private String userSex = null; // �û��Ա�
	private String userAvatarPath = null; // �û�ͷ��Ĵ洢·��
	private Date userBirthday = null; // �û��������ڸ�ʽΪ��YYYY-MM-DD
	private String userTel = null; // �û��绰����
	
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
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserAvatarPath() {
		return userAvatarPath;
	}
	public void setUserAvatarPath(String userAvatarPath) {
		this.userAvatarPath = userAvatarPath;
	}
	public Date getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}


}
