package server.interfaces;

import java.util.ArrayList;
import java.util.Calendar;

import beans.mail.AddressGroup;
import beans.mail.FeedbackMail;
import beans.mail.Mail;


/** �����ռ��䡢�����估��ַ������ */
public interface MailOperationInterface {
	
	/**
	 * ��ȡ�ռ����ż��б�
	 * @param username
	 * @return ArrayList<Mail>
	 */
	ArrayList<Mail> getReceivedMailList(String username);
	
	/**
	 * �����ż������͸�ָ���ˣ�
	 * @param username
	 * @param receivers
	 * @param mail
	 * @return Calendar ϵͳ�յ��������ʱ�䣨�����ݿ��ж�Ӧ��Ŀ��time�
	 */
	Calendar sendMail(String username,ArrayList<String> receivers,Mail mail);
	
	/**
	 * ���ż������͸�����ָ���������ˣ�
	 * @param username
	 * @param hobbyNumList
	 * @param adeptnessNumList
	 * @param mail
	 * @return Calendar ϵͳ�յ��������ʱ�䣨�����ݿ��ж�Ӧ��Ŀ��time�
	 */
	Calendar sendMail(String username, ArrayList<Integer> hobbyNumList, ArrayList<Integer> adeptnessNumList, Mail mail);
	
/*	//�ظ��ż�
	boolean replyMail(String username,String receiver,Mail mail);*/
	
	/**
	 * ����Ķ��ż��������ż���
	 * @param username
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean readMail(String username,String senderUsername,Calendar time);
	
	/**
	 * ɾ���ռ����е�ĳ�ż�
	 * @param username
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean deleteReceivedMail(String username,String senderUsername,Calendar time);
	
	/**
	 * ��ȡ�������ż��б�
	 * @param username
	 * @return ArrayList<FeedbackMail>
	 */
	ArrayList<FeedbackMail> getSentMailList(String username);
	
	/**
	 * ��ȡ�ż�����ϸ������Ϣ
	 * @param mail
	 * @return FeedbackMail
	 */
	FeedbackMail getFeedbackMail(FeedbackMail mail);
	    
	/**
	 * ɾ���������е��ż�
	 * @param username
	 * @param time
	 * @return boolean
	 */
	boolean deleteSentMail(String username,Calendar time);
	    
	/**
	 * ��ȡ˽�е�ַ���б�
	 * @param username
	 * @return ArrayList<AddressGroup>
	 */
    ArrayList<AddressGroup> getPrivateAddressGroupList(String username);
    
    /**
     * ��ȡȫ�ֵ�ַ���б�
     * @return ArrayList<AddressGroup>
     */
    ArrayList<AddressGroup> getGlobalAddressGroupList();
    
    /**
     * ɾ����ַ��ĳ��
     * @param username
     * @param groupName
     * @return boolean
     */
    boolean deleteGroup(String username,String groupName);
    
    /**
     * ���ӵ�ַ��ĳ���е���ϵ��
     * @param username
     * @param groupName
     * @param usernameList
     * @return boolean
     */
    boolean addAddress(String username,String groupName,ArrayList<String> usernameList);
    
    /**
     * ɾ����ַ��ĳ���е���ϵ��
     * @param username
     * @param groupName
     * @param usernameList
     * @return boolean
     */
    boolean deleteAddress(String username,String groupName,ArrayList<String> usernameList);
    
}
