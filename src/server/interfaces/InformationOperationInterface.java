package server.interfaces;

import java.util.ArrayList;

import beans.information.Information;
import beans.information.OnlinePerson;
import beans.information.TeacherInfo;

/** ���ڻ�ȡ���޸��û�����������Ϣ */
public interface InformationOperationInterface {
	
	/**
	 * ��ȡĳ�˵ĸ�����Ϣ
	 * @param username
	 * @return Information
	 */
	Information getPersonalInformation(String username);
	
	/**
	 * �޸��û��ĸ�����Ϣ
	 * @param username
	 * @param info
	 * @return boolean
	 */
	boolean revisePersonalInformation(String username,Information info);
	
	/**
	 * �޸�����
	 * @param username
	 * @param passowrd
	 * @return boolean
	 */
	boolean revisePassword(String username, String passowrd);
	
	/**
	 * ��ȡ�����û��б�
	 * @return ArrayList<OnlinePerson>
	 */
	ArrayList<OnlinePerson> getOnlinePersonList();
	
	/**
	 * ��ȡ��ʦ������Ϣ�б�
	 * @return ArrayList<TeacherInfo>
	 */
	ArrayList<TeacherInfo> getAllTeacherInfoList();
	
	/**
	 * ��ȡ������Ȥ�����б�
	 * @return ArrayList<String>
	 */
	ArrayList<String> getHobbyDefinitionList();
	
	/**
	 * ��ȡ�����س������б�
	 * @return ArrayList<String>
	 */
	ArrayList<String> getAdeptnessDefinitionList();
	
	/**
	 * �����ʺ�����Ŀ��ѧ��
	 * @param username
	 * @param adeptnessNumList
	 * @return ArrayList<Information>
	 */
	ArrayList<Information> getSuitableStudentList(String username, ArrayList<Integer> adeptnessNumList);
	
}
