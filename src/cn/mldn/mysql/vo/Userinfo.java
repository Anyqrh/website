package cn.mldn.mysql.vo;

import java.io.Serializable;
import java.sql.Date;


@SuppressWarnings("serial")
public class Userinfo implements Serializable{
	
	private Integer userID = 0; // 用户编号(参照user表的uID)
	private String userName = null; // 用户真实姓名
	private String userSex = null; // 用户性别
	private String userAvatarPath = null; // 用户头像的存储路径
	private Date userBirthday = null; // 用户出生日期格式为：YYYY-MM-DD
	private String userTel = null; // 用户电话号码
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
