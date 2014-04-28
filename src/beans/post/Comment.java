package beans.post;

import java.io.Serializable;
import java.util.Calendar;

public class Comment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String senderUsername;
	
	private String senderName;
	
	private Calendar time;
	
	private String content;
	
	
	/**
	 * 
	 */
	public Comment() {
		
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

}
