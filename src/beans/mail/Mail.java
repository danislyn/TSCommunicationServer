package beans.mail;

import java.io.Serializable;
import java.util.Calendar;

public class Mail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String senderUsername;
	
	private String senderName;
	
	private String title;
	
	private Calendar time;
	
	private String content;
	
	private boolean isRead;
	
	private boolean isNeedFeedback;
	
	
	/**
	 * 
	 */
	public Mail() {
		
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
	 * @return boolean
	 */
	public boolean getIsRead(){
		return isRead;
		
	}
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean getIsNeedFeedback(){
		return isNeedFeedback;
		
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
	 * @param isRead
	 */
	public void setIsRead(boolean isRead){
		this.isRead=isRead;
	}
	
	/**
	 * 
	 * @param isNeedFeedback
	 */
	public void setIsNeedFeedback(boolean isNeedFeedback){
		this.isNeedFeedback=isNeedFeedback;
	}

}
