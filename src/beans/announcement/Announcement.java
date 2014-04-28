package beans.announcement;

import java.io.Serializable;
import java.util.Calendar;

public class Announcement implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String senderUsername;
	
	private String senderName;
	
	private String title;
	
	private String category;
	
	private int grade;
	
	private Calendar time;
	
	private String content;
	
	private boolean isFeedback;
	
	
	/**
	 * 
	 */
	public Announcement() {
		
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
	 * @return String
	 */
	public String getCategory(){
		return category;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getGrade(){
		return grade;
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
	 * @return boolean
	 */
	public boolean getIsFeedback(){
		return isFeedback;
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
	 * @param category
	 */
	public void setCategory(String category){
		this.category=category;
	}
	
	/**
	 * 
	 * @param grade
	 */
	public void setGrade(int grade){
		this.grade=grade;
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
	 * @param isFeedback
	 */
	public void setIsFeedback(boolean isFeedback){
		this.isFeedback=isFeedback;
	}

}
