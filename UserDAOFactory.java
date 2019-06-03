package website;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


// UserDAO的工厂模式
//  在user.properties文件中，key是第一行的UserDAO，Value是website.UserDAOrealize，格式为：key = Value
//  Value值是实现 接口 的类，即是个实现类
public class UserDAOFactory {
	
	private static UserDAO userDAO = null;
	private static UserDAOFactory userDAOFactory = new UserDAOFactory();
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	private UserDAOFactory() {
		try {
			Properties prop = new Properties();   // Properties类是用来操作properties文件
			InputStream in = new FileInputStream(new File("src/user.properties"));
			prop.load(in);
			String UserDAOrealize = prop.getProperty("UserDAOrealize");
			Class clazz = Class.forName(UserDAOrealize);
			userDAO = (UserDAO)clazz.newInstance();   // 根据字符串selectUIDexist将该类装载到虚拟机里，再通过newInstance()创建一个对象
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
