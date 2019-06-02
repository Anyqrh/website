package website;

import java.sql.Date;

import org.w3c.dom.Text;

import com.mysql.cj.jdbc.Blob;

/*
 * 用户信息表
 * 
 */

public class Userinfo {
	private int uID = 0; // 用户编号(参照user表的uID)
	private String uName = null; // 用户真实姓名
	private String uSex = null; // 用户性别
	private String uAvatar = null; // 用户头像的存储路径
	private Date uBirthday = null; // 用户出生日期格式为：YYYY-MM-DD
	private String uTel = null; // 用户电话号码

	public int getuID() {
		return uID;
	}

	public void setuID(int uID) {
		this.uID = uID;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getuSex() {
		return uSex;
	}

	public void setuSex(String uSex) {
		this.uSex = uSex;
	}

	public String getuAvatar() {
		return uAvatar;
	}

	public void setuAvatar(String uAvatar) {
		this.uAvatar = uAvatar;
	}

	public Date getuBirthday() {
		return uBirthday;
	}

	public void setuBirthday(Date uBirthday) {
		this.uBirthday = uBirthday;
	}

	public String getuTel() {
		return uTel;
	}

	public void setuTel(String uTel) {
		this.uTel = uTel;
	}

}
