package server.interfaces;

import java.util.ArrayList;

import beans.information.Information;
import beans.information.OnlinePerson;
import beans.information.TeacherInfo;

/** 用于获取、修改用户基本个人信息 */
public interface InformationOperationInterface {
	
	/**
	 * 获取某人的个人信息
	 * @param username
	 * @return Information
	 */
	Information getPersonalInformation(String username);
	
	/**
	 * 修改用户的个人信息
	 * @param username
	 * @param info
	 * @return boolean
	 */
	boolean revisePersonalInformation(String username,Information info);
	
	/**
	 * 修改密码
	 * @param username
	 * @param passowrd
	 * @return boolean
	 */
	boolean revisePassword(String username, String passowrd);
	
	/**
	 * 获取在线用户列表
	 * @return ArrayList<OnlinePerson>
	 */
	ArrayList<OnlinePerson> getOnlinePersonList();
	
	/**
	 * 获取老师特有信息列表
	 * @return ArrayList<TeacherInfo>
	 */
	ArrayList<TeacherInfo> getAllTeacherInfoList();
	
	/**
	 * 获取内置兴趣定义列表
	 * @return ArrayList<String>
	 */
	ArrayList<String> getHobbyDefinitionList();
	
	/**
	 * 获取内置特长定义列表
	 * @return ArrayList<String>
	 */
	ArrayList<String> getAdeptnessDefinitionList();
	
	/**
	 * 搜索适合做项目的学生
	 * @param username
	 * @param adeptnessNumList
	 * @return ArrayList<Information>
	 */
	ArrayList<Information> getSuitableStudentList(String username, ArrayList<Integer> adeptnessNumList);
	
}
