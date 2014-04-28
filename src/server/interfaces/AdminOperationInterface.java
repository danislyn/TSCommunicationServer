package server.interfaces;


import java.util.ArrayList;
import java.util.Calendar;

import beans.admin.Log;
import beans.admin.UserBaseInfo;
import beans.announcement.Announcement;


/** 用于管理员特有的操作，目前提供了用户管理、帖子管理、公告管理、操作日志管理 */
public interface AdminOperationInterface {
	
	/**
	 * 获取所有用户列表
	 * @return ArrayList<UserBaseInfo>
	 */
	ArrayList<UserBaseInfo> getUserList();
	
	/**
	 * 重置用户密码（单个操作）
	 * @param username
	 * @param password
	 * @return boolean
	 */
	boolean resetUserPassword(String username,String password);
	
	/**
	 * 重置用户密码（批量操作）
	 * @param usernameList
	 * @param password
	 * @return boolean
	 */
	boolean resetUserPassword(ArrayList<String> usernameList,String password);
	
	/**
	 * 修改用户（用户名不改）
	 * @param userBaseInfo
	 * @return boolean
	 */
	boolean reviseUser(UserBaseInfo userBaseInfo);
	
	/**
	 * 添加新用户
	 * @param user
	 * @return boolean
	 */
	boolean addUser(UserBaseInfo user);
	
	/**
	 * 添加新用户（批量操作）
	 * @param userList
	 * @return boolean
	 */
	boolean addUser(ArrayList<UserBaseInfo> userList);
	
	/**
	 * 删除用户（单个操作）
	 * @param username
	 * @return boolean
	 */
	boolean deleteUser(String username);
	
	/**
	 * 删除用户（批量操作）
	 * @param usernameList
	 * @return boolean
	 */
	boolean deleteUser(ArrayList<String> usernameList);
	
	/**
	 * 将某帖子置顶
	 * @param tag
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean makePostTop(int tag, String senderUsername, Calendar time);
	
	/**
	 * 取消某帖子置顶
	 * @param tag
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean cancelPostTop(int tag, String senderUsername, Calendar time);
	
	/**
	 * 删除某帖子
	 * @param tag
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean deletePost(int tag, String senderUsername, Calendar time);
	
	/**
	 * 删除某帖子的某条评论
	 * @param tag
	 * @param postUsername
	 * @param postTime
	 * @param commentUsername
	 * @param commentTime
	 * @return boolean
	 */
	boolean deleteComment(int tag, String postUsername, Calendar postTime, String commentUsername, Calendar commentTime);
	
	/**
	 * 获取所有公告列表
	 * @return ArrayList<Announcement>
	 */
	ArrayList<Announcement> getAllAnnouncementList();
	
	/**
	 * 删除某公告
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean deleteAnnouncement(String senderUsername, Calendar time);
	
	/**
	 * 获取操作日志列表
	 * @param username
	 * @return ArrayList<Log>
	 */
	ArrayList<Log> getLogList(String username);
	
	/**
	 * 增加操作日志
	 * @param username
	 * @param description
	 * @return boolean
	 */
	boolean addLog(String username, String description);
	
}
