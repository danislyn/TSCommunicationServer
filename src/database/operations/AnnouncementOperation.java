package database.operations;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import database.connection.ConnectionStatement;
import database.connection.PublicConnection;



import beans.announcement.Announcement;
import beans.announcement.FeedbackAnnouncement;
import server.interfaces.AnnouncementOperationInterface;

public class AnnouncementOperation implements AnnouncementOperationInterface {

	private static String announcementTable="announcement_table";
	
	private static String announcementFeedbackTable="announcement_feedback_table";
	
	private static String userTable="user_table";
	

	//与数据库取得连接，初始化statement
	private void open(ConnectionStatement connectionStatement){
		try {
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
			System.out.println("Exception when open().....");
			//e.printStackTrace();
		}
			
	}
	
	//归还Connection
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
	public ArrayList<Announcement> getAnnouncementList(int grade, String username) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt=new ConnectionStatement();
		ResultSet rs=null;
		ArrayList<Announcement> announcementList=new ArrayList<Announcement>();
		
		open(connStmt);
		
		if(connStmt.connection!=null){
			String sql;
			try{
				Statement subStatement = connStmt.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet subRS = null;
				
				//获得公告列表
				sql="select * from "+announcementTable+" where grade="+grade;
				
				System.out.println("grade:               "+grade);
				rs=connStmt.statement.executeQuery(sql);
				
				//System.out.println("rs:                        "+rs.next());
				while(rs.next())
				{
					
					Announcement announcement=new Announcement();
					int aId = rs.getInt("a_id");
					String senderUsername=rs.getString("sender_username");
					String title=rs.getString("title");
					String category=rs.getString("category");
					java.sql.Timestamp ts=rs.getTimestamp("time");
					String content=rs.getString("content");
					
					String senderName = null;
					//查name
					sql = "select name from " + userTable + " where username='" + senderUsername + "'";
					subRS = subStatement.executeQuery(sql);
					if(subRS.next())
						senderName = subRS.getString("name");
					
					announcement.setSenderUsername(senderUsername);
					announcement.setSenderName(senderName);
					announcement.setTitle(title);
					announcement.setCategory(category);
					announcement.setGrade(grade);
					announcement.setContent(content);
					
					Calendar time=Calendar.getInstance();
					time.setTime(ts);	
					announcement.setTime(time);
					
					boolean isFeedback = false;
					//查announcement_feedback_table
					sql = "select * from " + announcementFeedbackTable + " where a_id=" + aId + " and username='" + username + "'";
					subRS = subStatement.executeQuery(sql);
					if(subRS.next())
						isFeedback = true;
					
					announcement.setIsFeedback(isFeedback);
					
					//announcement对象初始化全部完成
					announcementList.add(announcement);
					
				}
				//System.out.print("size:             "+announcementList.size());
			}
			catch(SQLException e){
				System.out.println("Exception when handling getAnnouncementList......");
				//e.printStackTrace();
				}
			close(connStmt);
			}
		return announcementList;
		}

	
	@Override
	public boolean feedbackAnnouncement(String username, String senderUsername, Calendar time) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt=new ConnectionStatement();
		ResultSet rs=null;
		boolean result=false;
		
		open(connStmt);
		
		if(connStmt.connection!=null){
			String sql=null;
			try{
				java.util.Date date = time.getTime();
				java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());						
				
				//查找相应的公告id
				sql="select a_id from "+announcementTable+" where sender_username='"+senderUsername+"' and time='"+ts+"'";
				rs=connStmt.statement.executeQuery(sql);
				
				
				int aId=0;
				if(rs.next()){
					aId=rs.getInt("a_id");
				System.out.println("a:"+aId);}
				
				if(aId != 0)
				{
					//插入到announcement_feedback_table
					sql = "insert into " + announcementFeedbackTable + " (a_id, username) values(" + aId + ", '" + username + "')";
					connStmt.statement.executeUpdate(sql);
					
					result = true;
				}
			}
			catch(SQLException e){
				result = false;
				System.out.println("exception when feedbackAnnouncement......");
				//e.printStackTrace();
			}
			close(connStmt);
		}
		
		return result;
	}


	
	@Override
	public ArrayList<FeedbackAnnouncement> getFeedbackAnnouncementList(String username) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt=new ConnectionStatement();
		ResultSet rs=null;
		ArrayList<FeedbackAnnouncement> feedbackAnnouncementList=new ArrayList<FeedbackAnnouncement>();
				
		open(connStmt);
			
		if(connStmt.connection!=null){
			String sql=null;
			
			try{
				Statement subStatement = connStmt.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet subRS = null;
				
				String name = null;				
				
				//查name
				sql="select name from "+userTable+" where username='"+username+"'";
				rs=connStmt.statement.executeQuery(sql);
				if(rs.next())
					name=rs.getString("name");
				
				//查announcement_table
				sql="select a_id,title,category,grade,time,content from "+announcementTable+" where sender_username='"+username+"'";
				rs=connStmt.statement.executeQuery(sql);
				
				while(rs.next()){
					FeedbackAnnouncement feedbackAnnouncement=new FeedbackAnnouncement();
					
					int a_id=rs.getInt("a_id");
					String title=rs.getString("title");
					String category=rs.getString("category");
					int grade=rs.getInt("grade");
								
					//时间
					java.sql.Timestamp ts=rs.getTimestamp("time");
					Calendar time=Calendar.getInstance();
					time.setTime(ts);		
					
					String content=rs.getString("content");
					
					feedbackAnnouncement.setSenderUsername(username);
					feedbackAnnouncement.setSenderName(name);
					feedbackAnnouncement.setTitle(title);
					feedbackAnnouncement.setCategory(category);
					feedbackAnnouncement.setGrade(grade);
					feedbackAnnouncement.setTime(time);
					feedbackAnnouncement.setContent(content);
					feedbackAnnouncement.setIsFeedback(false);
					
					ArrayList<String> feedbackList = new ArrayList<String>();
					//获得回馈名单
					sql="select username from "+announcementFeedbackTable+" where a_id="+a_id;
					subRS = subStatement.executeQuery(sql);
						
					while(subRS.next()){
						String userName=subRS.getString("username");					
						feedbackList.add(userName);
						}
					
					feedbackAnnouncement.setFeedbackList(feedbackList);
					
					//feedbackAnnouncement对象初始全部完成
					feedbackAnnouncementList.add(feedbackAnnouncement);
					}
				
				}
			
			catch(SQLException e){
				System.out.println("exception when getfeedbackannouncementlist......");
				//e.printStackTrace();
			}
			close(connStmt);
		}
		
		return feedbackAnnouncementList;
	}

	
	@Override
	public Calendar sendAnnouncement(String username, Announcement announcement) {
		// TODO Auto-generated method stub

		ConnectionStatement connStmt=new ConnectionStatement();
		Calendar result=null;
		
		open(connStmt);	
		
		if(connStmt.connection!=null){
			try{				
				String senderUsername=username;
				String title=announcement.getTitle();
				String category=announcement.getCategory();
				int grade=announcement.getGrade();
				
				Calendar calendar=Calendar.getInstance();
				java.util.Date date = calendar.getTime();
				java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());
								
				String content=announcement.getContent();
		
				String sql="insert into "+announcementTable+" (sender_username,title,category,grade,time,content) values('"+senderUsername+"','"+title+"','"+category+"',"+grade+",'"+ts+"','"+content+"');";
				connStmt.statement.executeUpdate(sql);
				
				result = calendar;
				}
			catch(SQLException e){
				result=null;
				System.out.println("exception when sendAnnouncement......");
				//e.printStackTrace();			
				}
			close(connStmt);
			}
		return result;
		}
		
	

	@Override
	public boolean reviseAnnouncement(String username, Announcement announcement) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt=new ConnectionStatement();
		boolean result=false;
		
		open(connStmt);
			
		if(connStmt.connection!=null){			
			try{
				
				String title=announcement.getTitle();
				String category=announcement.getCategory();
				int grade=announcement.getGrade();
				
				Calendar time=announcement.getTime();
				java.util.Date date = time.getTime();
				java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());
								
				String content=announcement.getContent();
				
				String sql="update "+announcementTable+" set title='"+title+"',category='"+category+"',grade="+grade+",content='"+content+"'"+" where sender_username='"+username+"' and time='"+ts+"'";
				connStmt.statement.executeUpdate(sql);
				
				result = true;
			}		
			catch(SQLException e){
				result=false;
				System.out.println("exception when sendAnnouncement......");
				//e.printStackTrace();			
				}
			close(connStmt);
		}
		return result;
	}
	
	
}
