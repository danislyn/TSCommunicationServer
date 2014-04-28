package database.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import database.connection.ConnectionStatement;
import database.connection.PublicConnection;


import server.interfaces.UserOperationInterface;

public class UserOperation implements UserOperationInterface{

	//��Ҫ���ʵ����ű�
	private static String userTable = "user_table";
	private static String onlineUserTable = "online_user_table";
	
	
	//�����ݿ�ȡ�����ӣ���ʼ��statement
	private void open(ConnectionStatement connectionStatement){
		try {
			//�����ݿ�ȡ�����ӣ��������ȴ�ʱ��Ϊ1000����
			connectionStatement.connection = PublicConnection.getConnection();
			
			//��ʼ��statement
			if(connectionStatement.connection != null)
			{
				connectionStatement.statement = connectionStatement.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			}
			else 
			{
				System.out.println("no available connection in ConnectionPool, wait......");
			}
		}catch(SQLException e){			
			System.out.println("Exception when open().....");
			//e.printStackTrace();
		}
			
	}
	
	//�黹Connection
	private void close(ConnectionStatement connectionStatement){
		if(connectionStatement.statement != null)
			try {
				connectionStatement.statement.close();
			} catch (SQLException e) {
				System.out.println("Exception when close().....");
				//e.printStackTrace();				
			}
		
		if(connectionStatement.connection != null)
			PublicConnection.freeConnection(connectionStatement.connection);
	}
	
	
	@Override
	/** ����user_table��username */
	public boolean isUsernameExisted(String username) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		boolean result = false;
		
		open(connStmt);

		if(connStmt.connection != null)
		{
			String sqlString = "select username from " + userTable + " where username=" + "'" + username + "'";
			
			try {
				rs = connStmt.statement.executeQuery(sqlString);
				
				if(rs.next())
					result = true;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				result = false;
				System.out.println("Exception when handling isUsernameExisted......");			
				//e.printStackTrace();
			}
			
			close(connStmt);
		}
		return result;
	}

	@Override
	/** ��user_table��ȡ����Ӧ��Ŀ��authority����Ϊ����ֵ����������user_table�е�last_time��
	 *  �ٽ�username��location����online_user_table����ɾ�ظ���username��Ŀ�� */
	public int loginCheck(String username, String password, int location) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		int authority = -1;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = "select authority from " + userTable + " where username=" + "'" + username + "' and password=" + "'" + password +"'";
			
			try {
				rs = connStmt.statement.executeQuery(sqlString);
			
				if(rs.next())
				{
					authority = rs.getInt("authority");
				
					java.util.Date date = new Date();
					java.sql.Timestamp time = new Timestamp(date.getTime());
					
					sqlString = "update " + userTable + " set last_time='" + time + "' where username='" + username + "'";
					connStmt.statement.executeUpdate(sqlString);
					
					sqlString = "delete from " + onlineUserTable + " where username='" + username + "'";
					connStmt.statement.executeUpdate(sqlString);
				
					sqlString = "insert into " + onlineUserTable + " (username, location) values('" + username + "', " + location + ")";
					connStmt.statement.executeUpdate(sqlString);
				}		
			} catch (SQLException e) {
				authority = -1;
				System.out.println("Exception when handling loginCheck......");
				//e.printStackTrace();
			}
		
			close(connStmt);
		}
		return authority;	
	}

	@Override
	/** ɾ��online_user_table�еĶ�Ӧ��Ŀ */
	public boolean logout(String username) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		boolean result = false;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = "delete from " + onlineUserTable + " where username=" + "'" + username +"'";
			
			try {
				connStmt.statement.executeUpdate(sqlString);
				result = true;
				
			} catch (SQLException e) {
				result = false;
				System.out.println("Exception when handling logout......");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}
		return result;
	}

}
