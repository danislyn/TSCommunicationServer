package server.service;

import java.util.HashMap;
import java.util.Map;

import database.operations.AdminOperation;
import database.operations.AnnouncementOperation;
import database.operations.FileOperation;
import database.operations.InformationOperation;
import database.operations.MailOperation;
import database.operations.PostOperation;
import database.operations.TeacherOperation;
import database.operations.UserOperation;


public class Main {
	
	/**
	 * 命令行模式main函数入口
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Object> interfaceInstance = new HashMap<String, Object>();		
		interfaceInstance.put("server.interfaces.AdminOperationInterface", new AdminOperation());
		interfaceInstance.put("server.interfaces.AnnouncementOperationInterface", new AnnouncementOperation());
		interfaceInstance.put("server.interfaces.InformationOperationInterface", new InformationOperation());
		interfaceInstance.put("server.interfaces.MailOperationInterface", new MailOperation());
		interfaceInstance.put("server.interfaces.PostOperationInterface", new PostOperation());
		interfaceInstance.put("server.interfaces.TeacherOperationInterface", new TeacherOperation());
		interfaceInstance.put("server.interfaces.UserOperationInterface", new UserOperation());		
		interfaceInstance.put("server.interfaces.FileOperationInterface", new FileOperation());
		
		Map<String, String> directoryInstance = new HashMap<String, String>();		
		//directoryInstance.put("uploadPersonalImage", "F:/TSCS/temp/image/");
		directoryInstance.put("uploadSharedFile", "F:/TSCS/temp/file/");
		
		Server server = new Server(interfaceInstance, directoryInstance);
		server.startService();
	}
}
