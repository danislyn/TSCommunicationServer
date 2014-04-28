package server.interfaces;


import java.util.ArrayList;
import java.util.Calendar;

import beans.admin.Log;
import beans.admin.UserBaseInfo;
import beans.announcement.Announcement;


/** ���ڹ���Ա���еĲ�����Ŀǰ�ṩ���û��������ӹ����������������־���� */
public interface AdminOperationInterface {
	
	/**
	 * ��ȡ�����û��б�
	 * @return ArrayList<UserBaseInfo>
	 */
	ArrayList<UserBaseInfo> getUserList();
	
	/**
	 * �����û����루����������
	 * @param username
	 * @param password
	 * @return boolean
	 */
	boolean resetUserPassword(String username,String password);
	
	/**
	 * �����û����루����������
	 * @param usernameList
	 * @param password
	 * @return boolean
	 */
	boolean resetUserPassword(ArrayList<String> usernameList,String password);
	
	/**
	 * �޸��û����û������ģ�
	 * @param userBaseInfo
	 * @return boolean
	 */
	boolean reviseUser(UserBaseInfo userBaseInfo);
	
	/**
	 * ������û�
	 * @param user
	 * @return boolean
	 */
	boolean addUser(UserBaseInfo user);
	
	/**
	 * ������û�������������
	 * @param userList
	 * @return boolean
	 */
	boolean addUser(ArrayList<UserBaseInfo> userList);
	
	/**
	 * ɾ���û�������������
	 * @param username
	 * @return boolean
	 */
	boolean deleteUser(String username);
	
	/**
	 * ɾ���û�������������
	 * @param usernameList
	 * @return boolean
	 */
	boolean deleteUser(ArrayList<String> usernameList);
	
	/**
	 * ��ĳ�����ö�
	 * @param tag
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean makePostTop(int tag, String senderUsername, Calendar time);
	
	/**
	 * ȡ��ĳ�����ö�
	 * @param tag
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean cancelPostTop(int tag, String senderUsername, Calendar time);
	
	/**
	 * ɾ��ĳ����
	 * @param tag
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean deletePost(int tag, String senderUsername, Calendar time);
	
	/**
	 * ɾ��ĳ���ӵ�ĳ������
	 * @param tag
	 * @param postUsername
	 * @param postTime
	 * @param commentUsername
	 * @param commentTime
	 * @return boolean
	 */
	boolean deleteComment(int tag, String postUsername, Calendar postTime, String commentUsername, Calendar commentTime);
	
	/**
	 * ��ȡ���й����б�
	 * @return ArrayList<Announcement>
	 */
	ArrayList<Announcement> getAllAnnouncementList();
	
	/**
	 * ɾ��ĳ����
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean deleteAnnouncement(String senderUsername, Calendar time);
	
	/**
	 * ��ȡ������־�б�
	 * @param username
	 * @return ArrayList<Log>
	 */
	ArrayList<Log> getLogList(String username);
	
	/**
	 * ���Ӳ�����־
	 * @param username
	 * @param description
	 * @return boolean
	 */
	boolean addLog(String username, String description);
	
}
