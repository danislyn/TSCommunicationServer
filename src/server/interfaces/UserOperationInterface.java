package server.interfaces;

/** 用于用户的登录、退出操作 */
public interface UserOperationInterface {
	
	/**
	 * 检查username是否已存在
	 * @param username
	 * @return boolean
	 */
	boolean isUsernameExisted(String username);
	
	/**
	 * 登录检测用户名和密码是否正确，若正确，记录用户登录位置，并返回用户权限
	 * @param username
	 * @param password
	 * @param location
	 * @return int 权限（0-5）
	 */
	int loginCheck(String username,String password,int location);
	
	/**
	 * 用户退出系统（注销在线）
	 * @param username
	 * @return boolean
	 */
	boolean logout(String username);

}
