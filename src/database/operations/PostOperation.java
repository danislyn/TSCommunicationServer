package database.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import database.connection.ConnectionStatement;
import database.connection.PublicConnection;

import beans.post.Comment;
import beans.post.Post;
import server.interfaces.PostOperationInterface;

public class PostOperation implements PostOperationInterface {
	
	private static HashMap<Integer, String> postTableMap = new HashMap<Integer, String>();
	private static HashMap<Integer, String> commentTableMap = new HashMap<Integer, String>();
	private static HashMap<Integer, String> authorityPostTableMap = new HashMap<Integer, String>();
	
	//��̬��ʼ��postTableMap, commentTableMap, authorityPostTableMap
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
		
		authorityPostTableMap.put(0, "student_post_table");
		authorityPostTableMap.put(1, "major_teacher_post_table");
		authorityPostTableMap.put(2, "department_teacher_post_table");
		authorityPostTableMap.put(3, "department_teacher_post_table");
		authorityPostTableMap.put(4, "department_teacher_post_table");
		authorityPostTableMap.put(5, "admin_teacher_post_table");
	}
	
	
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
	/** �õ����а��������б����֮����null�ָ� */
	public ArrayList<Post> getAllPostList() {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		ArrayList<Post> allPostList = new ArrayList<Post>();
		   
		open(connStmt);
		
		if(connStmt.connection != null)
		{				
			String sqlString = null;
			String postTableName = null;
			String commentTableName = null;
			
			try {
				//��ʼ���Ӳ�ѯ����Ʒ
				ResultSet subRs = null;
				Statement subStatement = connStmt.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet subRs2 = null;
				Statement subStatement2 = connStmt.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				
				//forѭ�������β�����post_table
				for(int i=1; i<=4; i++)
				{
					postTableName = postTableMap.get(i);
					commentTableName = commentTableMap.get(i);
					
					//��post_table���õ�������Ϣ
					sqlString = "select * from " + postTableName;
					rs = connStmt.statement.executeQuery(sqlString);
				
					while(rs.next())
					{		
						int pId = rs.getInt("p_id");
						String senderUsername = rs.getString("sender_username");;
						String title = rs.getString("title");
						String category = rs.getString("category");
						
						java.util.Date date = rs.getTimestamp("time");
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						
						String content = rs.getString("content");
						int readSum = rs.getInt("read_sum");
						boolean isTop = rs.getBoolean("is_top");
						
						//��ʼ��һ��Post����
						Post post = new Post();
						post.setSenderUsername(senderUsername);
						post.setTitle(title);
						post.setCategory(category);
						post.setTime(calendar);
						post.setContent(content);
						post.setReadSum(readSum);
						post.setIsTop(isTop);
						
						
						//�Ӳ�ѯ����user_table����name
						sqlString = "select name from user_table where username='" + senderUsername + "'";
						String senderName = null;
						
						subRs = subStatement.executeQuery(sqlString);					
						if(subRs.next())
						{
							senderName = subRs.getString("name");
						}
						post.setSenderName(senderName);
						
						
						//�Ӳ�ѯ����comment_table
						ArrayList<Comment> commentList = new ArrayList<Comment>();				
						sqlString ="select commenter_username, time, content from " + commentTableName + " where p_id=" + pId;
						
						subRs = subStatement.executeQuery(sqlString);					
						while(subRs.next())
						{
							String commenterUsername = subRs.getString("commenter_username");
							
							java.util.Date subdate = subRs.getTimestamp("time");
							Calendar subcalendar = Calendar.getInstance();
							subcalendar.setTime(subdate);
							
							String commentContent = subRs.getString("content");
							
							//��ʼ��һ��Comment����
							Comment comment = new Comment();
							comment.setSenderUsername(commenterUsername);
							comment.setTime(subcalendar);
							comment.setContent(commentContent);
							
						
							//�Ӳ�ѯ2����user_table����name
							sqlString = "select name from user_table where username='" + commenterUsername + "'";
							String commenterName = null;
							
							subRs2 = subStatement2.executeQuery(sqlString);
							if(subRs2.next())
							{
								commenterName = subRs2.getString("name");
							}
							comment.setSenderName(commenterName);
							
							//comment��ʼ��ȫ�����
							commentList.add(comment);
							
						}//end while for comment_table Query
						
						post.setCommentList(commentList);
						post.setCommentsSum(commentList.size());
						
						//post��ʼ��ȫ�����
						allPostList.add(post);
					
					}//end while for post_table Query
					
					//���֮����null�ָ�
					if(i != 4)
					{
						allPostList.add(null);
					}
					
				}//end for loop
			
			} catch(SQLException ex){
				System.out.println("Exception when handling getAllPostList......");
				ex.printStackTrace();
			}
			
			close(connStmt);
		}
		return allPostList;
	}

	@Override
	/** �õ�ĳһ���������б� */
	public ArrayList<Post> getPostList(int tag) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		ArrayList<Post> postList = new ArrayList<Post>();
		   
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = null;
			String postTableName = postTableMap.get(tag);
			String commentTableName = commentTableMap.get(tag);
			
			try {
				//��ʼ���Ӳ�ѯ����Ʒ
				ResultSet subRs = null;
				Statement subStatement = connStmt.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet subRs2 = null;
				Statement subStatement2 = connStmt.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				
				//��post_table���õ�������Ϣ
				sqlString = "select * from " + postTableName;
				rs = connStmt.statement.executeQuery(sqlString);
			
				while(rs.next())
				{		
					int pId = rs.getInt("p_id");
					String senderUsername = rs.getString("sender_username");;
					String title = rs.getString("title");
					String category = rs.getString("category");
					
					java.util.Date date = rs.getTimestamp("time");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					
					String content = rs.getString("content");
					int readSum = rs.getInt("read_sum");
					boolean isTop = rs.getBoolean("is_top");
					
					//��ʼ��һ��Post����
					Post post = new Post();
					post.setSenderUsername(senderUsername);
					post.setTitle(title);
					post.setCategory(category);
					post.setTime(calendar);
					post.setContent(content);
					post.setReadSum(readSum);
					post.setIsTop(isTop);
					
					
					//�Ӳ�ѯ����user_table����name
					sqlString = "select name from user_table where username='" + senderUsername + "'";
					String senderName = null;
					
					subRs = subStatement.executeQuery(sqlString);					
					if(subRs.next())
					{
						senderName = subRs.getString("name");
					}
					post.setSenderName(senderName);
					
					
					//�Ӳ�ѯ����comment_table
					ArrayList<Comment> commentList = new ArrayList<Comment>();				
					sqlString ="select * from " + commentTableName + " where p_id=" + pId;
					
					subRs = subStatement.executeQuery(sqlString);					
					while(subRs.next())
					{
						String commenterUsername = subRs.getString("commenter_username");
						
						java.util.Date subdate = subRs.getTimestamp("time");
						Calendar subcalendar = Calendar.getInstance();
						subcalendar.setTime(subdate);
						
						String commentContent = subRs.getString("content");
						
						//��ʼ��һ��Comment����
						Comment comment = new Comment();
						comment.setSenderUsername(commenterUsername);
						comment.setTime(subcalendar);
						comment.setContent(commentContent);
						
					
						//�Ӳ�ѯ2����user_table����name
						sqlString = "select name from user_table where username='" + commenterUsername + "'";
						String commenterName = null;
						
						subRs2 = subStatement2.executeQuery(sqlString);
						if(subRs2.next())
						{
							commenterName = subRs2.getString("name");
						}
						comment.setSenderName(commenterName);
						
						//comment��ʼ��ȫ�����
						commentList.add(comment);
						
					}//end while for comment_table Query
					
					post.setCommentList(commentList);
					post.setCommentsSum(commentList.size());
					
					//post��ʼ��ȫ�����
					postList.add(post);
				
				}//end while for post_table Query
			
			} catch(SQLException ex){
				System.out.println("Exception when handling getAllPostList......");
				ex.printStackTrace();
			}
			
			close(connStmt);
		}
		return postList;
	}
	
	@Override
	/** �����ӣ�����Ӧ��Ŀ�е�read_sum��1 */
	public boolean readPost(int tag, String senderUsername, Calendar time) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		boolean result = false;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{		
			String sqlString = null;
			String tableName = postTableMap.get(tag);
			
			java.util.Date date= time.getTime();
			java.sql.Timestamp ts =new java.sql.Timestamp(date.getTime());
						
			try {
				//��post_table����p_id��read_sum
				int pId = 1;
				int readSum = 0;
				
				sqlString = "select p_id, read_sum from " + tableName + " where sender_username='" + senderUsername+"' and time='" + ts + "'";
				
				rs = connStmt.statement.executeQuery(sqlString);
				if(rs.next())
				{
					pId = rs.getInt("p_id");
					readSum = rs.getInt("read_sum");
				}
				
				//��Ӧ��Ŀread_sum��1
				sqlString = "update " + tableName + " set read_sum=" + (readSum+1) + " where p_id=" + pId;
				connStmt.statement.executeUpdate(sqlString);
			
				result = true;
			
			} catch(SQLException e){
				result = false;
				System.out.println("Exception when handling readPost......");
				//e.printStackTrace();
			}
		
			close(connStmt);
		}
		return result;
	}

	@Override
	/** �ظ����ӣ���comment_table�в�������Ŀ */
	public Calendar replyPost(String username, int tag, String senderUsername, Calendar time, Comment comment) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		Calendar result = null;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = null;
			String postTableName = postTableMap.get(tag);
			String commentTableName = commentTableMap.get(tag);
						
			java.util.Date date = time.getTime();
			java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());
			
			try {
				//��post_table����p_id
				int pId = 0;				
				sqlString="select p_id from " + postTableName + " where sender_username='" + senderUsername + "' and time='" + ts + "'";
				
				rs = connStmt.statement.executeQuery(sqlString);			
				if(rs.next())
				{
					pId = rs.getInt("p_id");
				}
		
				//����comment_table����Ŀ
				Calendar calendar = Calendar.getInstance();
				java.util.Date currentDate = calendar.getTime();
				java.sql.Timestamp currentTime = new java.sql.Timestamp(currentDate.getTime());
			
				sqlString = "insert into " + commentTableName + " (p_id, commenter_username, time, content) values(" + pId + ", '" +
						username + "', '" + currentTime + "', '" + comment.getContent() + "');";
				connStmt.statement.executeUpdate(sqlString);	
				
				result = calendar;
				
			} catch(Exception ex){
				result = null;
				System.out.println("Exception when handling replyPost......");
				ex.printStackTrace();
			}

			close(connStmt);
		}
		return result;
	}

	@Override
	/** ��post_table�в�������Ŀ */
	public Calendar sendPost(String username, int authority, Post post) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		Calendar result = null;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = null;
			String tableName = authorityPostTableMap.get(authority);
			
			try {
				Calendar calendar = Calendar.getInstance();
				java.util.Date currentDate = calendar.getTime();
				java.sql.Timestamp currentTime = new java.sql.Timestamp(currentDate.getTime());
				
				sqlString = "insert into " + tableName + " (sender_username, title, category, time, content) values('" + username + "', '" +
						post.getTitle() + "', '" + post.getCategory() + "', '" + currentTime + "', '" + post.getContent() + "');";
				connStmt.statement.executeUpdate(sqlString);
				
				result = calendar;
				
			} catch(SQLException e){
				result = null;
				System.out.println("Exception when handling sendPost.....");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}
		return result;
	}

	@Override
	/** �޸�post_table�еĶ�Ӧ��Ŀ��ֻ���޸�title, category, content */
	public boolean revisePost(String username, int authority, Post post) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		boolean result = false;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = null;
			String tableName = authorityPostTableMap.get(authority);
			
			try{
				//����post_table����p_id
				int pId = 0;
				java.util.Date date = post.getTime().getTime();
				java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());
			
				sqlString = "select p_id from " + tableName + " where sender_username='" + username + "' and time='" + ts + "'";
				
				rs = connStmt.statement.executeQuery(sqlString);			
				if(rs.next())
				{
					pId = rs.getInt("p_id");
				}
			
				//���¶�Ӧ��Ŀ
				sqlString = "update " + tableName + " set title='" + post.getTitle() + "', category='" + post.getCategory() + "', content='" + post.getContent() +"' where p_id=" + pId;   
				connStmt.statement.executeUpdate(sqlString);

				result = true;
		
			} catch(SQLException e){
				result = false;
				System.out.println("Exception when handling revisePost......");
				//e.printStackTrace();
			}
		
			close(connStmt);
		}
		return result;
	}


/*	public static void main(String[] args)
	{
		PostOperation postOperation = new PostOperation();
		ArrayList<Post> postList = postOperation.getAllPostList();
		
		for(int i=0; i<postList.size(); i++)
		{
			Post post = postList.get(i);
			if(post != null)
			{
				System.out.println(post.getSenderName() + ": " + post.getTitle());
				
				for(int j=0; j<post.getCommentList().size(); j++)
				{
					Comment comment = post.getCommentList().get(j);
					System.out.println("�ظ�----" + comment.getSenderName() + ": " + comment.getContent());
				}
				System.out.println();
			}
		}
		
	}*/
	
}
