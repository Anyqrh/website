package website;

import java.sql.Date;

import org.w3c.dom.Text;

import com.mysql.cj.jdbc.Blob;

/*
 * �û���Ϣ��
 * 
 */

public class Userinfo {
	private int uID = 0; // �û����(����user���uID)
	private String uName = null; // �û���ʵ����
	private String uSex = null; // �û��Ա�
	private String uAvatar = null; // �û�ͷ��Ĵ洢·��
	private Date uBirthday = null; // �û��������ڸ�ʽΪ��YYYY-MM-DD
	private String uTel = null; // �û��绰����

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
