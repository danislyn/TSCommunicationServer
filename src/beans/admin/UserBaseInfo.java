package beans.admin;

import java.io.Serializable;

public class UserBaseInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private String password;
	
	private int authority;
	
	private String name;
	
	private int grade;
	
	
	/**
	 * 
	 */
	public UserBaseInfo() {
		
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
	 * @return String
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getAuthority(){
		return authority;
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
	 * @param password
	 */
	public void setPassword(String password){
		this.password=password;
	}
	
	/**
	 * 
	 * @param authority
	 */
	public void setAuthority(int authority){
		this.authority=authority;
	}
	
	/**
	 * 
	 * @param grade
	 */
	public void setGrade(int grade){
		this.grade=grade;
	}
	

}
