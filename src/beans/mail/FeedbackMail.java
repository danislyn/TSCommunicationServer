package beans.mail;

import java.util.ArrayList;

public class FeedbackMail extends Mail{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<String> feedbackList; 
	
	private ArrayList<String> receiverList;
	
	private int feedbackSum;
	
    private int receiverSum;
    
    
    /**
     * 
     */
    public FeedbackMail() {
    	feedbackList = new ArrayList<String>();
    	receiverList = new ArrayList<String>();
	}
    
    /**
     * 
     * @return int
     */
    public int getFeedbackSum(){
    	return feedbackSum;
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
     * @return int
     */
    public int getReceiverSum(){
    	return receiverSum ;
    }
    
    /**
     * 
     * @return ArrayList<String>
     */
    public ArrayList<String> getReceiverList(){
    	return receiverList;
    }
    
    /**
     * 
     * @param feedbackList
     */
    public void setFeedbackList(ArrayList<String> feedbackList){
    	this.feedbackList=feedbackList;
    	feedbackSum=feedbackList.size();
    }
    
    /**
     * 
     * @param receiverList
     */
    public void setReceiverList(ArrayList<String> receiverList){
    	this.receiverList=receiverList;
    	receiverSum=receiverList.size();
    }
    
    /**
     * 
     * @param feedbackSum
     */
    public void setFeedbackSum(int feedbackSum){
    	this.feedbackSum=feedbackSum;
    }
    
    /**
     * 
     * @param receiverSum
     */
    public void setReceiverSum(int receiverSum){
    	this.receiverSum=receiverSum;
    }

}
