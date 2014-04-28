package server.interfaces;

import java.util.ArrayList;

import beans.file.FileBaseInfo;

/** �����ļ��ϴ������� */
public interface FileOperationInterface {
	
	/**
	 * �ϴ�����ͷ���ļ���Ϣ
	 * @param fileBaseInfo
	 * @return boolean
	 */
	boolean uploadPersonalImage(FileBaseInfo fileBaseInfo);
	
	/**
	 * ���ظ���ͷ���ļ���Ϣ
	 * @param username
	 * @return FileBaseInfo
	 */
	FileBaseInfo downloadPersonalImage(String username);
	
	/**
	 * �ϴ��ļ���Ϣ
	 * @param fileBaseInfo
	 * @return boolean
	 */
	boolean uploadSharedFile(FileBaseInfo fileBaseInfo);
	
	/**
	 * ����ĳ���ļ���Ϣ
	 * @param username
	 * @param fileName
	 * @return FileBaseInfo
	 */
	FileBaseInfo downloadSharedFile(String username, String fileName);
	
	/**
	 * ���������ļ���Ϣ�б�
	 * @return ArrayList<FileBaseInfo>
	 */
	ArrayList<FileBaseInfo> downloadSharedFileList();

}
