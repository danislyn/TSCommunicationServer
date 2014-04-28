package beans.information;

import java.io.Serializable;
import java.util.ArrayList;

public class Information implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private String name;
	
	private int grade;
	
	private int klass;
	
	private ArrayList<String> hobbyList;
	
	private ArrayList<Integer> hobbyNumList;
	
	private ArrayList<String> adeptnessList;
	
	private ArrayList<Integer> adeptnessNumList;
	
	private ArrayList<String> favouriteTeacherList;
	
	private String signature;
	
	
	/**
	 * 
	 */
	public Information() {
		hobbyList = new ArrayList<String>();
		hobbyNumList = new ArrayList<Integer>();
		adeptnessList = new ArrayList<String>();
		adeptnessNumList = new ArrayList<Integer>();
		favouriteTeacherList = new ArrayList<String>();
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
	 * @return int
	 */
	public  int getKlass(){
		return klass;
	}
	
	/**
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getHobbyList(){
		return hobbyList;
	}
	
	/**
	 * 
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getHobbyNumList(){
		return hobbyNumList;
	}
	
	/**
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getAdeptnessList(){
		return adeptnessList;
	}
	
	/**
	 * 
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getAdeptnessNumList(){
		return adeptnessNumList;
	}
	
	/**
	 * 
	 * @return ArrayList<String> 
	 */
	public ArrayList<String> getFavouriteTeacherList(){
		return favouriteTeacherList;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getSignature(){
		return signature;
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
	 * @param klass
	 */
	public void setKlass(int klass){
		this.klass=klass;
	}
	
	/**
	 * 
	 * @param hobbyList
	 */
	public void setHobbyList(ArrayList<String> hobbyList){
		this.hobbyList=hobbyList;
	}
	
	/**
	 * 
	 * @param hobbyNumList
	 */
	public void setHobbyNumList(ArrayList<Integer> hobbyNumList){
		this.hobbyNumList = hobbyNumList;
	}
	
	/**
	 * 
	 * @param adeptnessList
	 */
	public void setAdeptnessList(ArrayList<String> adeptnessList){
		this.adeptnessList=adeptnessList;
	}
	
	/**
	 * 
	 * @param adeptnessNumList
	 */
	public void setAdeptnessNumList(ArrayList<Integer> adeptnessNumList){
		this.adeptnessNumList = adeptnessNumList;
	}
	
	/**
	 * 
	 * @param favouriteTeacherList
	 */
	public void setFavouriteTeacherList(ArrayList<String> favouriteTeacherList){
		this.favouriteTeacherList=favouriteTeacherList;
	}
	
	/**
	 * 
	 * @param signature
	 */
	public void setSignature(String signature){
		this.signature=signature;
	}

}
