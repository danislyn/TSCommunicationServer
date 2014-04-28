package server.interfaces;

import java.util.ArrayList;

import beans.teacher.Schedule;


/** 用于老师特有的操作，目前只给出了日程表管理 */
public interface TeacherOperationInterface {
	
	/**
	 * 获取所有日程列表
	 * @param username
	 * @return ArrayList<Schedule>
	 */
	ArrayList<Schedule> getScheduleList(String username);
	
	/**
	 * 获取指定年月的日程列表
	 * @param username
	 * @param year
	 * @param month
	 * @return ArrayList<Schedule>
	 */
	ArrayList<Schedule> getScheduleList(String username, int year, int month);
	
	/**
	 * 增加新日程
	 * @param username
	 * @param schedule
	 * @return boolean
	 */
	boolean addSchedule(String username, Schedule schedule);
	
	/**
	 * 修改已有日程
	 * @param username
	 * @param schedule
	 * @return boolean
	 */
	boolean reviseSchedule(String username, Schedule schedule);
	
	/**
	 * 删除某日程
	 * @param username
	 * @param year
	 * @param month
	 * @param day
	 * @return boolean
	 */
	boolean deleteSchedule(String username, int year, int month, int day);
	
}
