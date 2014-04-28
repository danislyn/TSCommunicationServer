package beans.mail;

import java.io.Serializable;

public class Address implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private String name;
	
	private int grade;
	
	private boolean isTeacher;
	
	
	/**
	 * 
	 */
	public Address() {
		
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
	 * @return String
	 */
	public String getName(){
		return name;
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
	 * @return boolean
	 */
	public boolean getIsTeacher(){
		return isTeacher;
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
	 * @param name
	 */
	public void setName(String name){
		this.name=name;
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
	 * @param isTeacher
	 */
	public void setIsTeacher(boolean isTeacher){
		this.isTeacher=isTeacher;
	}

}
