package database.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.connection.ConnectionStatement;
import database.connection.PublicConnection;

import beans.file.FileBaseInfo;
import server.interfaces.FileOperationInterface;

public class FileOperation implements FileOperationInterface {

	private static String personalImageTable = "personal_image_table";
	private static String sharedFileTable = "shared_file_table";
	
	
	//与数据库取得连接，初始化statement
	private void open(ConnectionStatement connectionStatement){
		try{
			//与数据库取得连接，设置最大等待时间为1000毫秒
			connectionStatement.connection = PublicConnection.getConnection();
			
			//初始化statement
			if(connectionStatement.connection != null)
			{
				connectionStatement.statement = connectionStatement.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			}
			else 
			{
				System.out.println("no available connection in ConnectionPool, wait......");
			}
		}catch(SQLException e){
			//e.printStackTrace();
			System.out.println("Exception when open().....");
		}
			
	}
	
	//归还Connection
	private void close(ConnectionStatement connectionStatement){
		if(connectionStatement.statement != null)
			try {
				connectionStatement.statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("Exception when close().....");
			}
		
		if(connectionStatement.connection != null)
			PublicConnection.freeConnection(connectionStatement.connection);
	}
	
	
	@Override
	/** 插入文件信息到personal_image_table */
	public boolean uploadPersonalImage(FileBaseInfo fileBaseInfo) {
		// TODO Auto-generated method stub

		ConnectionStatement connStmt = new ConnectionStatement();
		boolean result = false;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{	
			String sqlString;
			
			try {
				//先清除上一次传的image
				sqlString = "delete from " + personalImageTable + " where username=" + "'" + fileBaseInfo.getContributorUsername() + "'";
				connStmt.statement.executeUpdate(sqlString);
				
				//插入这次的image
				sqlString = "insert into " + personalImageTable + " (username, image_name, image_path, image_size) " +
						"values('" + fileBaseInfo.getContributorUsername() + "', '" + fileBaseInfo.getFileName() + "', '"
						+ fileBaseInfo.getFilePath() + "', " + fileBaseInfo.getFileSize() + ")";
				connStmt.statement.executeUpdate(sqlString);
				
				result = true;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				result = false;
				System.out.println("Exception when handling uploadPersonalImage......");
				//e.printStackTrace();
			}

			close(connStmt);
		}
		return result;
	}

	@Override
	/** 从personal_image_table中得到某条文件信息 */
	public FileBaseInfo downloadPersonalImage(String username) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		FileBaseInfo fileBaseInfo = new FileBaseInfo();
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{	
			String sqlString;
			
			sqlString = "select * from " + personalImageTable + " where username=" + "'" + username + "'";
			try {
				rs = connStmt.statement.executeQuery(sqlString);
				
				if(rs.next())
				{
					fileBaseInfo.setContributorUsername(rs.getString("username"));
					fileBaseInfo.setFileName(rs.getString("image_name"));
					fileBaseInfo.setFilePath(rs.getString("image_path"));
					fileBaseInfo.setFileSize(rs.getInt("image_size"));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Exception when handling downloadPersonalImage......");
				//e.printStackTrace();
			}

			close(connStmt);
		}
		//else if connection==null	
		return fileBaseInfo;
	}

	@Override
	/** 插入文件信息到shared_file_table */
	public boolean uploadSharedFile(FileBaseInfo fileBaseInfo) {
		// TODO Auto-generated method stub

		ConnectionStatement connStmt = new ConnectionStatement();
		boolean result = false;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{		
			String sqlString = "insert into " + sharedFileTable + " (contributor_username, file_name, file_path, file_size) " +
					"values('" + fileBaseInfo.getContributorUsername() + "', '" + fileBaseInfo.getFileName() + "', '"
					+ fileBaseInfo.getFilePath() + "', " + fileBaseInfo.getFileSize() + ")";
			try {
				connStmt.statement.executeUpdate(sqlString);
				result = true;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				result = false;
				System.out.println("Exception when handling uploadPersonalImage......");
				//e.printStackTrace();
			}

			close(connStmt);
		}
		return result;
	}

	@Override
	/** 从shared_file_table中得到某条文件信息 */
	public FileBaseInfo downloadSharedFile(String username, String fileName) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		FileBaseInfo fileBaseInfo = new FileBaseInfo();
		ResultSet rs = null;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = "select file_path, file_size from " + sharedFileTable + " where contributor_username='" + username +"' and file_name='" + fileName + "'";
			
			try {
				rs = connStmt.statement.executeQuery(sqlString);
				
				if(rs.next())
				{
					fileBaseInfo.setContributorUsername(username);
					fileBaseInfo.setFileName(fileName);
					fileBaseInfo.setFilePath(rs.getString("file_path"));
					fileBaseInfo.setFileSize(rs.getInt("file_size"));
				}
			} catch (SQLException e) {
				System.out.println("Exception when handling downloadSharedFile......");
				//e.printStackTrace();
			}
		}
		
		return fileBaseInfo;
	}

	@Override
	/** 从shared_file_table中得到所有文件信息列表 */
	public ArrayList<FileBaseInfo> downloadSharedFileList() {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		ArrayList<FileBaseInfo> fileList = new ArrayList<FileBaseInfo>();
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = "select contributor_username, file_name, file_path, file_size from " + sharedFileTable;
			
			try {
				rs = connStmt.statement.executeQuery(sqlString);
				
				while(rs.next())
				{
					FileBaseInfo fileBaseInfo = new FileBaseInfo();
					fileBaseInfo.setContributorUsername(rs.getString("contributor_username"));
					fileBaseInfo.setFileName(rs.getString("file_name"));
					fileBaseInfo.setFilePath(rs.getString("file_path"));
					fileBaseInfo.setFileSize(rs.getInt("file_size"));
					fileList.add(fileBaseInfo);
				}
				
			} catch (SQLException e) {
				System.out.println("Exception when handling downloadSharedFileList......");
				//e.printStackTrace();
			}
		}
		
		return fileList;
	}

}
