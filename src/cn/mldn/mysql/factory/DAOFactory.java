package cn.mldn.mysql.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import cn.mldn.mysql.dao.IUserDAO;


//工厂模式
//在website.properties文件中，key是第一行的UserDAOImpl，Value是cn.mldn.mysql.dao.impl.UserDAOImpl，格式为：key = Value
//Value值是实现 接口 的类，即是个实现类
public class DAOFactory {

	private static IUserDAO iuserDAO = null;
	private static DAOFactory userDAOFactory = new DAOFactory();
	
	@SuppressWarnings("deprecation")
	private DAOFactory() {
		try {
			Properties prop = new Properties();   // Properties类是用来操作properties文件
			InputStream in = new FileInputStream(new File("src/cn/mldn/mysql/factory/website.properties"));
			prop.load(in);
			String UserDAOImpl = prop.getProperty("UserDAOImpl");
			@SuppressWarnings("rawtypes")
			Class clazz = Class.forName(UserDAOImpl);
			iuserDAO = (IUserDAO)clazz.newInstance();   // 根据字符串selectUIDexist将该类装载到虚拟机里，再通过newInstance()创建一个对象
		} catch (Throwable e) {		
			System.out.println("工厂出问题！！！");
			throw new ExceptionInInitializerError(e);
		}
	}
	public static DAOFactory getDAOFactory() {
		return userDAOFactory;
	}
	public IUserDAO getIUserDAO() {
		return iuserDAO;
	}
}



