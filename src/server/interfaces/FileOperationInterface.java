package server.interfaces;

import java.util.ArrayList;

import beans.file.FileBaseInfo;

/** 用于文件上传和下载 */
public interface FileOperationInterface {
	
	/**
	 * 上传个人头像文件信息
	 * @param fileBaseInfo
	 * @return boolean
	 */
	boolean uploadPersonalImage(FileBaseInfo fileBaseInfo);
	
	/**
	 * 下载个人头像文件信息
	 * @param username
	 * @return FileBaseInfo
	 */
	FileBaseInfo downloadPersonalImage(String username);
	
	/**
	 * 上传文件信息
	 * @param fileBaseInfo
	 * @return boolean
	 */
	boolean uploadSharedFile(FileBaseInfo fileBaseInfo);
	
	/**
	 * 下载某个文件信息
	 * @param username
	 * @param fileName
	 * @return FileBaseInfo
	 */
	FileBaseInfo downloadSharedFile(String username, String fileName);
	
	/**
	 * 下载所有文件信息列表
	 * @return ArrayList<FileBaseInfo>
	 */
	ArrayList<FileBaseInfo> downloadSharedFileList();

}
