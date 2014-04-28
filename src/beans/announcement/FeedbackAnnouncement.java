package beans.announcement;

import java.util.ArrayList;

public class FeedbackAnnouncement extends Announcement{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> feedbackList;
	
	
	/**
	 * 
	 */
	public FeedbackAnnouncement() {
		feedbackList = new ArrayList<String>();
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getFeedbackSum(){
		return feedbackList.size();
	}
	
	/**
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getFeedbackList(){
		return feedbackList;
	}
	
	/**
	 * 
	 * @param feedbackList
	 */
	public void setFeedbackList(ArrayList<String> feedbackList){
		this.feedbackList=feedbackList;
	}

}
