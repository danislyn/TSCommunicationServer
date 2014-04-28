package server.interfaces;

import java.util.ArrayList;
import java.util.Calendar;

import beans.post.Comment;
import beans.post.Post;


/** 用于看帖，发帖，回帖等操作 */
public interface PostOperationInterface {
	
	/**
	 * 获取所有板块帖子列表
	 * @return ArrayList<Post>
	 */
	ArrayList<Post> getAllPostList();
	
	/**
	 * 获取指定板块帖子列表
	 * @param tag
	 * @return ArrayList<Post>
	 */
	ArrayList<Post> getPostList(int tag);
	
	/**
	 * 标记帖子阅读量
	 * @param tag
	 * @param senderUsername
	 * @param time
	 * @return boolean
	 */
	boolean readPost(int tag,String senderUsername,Calendar time);
	
	/**
	 * 回复帖子
	 * @param username
	 * @param tag
	 * @param senderUsername
	 * @param time
	 * @param comment
	 * @return Calendar 系统收到该请求的时间（即数据库中对应条目的time项）
	 */
	Calendar replyPost(String username,int tag,String senderUsername,Calendar time,Comment comment);
	
	/**
	 * 发帖
	 * @param username
	 * @param authority
	 * @param post
	 * @return Calendar 系统收到该请求的时间（即数据库中对应条目的time项）
	 */
	Calendar sendPost(String username,int authority,Post post);
	
	/**
	 * 修改自己发的帖子
	 * @param username
	 * @param authority
	 * @param post
	 * @return boolean
	 */
	boolean revisePost(String username,int authority,Post post);

}
