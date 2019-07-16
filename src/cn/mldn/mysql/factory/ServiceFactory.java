package cn.mldn.mysql.factory;


import cn.mldn.mysql.service.impl.UserServiceImpl;

public class ServiceFactory {
	private static UserServiceImpl userServiceImpl = null;
	private static class ServiceFactorySon {
		private static final ServiceFactory serviceFactory = new ServiceFactory();
	}
	public static UserServiceImpl getUserServiceImpl() {
		if(userServiceImpl == null) {
			synchronized(ServiceFactory.class) {
				if(userServiceImpl == null) {
					userServiceImpl = new UserServiceImpl();
				}
			}
		}
		return userServiceImpl;
	}

	public static ServiceFactory getServiceFactory() { 
		return ServiceFactorySon.serviceFactory;
	}
	
}
