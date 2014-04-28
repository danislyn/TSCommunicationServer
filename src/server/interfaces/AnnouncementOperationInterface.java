package server.interfaces;

import java.util.ArrayList;
import java.util.Calendar;

import beans.announcement.Announcement;
import beans.announcement.FeedbackAnnouncement;


/** ���ڿ����棬�������棬������Ȳ��� */
public interface AnnouncementOperationInterface {
	
	/**
	 * ��ȡ�ɼ������б�
	 * @param grade
	 * @param username
	 * @return ArrayList<Announcement>
	 */
	ArrayList<Announcement> getAnnouncementList(int grade, String username);
	
	/**
	 * ��������
	 * @param username
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean feedbackAnnouncement(String username,String senderUsername,Calendar time);
	
	/**
	 * ��ȡ�ѷ������б�
	 * @param username
	 * @return ArrayList<FeedbackAnnouncement>
	 */
	ArrayList<FeedbackAnnouncement> getFeedbackAnnouncementList(String username);
	
	/**
	 * ������
	 * @param username
	 * @param announcement
	 * @return Calendar ϵͳ�յ��������ʱ�䣨�����ݿ��ж�Ӧ��Ŀ��time�
	 */
	Calendar sendAnnouncement(String username,Announcement announcement);
	
	/**
	 * �޸��ѷ��Ĺ���
	 * @param username
	 * @param announcement
	 * @return boolean
	 */
	boolean reviseAnnouncement(String username,Announcement announcement);

}
