package beans.post;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Post implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String senderUsername;
	
	private String senderName;
	
	private String title;
	
	private String category;
	
	private Calendar time;
	
	private String content;
	
	private int readSum;
	
	private int commentsSum;
	
	private boolean isTop;
	
	private ArrayList<Comment> commentList;
	
	private int tag;
	
	
	/**
	 * 
	 */
	public Post() {
		commentList = new ArrayList<Comment>();
	}
	
	/**
	 * 
	 * @param Comment
	 */
	public void addComment(Comment Comment){
		commentList.add(Comment);
	}
	
	/**
	 * 
	 * @param senderUsername
	 */
	public void setSenderUsername(String senderUsername){
		this.senderUsername=senderUsername;
	}
	
	/**
	 * 
	 * @param senderName
	 */
	public void setSenderName(String senderName){
		this.senderName=senderName;
	}
	
	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title){
		this.title=title;
	}
	
	/**
	 * 
	 * @param time
	 */
	public void setTime(Calendar time){
		this.time=time;
	}
	
	/**
	 * 
	 * @param content
	 */
	public void setContent(String content){
		this.content=content;
	}
	
	/**
	 * 
	 * @param category
	 */
	public void setCategory(String category){
		this.category=category;
	}
	
	/**
	 * 
	 * @param readSum
	 */
	public void setReadSum(int readSum){
		this.readSum=readSum;
	}
	
	/**
	 * 
	 * @param commentsSum
	 */
	public void setCommentsSum(int commentsSum){
		this.commentsSum=commentsSum;
	}
	
	/**
	 * 
	 * @param isTop
	 */
	public void setIsTop(boolean isTop){
		this.isTop=isTop;
	}
	
	/**
	 * 
	 * @param commentList
	 */
	public void setCommentList(ArrayList<Comment> commentList){
		this.commentList=commentList;
	}
	
	/**
	 * 
	 * @param tag
	 */
	public void setTag(int tag){
		this.tag=tag;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getSenderUsername(){
		return senderUsername;	
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getSenderName(){
		return senderName;	
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getTitle(){
		return title;	
	}
	
	/**
	 * 
	 * @return Calendar
	 */
	public Calendar getTime(){
		return time;	
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getContent(){
		return content;	
	}
	
	/**
	 * 
	 * @return String 
	 */
	public String getCategory(){
		return category;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getReadSum(){
		return readSum;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getCommentsSum(){
		return commentsSum;
	}
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean getIsTop(){
		return isTop;
	}
	
	/**
	 * 
	 * @return ArrayList<Comment>
	 */
	public ArrayList<Comment> getCommentList(){
		return commentList;
	}

	/**
	 * 
	 * @return int
	 */
	public int getTag(){
		return tag;
	}
}
