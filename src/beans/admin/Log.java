package beans.admin;

import java.io.Serializable;
import java.util.Calendar;

public class Log implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private Calendar operationTime;
	
	private String operationDescription;
	
	
	/**
	 * 
	 */
	public Log() {
		
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
	 * @return Calendar
	 */
	public Calendar getOperationTime(){
		return operationTime;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getOperationDescription(){
		return operationDescription;
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
	 * @param operationTime
	 */
	public void setOperationTime(Calendar operationTime){
		this.operationTime=operationTime;
	}
	
	/**
	 * 
	 * @param operationDescription
	 */
	public void setOperationDescription(String operationDescription){
		this.operationDescription=operationDescription;
	}

}
