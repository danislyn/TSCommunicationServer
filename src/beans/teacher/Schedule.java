package beans.teacher;

import java.io.Serializable;

public class Schedule implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private int year;
	
	private int month;
	
	private int day;
	
	private String content;
	
	private int location;
	
	
	/**
	 * 
	 */
	public Schedule() {
		
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getUsername(){
		return username;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getYear(){
		return year;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getMonth(){
		return month;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getDay(){
		return day;
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
	 * @return int
	 */
	public int getLocation(){
		return location;
	}
	
	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username){
		this.username=username;
	}
	
	/**
	 * 
	 * @param year
	 */
	public void setYear(int year){
		this.year=year;
	}
	
	/**
	 * 
	 * @param month
	 */
	public void setMonth(int month){
		this.month=month;
	}
	
	/**
	 * 
	 * @param day
	 */
	public void setDay(int day){
		this.day=day;
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
	 * @param location
	 */
	public void setLocation(int location){
		this.location=location;
	}

}
