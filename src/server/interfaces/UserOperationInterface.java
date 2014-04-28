package server.interfaces;

/** �����û��ĵ�¼���˳����� */
public interface UserOperationInterface {
	
	/**
	 * ���username�Ƿ��Ѵ���
	 * @param username
	 * @return boolean
	 */
	boolean isUsernameExisted(String username);
	
	/**
	 * ��¼����û����������Ƿ���ȷ������ȷ����¼�û���¼λ�ã��������û�Ȩ��
	 * @param username
	 * @param password
	 * @param location
	 * @return int Ȩ�ޣ�0-5��
	 */
	int loginCheck(String username,String password,int location);
	
	/**
	 * �û��˳�ϵͳ��ע�����ߣ�
	 * @param username
	 * @return boolean
	 */
	boolean logout(String username);

}
