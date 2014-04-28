package server.interfaces;

import java.util.ArrayList;
import java.util.Calendar;

import beans.announcement.Announcement;
import beans.announcement.FeedbackAnnouncement;


/** 用于看公告，回馈公告，发公告等操作 */
public interface AnnouncementOperationInterface {
	
	/**
	 * 获取可见公告列表
	 * @param grade
	 * @param username
	 * @return ArrayList<Announcement>
	 */
	ArrayList<Announcement> getAnnouncementList(int grade, String username);
	
	/**
	 * 回馈公告
	 * @param username
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean feedbackAnnouncement(String username,String senderUsername,Calendar time);
	
	/**
	 * 获取已发公告列表
	 * @param username
	 * @return ArrayList<FeedbackAnnouncement>
	 */
	ArrayList<FeedbackAnnouncement> getFeedbackAnnouncementList(String username);
	
	/**
	 * 发公告
	 * @param username
	 * @param announcement
	 * @return Calendar 系统收到该请求的时间（即数据库中对应条目的time项）
	 */
	Calendar sendAnnouncement(String username,Announcement announcement);
	
	/**
	 * 修改已发的公告
	 * @param username
	 * @param announcement
	 * @return boolean
	 */
	boolean reviseAnnouncement(String username,Announcement announcement);

}
