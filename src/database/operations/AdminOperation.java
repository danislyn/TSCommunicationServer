package database.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import database.connection.ConnectionStatement;
import database.connection.PublicConnection;


import beans.admin.Log;
import beans.admin.UserBaseInfo;
import beans.announcement.Announcement;

import server.interfaces.AdminOperationInterface;


public class AdminOperation implements AdminOperationInterface {
		
	private static HashMap<Integer, String> postTableMap = new HashMap<Integer, String>();
	private static HashMap<Integer, String> commentTableMap = new HashMap<Integer, String>();

	
	private static String userTable="user_table";
	
	private static String announcementTable="announcement_table";
	
	//private static String announcementFeedbackTable="announcement_feedback_table";
	
	private static String logTable="log_table";
	
	//private static String labInfoTable="lab_info_table";
	

	static
	{
		postTableMap.put(1, "student_post_table");
		postTableMap.put(2, "major_teacher_post_table");
		postTableMap.put(3, "department_teacher_post_table");
		postTableMap.put(4, "admin_teacher_post_table");
		
		commentTableMap.put(1, "student_comment_table");
		commentTableMap.put(2, "major_teacher_comment_table");
		commentTableMap.put(3, "department_teacher_comment_table");
		commentTableMap.put(4, "admin_teacher_comment_table");
	}

	
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
	public ArrayList<UserBaseInfo> getUserList() {
		// TODO Auto-generated method stub
		
		ArrayList<UserBaseInfo> userList=new ArrayList<UserBaseInfo>();
		
		ConnectionStatement connStmt=new ConnectionStatement();
		ResultSet rs=null;
		
		open(connStmt);
		
		if(connStmt.connection!=null){
			try{
				
				String sql="select username, password, authority, name, grade from "+userTable;
				rs = connStmt.statement.executeQuery(sql);
						
				while(rs.next()){
					UserBaseInfo userBaseInfo=new UserBaseInfo();
					
					String username = rs.getString("username");
					String password = rs.getString("password");
					int authority = rs.getInt("authority");
					String name = rs.getString("name");
					int grade = rs.getInt("grade");
					
					userBaseInfo.setUsername(username);
					userBaseInfo.setPassword(password);
					userBaseInfo.setAuthority(authority);
					userBaseInfo.setName(name);
					userBaseInfo.setGrade(grade);
					
					userList.add(userBaseInfo);
					}
				}
			catch(SQLException e){
				System.out.println("exception when getUserList......");
				//e.printStackTrace();
				}
			close(connStmt);
		}
		return userList;
	}

	
	
