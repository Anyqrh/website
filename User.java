package website;

/*
 * 用户表
 */

public class User {
	private int uID = 0; // 用户编号(自动编号)
	private String uName = null; // 用户呢称
	private String MD5 = null; // MD5加密
	private String uEmail = null; // 邮箱

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

	public String getMD5() {
		return MD5;
	}

	public void setMD5(String mD5) {
		MD5 = mD5;
	}

	public String getuEmail() {
		return uEmail;
	}

	public void setuEmail(String uEmail) {
		this.uEmail = uEmail;
	}

}
