package database.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.connection.ConnectionStatement;
import database.connection.PublicConnection;

import beans.teacher.Schedule;
import server.interfaces.TeacherOperationInterface;


public class TeacherOperation implements TeacherOperationInterface {


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
	/** ��ȡ�����ճ��б� */
	public ArrayList<Schedule> getScheduleList(String username)
	{
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			try {				
				String string="select year, month, day, content, location from schedule_table where username='"+username+"'";
				rs=connStmt.statement.executeQuery(string);
					
				while(rs.next())
				{
					Schedule schedule=new Schedule();						
					schedule.setUsername(username);
						
					schedule.setYear(rs.getInt("year"));
					schedule.setMonth(rs.getInt("month"));						
					schedule.setDay(rs.getInt("day"));
					schedule.setContent(rs.getString("content"));
					schedule.setLocation(rs.getInt("location"));
						
					scheduleList.add(schedule);  //ȡ��һ����¼����װ��schedule�Ķ�������¼����					
				}	
				
			} catch(SQLException e){
				System.out.println("Exception when handling getScheduleList1......");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}
		return scheduleList;		
	}
	
	
	@Override
	/** ��ȡָ�����µ��ճ��б� */
	public ArrayList<Schedule> getScheduleList(String username, int year, int month) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			try {				
				String string="select day, content, location from schedule_table where username='"+username+"' and year="+year+" and month="+month+"";
				rs=connStmt.statement.executeQuery(string);
					
				while(rs.next())
				{
					Schedule schedule=new Schedule();
						
					schedule.setUsername(username);
					schedule.setYear(year);
					schedule.setMonth(month);
						
					schedule.setDay(rs.getInt("day"));
					schedule.setContent(rs.getString("content"));
					schedule.setLocation(rs.getInt("location"));
						
					scheduleList.add(schedule);  //ȡ��һ����¼����װ��schedule�Ķ�������¼����					
				}	
				
			} catch(SQLException e){
				System.out.println("Exception when handling getScheduleList2......");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}
		return scheduleList;	
	}


	@Override
	public boolean deleteSchedule(String username, int year, int month, int day) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		int flag=-1;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{ 
			try {	
				String string="delete from schedule_table where username='"+username+"' and year="+year+" and month="+month+" and day="+day+"";
				flag=connStmt.statement.executeUpdate(string);
				
			} catch(SQLException e){
				System.out.println("Exception when handling deleteSchedule......");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}	
		return (flag==1);
	}


	@Override
	//����Ҫ�ж��ճ̱��Ƿ���ڣ�ѧ����Boss�Ľ�����û���ճ̱���
	public boolean reviseSchedule(String username, Schedule schedule) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		int flag=-1;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			try {
				int year=schedule.getYear();
				int month=schedule.getMonth();
				int day=schedule.getDay();
				String content=schedule.getContent();
				int location = schedule.getLocation();
					
	            String s="update schedule_table set content='"+content+"', location="+location+" where username='"+username+"' and year="+year+" and month="+month+" and day="+day+"";
	            flag=connStmt.statement.executeUpdate(s);
									
			} catch(SQLException e){
				System.out.println("Exception when handling reviseSchedule......");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}		
		return (flag==1);
	}

	
	@Override
	public boolean addSchedule(String username, Schedule schedule) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		int flag=-1;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			try{					
				int year=schedule.getYear();
				int month=schedule.getMonth();
				int day=schedule.getDay();
				String content=schedule.getContent();
				int location=schedule.getLocation();
					
	            String s="insert into schedule_table (username, year, month, day, content, location) values('"+username+"', "+year+", "+month+", "+day+", '"+content+"', "+location+")";
	            flag=connStmt.statement.executeUpdate(s);
									
			}catch(SQLException e){
				System.out.println("Exception when handling addSchedule......");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}		
		return (flag==1);	
	}
	
}
