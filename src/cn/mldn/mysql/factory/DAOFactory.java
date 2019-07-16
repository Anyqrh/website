package cn.mldn.mysql.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import cn.mldn.mysql.dao.IUserDAO;


//����ģʽ
//��website.properties�ļ��У�key�ǵ�һ�е�UserDAOImpl��Value��cn.mldn.mysql.dao.impl.UserDAOImpl����ʽΪ��key = Value
//Valueֵ��ʵ�� �ӿ� ���࣬���Ǹ�ʵ����
public class DAOFactory {

	private static IUserDAO iuserDAO = null;
	private static DAOFactory userDAOFactory = new DAOFactory();
	
	@SuppressWarnings("deprecation")
	private DAOFactory() {
		try {
			Properties prop = new Properties();   // Properties������������properties�ļ�
			InputStream in = new FileInputStream(new File("src/cn/mldn/mysql/factory/website.properties"));
			prop.load(in);
			String UserDAOImpl = prop.getProperty("UserDAOImpl");
			@SuppressWarnings("rawtypes")
			Class clazz = Class.forName(UserDAOImpl);
			iuserDAO = (IUserDAO)clazz.newInstance();   // �����ַ���selectUIDexist������װ�ص���������ͨ��newInstance()����һ������
		} catch (Throwable e) {		
			System.out.println("���������⣡����");
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



