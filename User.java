package website;

/*
 * �û���
 */

public class User {
	private int uID = 0; // �û����(�Զ����)
	private String uName = null; // �û��س�
	private String MD5 = null; // MD5����
	private String uEmail = null; // ����

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
