package beans.information;

import java.io.Serializable;

public class OnlinePerson implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private String name;
	
	private int authority;
	
	private int location;
	
	
	/**
	 * 
	 */
	public OnlinePerson() {
		
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
	public int getAuthority(){
		 return authority;
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
	 * @param name
	 */
	public void setName(String name){
		this.name=name;
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
	 * @param location
	 */
	public void setLocation(int location){
		this.location=location;
	}

}
