package server.interfaces;

import java.util.ArrayList;
import java.util.Calendar;

import beans.post.Comment;
import beans.post.Post;


/** ���ڿ����������������Ȳ��� */
public interface PostOperationInterface {
	
	/**
	 * ��ȡ���а�������б�
	 * @return ArrayList<Post>
	 */
	ArrayList<Post> getAllPostList();
	
	/**
	 * ��ȡָ����������б�
	 * @param tag
	 * @return ArrayList<Post>
	 */
	ArrayList<Post> getPostList(int tag);
	
	/**
	 * ��������Ķ���
	 * @param tag
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean readPost(int tag,String senderUsername,Calendar time);
	
	/**
	 * �ظ�����
	 * @param username
	 * @param tag
	 * @param senderUsername
	 * @param time
	 * @param comment
	 * @return Calendar ϵͳ�յ��������ʱ�䣨�����ݿ��ж�Ӧ��Ŀ��time�
	 */
	Calendar replyPost(String username,int tag,String senderUsername,Calendar time,Comment comment);
	
	/**
	 * ����
	 * @param username
	 * @param authority
	 * @param post
	 * @return Calendar ϵͳ�յ��������ʱ�䣨�����ݿ��ж�Ӧ��Ŀ��time�
	 */
	Calendar sendPost(String username,int authority,Post post);
	
	/**
	 * �޸��Լ���������
	 * @param username
	 * @param authority
	 * @param post
	 * @return boolean
	 */
	boolean revisePost(String username,int authority,Post post);

}
