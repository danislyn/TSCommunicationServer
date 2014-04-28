package beans.information;

import java.io.Serializable;
import java.util.ArrayList;

import beans.teacher.Schedule;


public class TeacherInfo implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Information information;
	
	protected ArrayList<Schedule> scheduleList;
    
	
	/**
	 * 
	 */
	public TeacherInfo() {
		scheduleList = new ArrayList<Schedule>();
	}
	
	/**
	 * 
	 * @return Information
	 */
	public Information getInformation(){
		return information;
	}
	
	/**
	 * 
	 * @param information
	 */
	public void setInformation(Information information){
		this.information=information;
	}
	
	/**
	 * 
	 * @return ArrayList<Schedule>
	 */
	public ArrayList<Schedule> getScheduleList(){
		return scheduleList;
	}
	
	/**
	 * 
	 * @param scheduleList
	 */
	public void setScheduleList(ArrayList<Schedule> scheduleList){
		this.scheduleList=scheduleList;
	}

}

