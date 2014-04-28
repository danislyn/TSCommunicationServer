package beans.admin;

import java.io.Serializable;

public class LabInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;
	
	
	/**
	 * 
	 */
	public LabInfo() {
		
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
	 * @param content
	 */
	public void setContent(String content){
		this.content=content;
		
	}

}
