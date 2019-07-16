package cn.mldn.mysql.service.impl;

import cn.mldn.mysql.factory.DAOFactory;
import cn.mldn.mysql.service.IUserService;
import cn.mldn.mysql.vo.User;
import cn.mldn.mysql.vo.Userinfo;


public class UserServiceImpl implements IUserService{

	/*
	 *   �����û�
	 *  �ɹ��򷵻�true��ʧ���򷵻�false
	 * (non-Javadoc)
	 * @see cn.mldn.mysql.service.IUserService#InsertUser(cn.mldn.mysql.vo.User, cn.mldn.mysql.vo.Userinfo)
	 */
	public boolean insertUser(User user, Userinfo userinfo) {
		
		if(DAOFactory.getDAOFactory().getIUserDAO().doInsertUser01(user, userinfo)) {
			if(DAOFactory.getDAOFactory().getIUserDAO().doInsertUser02()) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * ɾ���û�
	 * �ɹ��򷵻�true��ʧ���򷵻�false
	 */
	public boolean deleteUser(String userName) {
		if(DAOFactory.getDAOFactory().getIUserDAO().selectUserExist(userName)) {
			if(DAOFactory.getDAOFactory().getIUserDAO().doDeleteUser(userName))
				return true;
		}
		return false;
	}
	/*
	 *   �޸��û�����
	 *  ����trueΪ�޸ĳɹ���falseΪ�޸�ʧ��
	 */
	 
	public boolean updateUserPassword(String userName, String olduserPassword, String newuserPassword) {
		return DAOFactory.getDAOFactory().getIUserDAO().doUpdateUserPassword(userName, olduserPassword, newuserPassword);
	}


	
	
	
	
}
