package server.interfaces;

import java.util.ArrayList;
import java.util.Calendar;

import beans.mail.AddressGroup;
import beans.mail.FeedbackMail;
import beans.mail.Mail;


/** 用于收件箱、发件箱及地址簿管理 */
public interface MailOperationInterface {
	
	/**
	 * 获取收件箱信件列表
	 * @param username
	 * @return ArrayList<Mail>
	 */
	ArrayList<Mail> getReceivedMailList(String username);
	
	/**
	 * 发送信件（发送给指定人）
	 * @param username
	 * @param receivers
	 * @param mail
	 * @return Calendar 系统收到该请求的时间（即数据库中对应条目的time项）
	 */
	Calendar sendMail(String username,ArrayList<String> receivers,Mail mail);
	
	/**
	 * 发信件（发送给符合指定条件的人）
	 * @param username
	 * @param hobbyNumList
	 * @param adeptnessNumList
	 * @param mail
	 * @return Calendar 系统收到该请求的时间（即数据库中对应条目的time项）
	 */
	Calendar sendMail(String username, ArrayList<Integer> hobbyNumList, ArrayList<Integer> adeptnessNumList, Mail mail);
	
/*	//回复信件
	boolean replyMail(String username,String receiver,Mail mail);*/
	
	/**
	 * 标记阅读信件（回馈信件）
	 * @param username
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean readMail(String username,String senderUsername,Calendar time);
	
	/**
	 * 删除收件箱中的某信件
	 * @param username
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean deleteReceivedMail(String username,String senderUsername,Calendar time);
	
	/**
	 * 获取发件箱信件列表
	 * @param username
	 * @return ArrayList<FeedbackMail>
	 */
	ArrayList<FeedbackMail> getSentMailList(String username);
	
	/**
	 * 获取信件的详细回馈信息
	 * @param mail
	 * @return FeedbackMail
	 */
	FeedbackMail getFeedbackMail(FeedbackMail mail);
	    
	/**
	 * 删除发件箱中的信件
	 * @param username
	 * @param time
	 * @return boolean
	 */
	boolean deleteSentMail(String username,Calendar time);
	    
	/**
	 * 获取私有地址簿列表
	 * @param username
	 * @return ArrayList<AddressGroup>
	 */
    ArrayList<AddressGroup> getPrivateAddressGroupList(String username);
    
    /**
     * 获取全局地址簿列表
     * @return ArrayList<AddressGroup>
     */
    ArrayList<AddressGroup> getGlobalAddressGroupList();
    
    /**
     * 删除地址簿某组
     * @param username
     * @param groupName
     * @return boolean
     */
    boolean deleteGroup(String username,String groupName);
    
    /**
     * 增加地址簿某组中的联系人
     * @param username
     * @param groupName
     * @param usernameList
     * @return boolean
     */
    boolean addAddress(String username,String groupName,ArrayList<String> usernameList);
    
    /**
     * 删除地址簿某组中的联系人
     * @param username
     * @param groupName
     * @param usernameList
     * @return boolean
     */
    boolean deleteAddress(String username,String groupName,ArrayList<String> usernameList);
    
}
