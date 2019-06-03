package website;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


// UserDAO�Ĺ���ģʽ
//  ��user.properties�ļ��У�key�ǵ�һ�е�UserDAO��Value��website.UserDAOrealize����ʽΪ��key = Value
//  Valueֵ��ʵ�� �ӿ� ���࣬���Ǹ�ʵ����
public class UserDAOFactory {
	
	private static UserDAO userDAO = null;
	private static UserDAOFactory userDAOFactory = new UserDAOFactory();
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	private UserDAOFactory() {
		try {
			Properties prop = new Properties();   // Properties������������properties�ļ�
			InputStream in = new FileInputStream(new File("src/user.properties"));
			prop.load(in);
			String UserDAOrealize = prop.getProperty("UserDAOrealize");
			Class clazz = Class.forName(UserDAOrealize);
			userDAO = (UserDAO)clazz.newInstance();   // �����ַ���selectUIDexist������װ�ص���������ͨ��newInstance()����һ������
		} catch (Throwable e) {						  
			throw new ExceptionInInitializerError(e);
		}
	}
	public static UserDAOFactory getUserDAOFactory() {
		return userDAOFactory;
	}
	public UserDAO getUserDAO() {
		return userDAO;
	}
}
