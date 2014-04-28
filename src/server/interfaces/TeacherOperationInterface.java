package server.interfaces;

import java.util.ArrayList;

import beans.teacher.Schedule;


/** ������ʦ���еĲ�����Ŀǰֻ�������ճ̱���� */
public interface TeacherOperationInterface {
	
	/**
	 * ��ȡ�����ճ��б�
	 * @param username
	 * @return ArrayList<Schedule>
	 */
	ArrayList<Schedule> getScheduleList(String username);
	
	/**
	 * ��ȡָ�����µ��ճ��б�
	 * @param username
	 * @param year
	 * @param month
	 * @return ArrayList<Schedule>
	 */
	ArrayList<Schedule> getScheduleList(String username, int year, int month);
	
	/**
	 * �������ճ�
	 * @param username
	 * @param schedule
	 * @return boolean
	 */
	boolean addSchedule(String username, Schedule schedule);
	
	/**
	 * �޸������ճ�
	 * @param username
	 * @param schedule
	 * @return boolean
	 */
	boolean reviseSchedule(String username, Schedule schedule);
	
	/**
	 * ɾ��ĳ�ճ�
	 * @param username
	 * @param year
	 * @param month
	 * @param day
	 * @return boolean
	 */
	boolean deleteSchedule(String username, int year, int month, int day);
	
}