	@Override
	public boolean resetUserPassword(String username, String password) {
		// TODO Auto-generated method stub
		
		boolean result=false;
		ConnectionStatement connStmt=new ConnectionStatement();
		
		open(connStmt);
		
		if(connStmt.connection!=null){
			try{
				String sql="update "+userTable+" set password='"+password+"' where username='"+username+"'";
				connStmt.statement.executeUpdate(sql);
				result = true;
				}
			catch(SQLException e){
				System.out.println("exception when resetUserPassword1......");
				//e.printStackTrace();
				result=false;
				}
			close(connStmt);
			}
		return result;
		}
	

	
	@Override
	public boolean resetUserPassword(ArrayList<String> usernames, String password) {
		// TODO Auto-generated method stub
		
		boolean result=true;
		ConnectionStatement connStmt=new ConnectionStatement();
		
		open(connStmt);
		
		if(connStmt.connection!=null){
			try{
				for(int index=0;index<usernames.size();index++){
					String username=usernames.get(index);
					String sql="update "+userTable+" set password='"+password+"' where username='"+username+"'";
					connStmt.statement.executeUpdate(sql);
					}
				}
			catch(SQLException e){
				System.out.println("exception when resetUserPassword2......");
				//e.printStackTrace();
				result=false;
				}
			close(connStmt);
			}
		return result;
		}

	
	//前置条件：不改username
	@Override
	public boolean reviseUser(UserBaseInfo userBaseInfo) {
		// TODO Auto-generated method stub
		
		boolean result = false;
		ConnectionStatement connStmt = new ConnectionStatement();
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = null;
			
			String username = userBaseInfo.getUsername();
			String password = userBaseInfo.getPassword();
			String name = userBaseInfo.getName();
			int authority = userBaseInfo.getAuthority();
			int grade = userBaseInfo.getGrade();
			
			try {
				sqlString = "update " + userTable + " set password='" + password + "', name='" + name + "', authority=" + authority + ", grade=" +
							grade + " where username='" + username + "'";
				
				connStmt.statement.executeUpdate(sqlString);
				result = true;
				
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("exception when reviseUser......");
				//e.printStackTrace();
				result=false;
			}
			
			close(connStmt);
		}		
		return result;
	}
	
	
	@Override
	public boolean addUser(UserBaseInfo user) {
		// TODO Auto-generated method stub
		
		boolean result = false;
		ConnectionStatement connStmt=new ConnectionStatement();
		open(connStmt);
		
		if(connStmt.connection!=null){
			try{
				String username = user.getUsername();
				String password = user.getPassword();
				int authority = user.getAuthority();
				String name = user.getName();
				int grade = user.getGrade();
				
				String sql="insert into "+userTable+" (username,password,authority,name,grade) values('"+username+"','"+password+"',"+authority+",'"+name+"',"+grade+");";
				connStmt.statement.executeUpdate(sql);
				
				result = true;
				}
			catch(SQLException e){
				System.out.println("exception when addUser1......");
				//e.printStackTrace();
				result=false;
				}
			close(connStmt);
			}
		return result;
		}
	
	
	@Override
	public boolean addUser(ArrayList<UserBaseInfo> userList)
	{
		boolean result = false;
		ConnectionStatement connStmt = new ConnectionStatement();
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = null;
			
			for(int i=0; i<userList.size(); i++)
			{
				UserBaseInfo user = userList.get(i);
			
				try {									
					String username = user.getUsername();
					String password = user.getPassword();
					int authority = user.getAuthority();
					String name = user.getName();
					int grade = user.getGrade();
					
					sqlString="insert into "+userTable+" (username,password,authority,name,grade) values('"+username+"','"+password+"',"+authority+",'"+name+"',"+grade+");";
					connStmt.statement.executeUpdate(sqlString);											
				
				} catch (SQLException e) {
					// TODO: handle exception
					System.out.println("exception when addUser2......");
					//e.printStackTrace();
					//result = false;  //????????
				}
			}
			result = true;
			
			close(connStmt);
		}
		return result;
	}
	

	@Override
	public boolean deleteUser(String username) {
		// TODO Auto-generated method stub
		
		boolean result=false;
		ConnectionStatement connStmt=new ConnectionStatement();
		
		open(connStmt);
		
		if(connStmt.connection!=null){
			try{
				String sql="delete from "+userTable+" where username='"+username+"'";
				connStmt.statement.executeUpdate(sql);
				result = true;
				}
			catch(SQLException e){
				System.out.println("exception when deleteUser1......");
				//e.printStackTrace();
				result=false;
				}
			close(connStmt);
			}
		return result;
		}

	
	
	@Override
	public boolean deleteUser(ArrayList<String> usernames) {
		// TODO Auto-generated method stub
		
		boolean result=true;
		ConnectionStatement connStmt=new ConnectionStatement();
		
		open(connStmt);
		
		if(connStmt.connection!=null){
			try{
				for(int index=0;index<usernames.size();index++){
					String username=usernames.get(index);
					String sql="delete from "+userTable+" where username='"+username+"'";
					connStmt.statement.executeUpdate(sql);
					}
				}
			catch(SQLException e){
				result=false;
				System.out.println("exception when deleteUser2......");
				//e.printStackTrace();
				}
			close(connStmt);
			}
		return result;
		}


	
	@Override
	public boolean makePostTop(int tag, String senderUsername, Calendar time) {
		// TODO Auto-generated method stub
		
		boolean result=false;
		ConnectionStatement connStmt=new ConnectionStatement();
		
		open(connStmt);
		
		if(connStmt.connection!=null){
			
			java.util.Date date = time.getTime();
			java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());
			String postTableName=postTableMap.get(tag);
			
			try{
				String sql=null;
				//判断哪一类型的帖子
				sql="update "+postTableName+" set is_top=1 where sender_username='"+senderUsername+"' and time='"+ts+"'";
				
				connStmt.statement.executeUpdate(sql);
				result = true;
				}
			catch(SQLException e){
				result=false;
				System.out.println("exception when makePostTop......");
				//e.printStackTrace();				
				}
			close(connStmt);
			}
		return result;
		}

	
		
	@Override
	public boolean cancelPostTop(int tag, String senderUsername, Calendar time) {
		// TODO Auto-generated method stub
		
		boolean result=false;
		ConnectionStatement connStmt=new ConnectionStatement();
		
		open(connStmt);
		
		if(connStmt.connection!=null){
			
			java.util.Date date = time.getTime();
			java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());
			String postTableName=postTableMap.get(tag);
			
			try{
				String sql=null;
				
				sql="update "+postTableName+" set is_top=0 where sender_username='"+senderUsername+"' and time='"+ts+"'";
				
				connStmt.statement.executeUpdate(sql);
				result = true;
				}
			catch(SQLException e){
				result=false;
				System.out.println("exception when cancelPostTop......");
				//e.printStackTrace();				
				}
			close(connStmt);
			}
			return result;
		}

	
	
	@Override
	public boolean deletePost(int tag, String senderUsername, Calendar time) {
		// TODO Auto-generated method stub
		
		boolean result=false;	
		ConnectionStatement connStmt=new ConnectionStatement();
		
		open(connStmt);
		
		if(connStmt.connection!=null){
			
			java.util.Date date = time.getTime();
			java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());
			String postTableName=postTableMap.get(tag);
			
			try{
				String sql=null;
					
				sql="delete from "+postTableName+" where sender_username='"+senderUsername+"' and time='"+ts+"'";
				connStmt.statement.executeUpdate(sql);
					
				result = true;
				}
			catch(SQLException e){
				result=false;
				System.out.println("exception when deletePost......");
				//e.printStackTrace();				
				}
			close(connStmt);
		}
		return result;
	}

	

	@Override
	public boolean deleteComment(int tag, String postUsername,
			Calendar postTime, String commentUsername, Calendar commentTime) {
		// TODO Auto-generated method stub
		
		String sql=null;
		ResultSet rs=null;
		boolean result=false;
		ConnectionStatement connStmt=new ConnectionStatement();
		
		open(connStmt);
		
		if(connStmt.connection!=null){
			
			int p_id=0;

			java.util.Date Podate = postTime.getTime();
			java.sql.Timestamp Pots = new java.sql.Timestamp(Podate.getTime());
					
			java.util.Date Codate = commentTime.getTime();
			java.sql.Timestamp Cots = new java.sql.Timestamp(Codate.getTime());
			
			String postTableName=postTableMap.get(tag);
			String commentTableName=commentTableMap.get(tag);
			
			try{
				
				sql="select p_id from "+postTableName+" where sender_username='"+postUsername+"' and time='"+Pots+"'";
				
				rs=connStmt.statement.executeQuery(sql);
				if(rs.next()){
					p_id=rs.getInt("p_id");
				}
				
				sql="delete from "+commentTableName+" where p_id="+p_id+" and commenter_username='"+commentUsername+"' and time='"+Cots+"'";
				connStmt.statement.executeUpdate(sql);
				result=true;
					
				}
			catch(SQLException e){
				result = false;
				System.out.println("exception when deleteComment......");
				//e.printStackTrace();
			}
			close(connStmt);
		}
		return result;
	}


	
	@Override
	public ArrayList<Announcement> getAllAnnouncementList() {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt=new ConnectionStatement();
		ResultSet rs=null;
		ArrayList<Announcement> allAnnouncementList=new ArrayList<Announcement>();
		
		open(connStmt);
		
		if(connStmt.connection!=null){
			String sql;
			
			try{			
				ResultSet subRs = null;
				Statement subStatement = connStmt.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
								
				//获得公告列表
				sql="select sender_username, title, category, grade, time, content from "+announcementTable;
				rs=connStmt.statement.executeQuery(sql);
				
				while(rs.next()){
					Announcement announcement=new Announcement();
					
					String senderUsername=rs.getString("sender_username");
					String title=rs.getString("title");
					String category=rs.getString("category");
					int grade=rs.getInt("grade");
					java.sql.Timestamp ts=rs.getTimestamp("time");
					String content=rs.getString("content");
							
					announcement.setSenderUsername(senderUsername);
					announcement.setTitle(title);
					announcement.setCategory(category);
					Calendar time=Calendar.getInstance();
					time.setTime(ts);
					announcement.setTime(time);
					announcement.setContent(content);
					announcement.setGrade(grade);
					announcement.setIsFeedback(false);
					
					//查name
					sql = "select name from "+userTable+" where username='"+senderUsername+"'";
					subRs=subStatement.executeQuery(sql);
					if(subRs.next()){
						String name = subRs.getString("name");
						announcement.setSenderName(name);
					}
					
					//announcement对象初始化全部完成
					allAnnouncementList.add(announcement);
					}
				}
			catch(SQLException e){
				System.out.println("Exception when handling getAnnouncementList......");
				//e.printStackTrace();
				}
			close(connStmt);
			}
		return allAnnouncementList;
		}

	

	@Override
	public boolean deleteAnnouncement(String senderUsername, Calendar time) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt=new ConnectionStatement();
		boolean result = false;
		String sql=null;
		
		open(connStmt);
			
		if(connStmt.connection!=null){
			
			try{
				java.util.Date date= time.getTime();
				java.sql.Timestamp ts =new java.sql.Timestamp(date.getTime());
				
				sql="delete from "+announcementTable+" where sender_username='"+senderUsername+"' and time='"+ts+"'";
				connStmt.statement.executeUpdate(sql);
				
				result=true;			
			}		
			catch(SQLException e){
				result = false;
				System.out.println("exception when deleteAnnouncement......");
				//e.printStackTrace();
			}
			close(connStmt);
		}
		
		return result;
	}

	
	
	@Override
	public ArrayList<Log> getLogList(String username) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt=new ConnectionStatement();
		ResultSet rs=null;
		ArrayList<Log> logList=new ArrayList<Log>();
		
		open(connStmt);
		
		if(connStmt.connection!=null){
			try{
				String sql="select time, description from "+logTable+" where username='"+username+"'";
				rs=connStmt.statement.executeQuery(sql);
				
				while(rs.next()){	
					Log log=new Log();
					
					java.sql.Timestamp ts=rs.getTimestamp("time");
					Calendar time=Calendar.getInstance();
					time.setTime(ts);
				
					String description =rs.getString("description");
					
					log.setUsername(username);
					log.setOperationDescription(description);
					log.setOperationTime(time);
					
					logList.add(log);
					}
				}
				catch(SQLException e){
					System.out.println("exception when getLogList......");
					//e.printStackTrace();
				}
				close(connStmt);
		}
		return logList;
	}

	
	
	@Override
	public boolean addLog(String username, String description) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt=new ConnectionStatement();
		boolean result=false;

		open(connStmt);
		
		if(connStmt.connection!=null){
			try{
				//获得系统时间
				Calendar t=Calendar.getInstance();
				java.util.Date date = t.getTime();
				java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
				
				String sql="insert into "+logTable+" (username,time,description) values ('"+username+"','"+time+"','"+description+"');";
				connStmt.statement.executeUpdate(sql);
				
				result=true;
			}
			catch(SQLException e){
				result = false;
				System.out.println("exception when addLog......");
				//e.printStackTrace();
			}
			close(connStmt);
		}
		
		return result;
	}

	
}
