package cn.mldn.mysql.exception;


/*
 * 该类是对异常处理               当前还不熟悉！！！
 */
public class DAOException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DAOException() {}
	
	public DAOException(String message) {
		super(message);
	}
	 
	public DAOException(Throwable cause) {
		super(cause);
	}
	
	public DAOException(String message, Throwable cause) {
		super(message,cause);
	}

}
