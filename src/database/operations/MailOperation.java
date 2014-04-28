package database.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import database.connection.ConnectionStatement;
import database.connection.PublicConnection;

import beans.mail.Address;
import beans.mail.AddressGroup;
import beans.mail.FeedbackMail;
import beans.mail.Mail;
import server.interfaces.MailOperationInterface;

public class MailOperation implements MailOperationInterface {
	
	private static String userTable = "user_table";
	private static String privateAddressTable = "private_address_table";	
	private static String privateAddressGroupTable = "private_address_group_table";
	private static String personalHobbyTable = "personal_hobby_table";
	private static String personalAdeptnessTable = "personal_adeptness_table";
		
	
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
	
	
	//=========================================================================
	@Override
	/**
	 * ******ȡ�ʼ�******  
	 * �������û���==sendername���ż�ȡ������Ҳ�����ռ���
	 */
	public ArrayList<Mail> getReceivedMailList(String username) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;

		String sqlString = null;
		ArrayList<Mail> mailList = new ArrayList<Mail>();
		
		open(connStmt);

		if(connStmt.connection != null)
		{		
			try {		
				Statement subStatement = connStmt.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				Statement subStatement2 = connStmt.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet subRS = null;
				ResultSet subRS2 = null;
				
				//��receiver_mail_table����m_id
				sqlString="select m_id, is_read from receiver_mail_table where receiver_username='"+username+"' and is_delete=0";
				rs = connStmt.statement.executeQuery(sqlString);
	
				while(rs.next())
				{
					int m_id = rs.getInt("m_id");
					boolean isRead = rs.getBoolean("is_read");				
					
					Mail mail = new Mail();
					mail.setIsRead(isRead);
					
					//��mail_table
					sqlString="select sender_username, title, time, content, is_feedback from mail_table where m_id="+m_id+"";
					subRS=subStatement.executeQuery(sqlString);
					
					if(subRS.next())
					{		
						String senderUsername=subRS.getString("sender_username");
						String title=subRS.getString("title");
						
						java.util.Date date=subRS.getTimestamp("time");
						Calendar time = Calendar.getInstance();
						time.setTime(date);
						
						String content=subRS.getString("content");
						boolean isNeedFeedback=subRS.getBoolean("is_feedback");
											
						String name=null;
						
						sqlString="select name from user_table where username='"+senderUsername+"'";
						subRS2=subStatement2.executeQuery(sqlString);
						
						if(subRS2.next())
							name=subRS2.getString("name");
						
						mail.setSenderUsername(senderUsername);
						mail.setTitle(title);
						mail.setTime(time);
						mail.setContent(content);
						mail.setIsNeedFeedback(isNeedFeedback);
						mail.setSenderName(name);
						
						//mail��ʼ��ȫ�����
						mailList.add(mail);
					}
				}
				
			} catch(SQLException e){
				System.out.println("Exception when handling getReceivedMailList......");
				//e.printStackTrace();
			}
		
			close(connStmt);
		}
		return mailList;
	}


	@Override
	/**
	 * ******���ʼ�/�ظ��ʼ�******   
	 * �ռ��˿�����һ���򼸸� 
	 */
	public Calendar sendMail(String username, ArrayList<String> receivers, Mail mail) {
		// TODO Auto-generated method stub
		
		int flag1=0;
		int flag2=1;
		
		Calendar result = null;

		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		String sqlString = null;
		
		open(connStmt);

		if(connStmt.connection != null)
		{
			try {
				String title=mail.getTitle();
				
				Date date = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = df.format(date);
				
				String content=mail.getContent();
				boolean isNeedFeedback=mail.getIsNeedFeedback();
				
				//���뵽mail_table
				sqlString="insert into mail_table (sender_username, title, time, content, is_feedback, receiver_sum) " +
						"values ('"+username+"', '"+title+"', '"+time+"', '"+content+"', "+isNeedFeedback+", "+receivers.size()+")";
				flag1=connStmt.statement.executeUpdate(sqlString);
				
				//��mail_table��ȡm_id
				sqlString="select m_id from mail_table where sender_username='"+username+"' and time='"+time+"'";
				rs=connStmt.statement.executeQuery(sqlString);
				
				int m_id=-1;
				
				if(rs.next())
					m_id=rs.getInt("m_id");
				
				for(int i=0; i<receivers.size(); i++)
				{
					//���뵽receiver_mail_table
					sqlString="insert into receiver_mail_table (receiver_username, m_id) values ('"+receivers.get(i)+"', "+m_id+")";
					int temp=connStmt.statement.executeUpdate(sqlString);
					
					if(temp!=1)
					{
						flag2=0;
					}
				}
				
				if(flag1==1 && flag2==1)
				{
					result = Calendar.getInstance();
					result.setTime(date);
				}
				
			} catch(SQLException e){
				result = null;
				System.out.println("Exception when handling sendMail1......");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}
		return result;
	}

	
	@Override
	/** ģ���������� 
	 *  ֻ�д��������������ռ���ʱ�ŷ��� true */
	public Calendar sendMail(String username, ArrayList<Integer> hobbyNumList,
			ArrayList<Integer> adeptnessNumList, Mail mail) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		Calendar result = null;
				
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = null;
			ArrayList<String> receiverList1 = new ArrayList<String>();  //for hobby
			ArrayList<String> receiverList2 = new ArrayList<String>();	//for adeptness	
			boolean tag1 = false;  //for list1
			boolean tag2 = false;  //for list2
			
			try {
				//��personal_hobby_table
				if(hobbyNumList.size() > 0)
				{
					tag1 = true;
					for(int i=0; i<hobbyNumList.size(); i++)
					{
						sqlString = "select username from " + personalHobbyTable + " where hobby=" + hobbyNumList.get(i);
						rs = connStmt.statement.executeQuery(sqlString);
						
						ArrayList<String> tempList = new ArrayList<String>();
						while(rs.next())
						{	
							tempList.add(rs.getString("username"));
						}
						
						if(i == 0)
						{
							receiverList1 = tempList;
						}
						else
						{
							receiverList1 = joinList(receiverList1, tempList);
						}					
					}
				}
				
				//��personal_adeptness_table
				if(adeptnessNumList.size() > 0)
				{
					tag2 = true;
					for(int i=0; i<adeptnessNumList.size(); i++)
					{
						sqlString = "select username from " + personalAdeptnessTable + " where adeptness=" + adeptnessNumList.get(i);
						rs = connStmt.statement.executeQuery(sqlString);
						
						ArrayList<String> tempList = new ArrayList<String>();
						while(rs.next())
						{	
							tempList.add(rs.getString("username"));
						}
						
						if(i == 0)
						{
							receiverList2 = tempList;
						}
						else
						{
							receiverList2 = joinList(receiverList2, tempList);
						}					
					}
				}
				
				//receiversȷ��
				ArrayList<String> receivers;
				
				if(tag1 == true && tag2 == false)
					receivers = receiverList1;
				else if (tag2 == true && tag1 == false)
					receivers = receiverList2;
				else if(tag1 == true && tag2 == true)
					receivers = joinList(receiverList1, receiverList2);
				else
					receivers = new ArrayList<String>();
				
				//send mail
				if(receivers.size() > 0)
				{				
					String title = mail.getTitle();
					
					Calendar calendar = Calendar.getInstance();
					java.util.Date date = calendar.getTime();
					java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
					
					String content = mail.getContent();
					boolean isNeedFeedback = mail.getIsNeedFeedback();
					
					//���뵽mail_table
					sqlString="insert into mail_table (sender_username, title, time, content, is_feedback, receiver_sum) " +
							"values ('"+username+"', '"+title+"', '"+time+"', '"+content+"', "+isNeedFeedback+", "+receivers.size()+")";
					connStmt.statement.executeUpdate(sqlString);
					
					//��mail_table��ȡm_id
					sqlString="select m_id from mail_table where sender_username='"+username+"' and time='"+time+"'";
					rs=connStmt.statement.executeQuery(sqlString);
					
					int mId = -1;
					
					if(rs.next())
						mId = rs.getInt("m_id");
					
					if(mId > 0)
					{
						for(int i=0; i<receivers.size(); i++)
						{
							//���뵽receiver_mail_table
							sqlString="insert into receiver_mail_table (receiver_username, m_id) values ('"+receivers.get(i)+"', "+mId+")";
							connStmt.statement.executeUpdate(sqlString);						
						}
						
						//ȫ�������ɹ�
						result = calendar;
					}
					
				}
	
			} catch (SQLException e) {
				// TODO: handle exception
				result = null;
				System.out.println("Exception when handling sendMail2......");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}
		return result;
	}
	
	
	private ArrayList<String> joinList(ArrayList<String> list1, ArrayList<String> list2)
	{
		ArrayList<String> resultList = new ArrayList<String>();
		
		int len1 = list1.size();
		int len2 = list2.size();
		
		if(len1 > len2)
		{
			for(int i=0; i<list1.size(); i++)
			{
				String string = list1.get(i);
				
				if(list2.contains(string) == true)
					resultList.add(string);
			}
		}
		else 
		{
			for(int i=0; i<list2.size(); i++)
			{
				String string = list2.get(i);
				
				if(list1.contains(string) == true)
					resultList.add(string);
			}
		}
		
		return resultList;
	}

	
/*	@Override
	public boolean replyMail(String username, String receiver, Mail mail) {
		// TODO Auto-generated method stub
		return false;
	}*/

	
	@Override
	/**
	 * ******�Ķ��ż�******     
	 * ע�⽫mail�е�timeȡ�����룬��Ϊ����calendar 
	 *  senderUsernameΪ���ż��ķ����ˣ�receiverUsernameΪ���û�
	 */
	public boolean readMail(String receiverUsername, String senderUsername, Calendar calendar) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;	
		boolean result = false;
		String sqlString = null;
		
		open(connStmt);

		if(connStmt.connection != null)
		{
			try {
				
				java.util.Date date=calendar.getTime();
				java.sql.Timestamp time=new java.sql.Timestamp(date.getTime());
				
				//��mail_table����m_id
				sqlString="select m_id, is_feedback, feedback_sum from mail_table where sender_username='"+senderUsername+"' and time='"+time+"'";
				rs=connStmt.statement.executeQuery(sqlString);
				
				int mId=-1;
				boolean isFeedback = false;
				int feedbackSum = 0;
				
				if(rs.next())
				{
					mId=rs.getInt("m_id");
					isFeedback = rs.getBoolean("is_feedback");
					feedbackSum = rs.getInt("feedback_sum");
				}
				
				//��receiver_mail_table�����is_read
				sqlString="update receiver_mail_table set is_read=1 where m_id="+mId+" and receiver_username='"+receiverUsername+"'";
				connStmt.statement.executeUpdate(sqlString);  //��is_read��Ϊ1����Ϊ�Ѷ�						
				
				//�������������ż�������feedback_sum
				if(isFeedback == true)
				{
					sqlString = "update mail_table set feedback_sum=" + (feedbackSum+1) + " where m_id=" + mId;
					connStmt.statement.executeUpdate(sqlString);
				}
				
				result = true;
				
			} catch(SQLException e){
				result = false;
				System.out.println("Exception when handling readMail......");
				//e.printStackTrace();
			}
		
			close(connStmt);		
		}
		return result;
	}

	
	@Override
	/**
	 * ******���ռ�����ɾ���ż�******   
	 * senderUsernameΪ���ż��ķ����ˣ�receiverUsernameΪ���û�
	 */
	public boolean deleteReceivedMail(String receiverUsername, String senderUsername, Calendar calendar) {
		// TODO Auto-generated method stub
		
		int flag=0;
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		String sqlString = null;
		
		open(connStmt);

		if(connStmt.connection != null)
		{
			try {
				java.util.Date date=calendar.getTime();
				java.sql.Timestamp time=new java.sql.Timestamp(date.getTime());
				
				//��mail_table����m_id
				sqlString="select m_id from mail_table where sender_username='"+senderUsername+"' and time='"+time+"'";
				rs=connStmt.statement.executeQuery(sqlString);
				
				int mId=-1;
				
				if(rs.next())
				{
					mId=rs.getInt("m_id");					
				}
				
				//��receiver_mail_table�����is_delete
				sqlString="update receiver_mail_table set is_delete=1 where m_id="+mId+" and receiver_username='"+receiverUsername+"'";
				flag=connStmt.statement.executeUpdate(sqlString);
								
			} catch(SQLException e){
				System.out.println("Exception when handling deleteReceivedMail......");
				//e.printStackTrace();
			}
		
		    close(connStmt);		
		}
		return flag==1;
	}
	

	@Override
	/**
	 * ******��ʾ�ѷ��ż�******     
	 * ע�� û��ȡ�������˵�name������nameû�г�ʼ��
	 */
	public ArrayList<FeedbackMail> getSentMailList(String username) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		String sqlString = null;
		
		ArrayList<FeedbackMail> mailList = new ArrayList<FeedbackMail>();
			
		open(connStmt);
		
		if(connStmt.connection != null)
		{
	        try {
	        	sqlString="select title, time, content, is_feedback, feedback_sum, receiver_sum from mail_table where sender_username='"+username+"' and is_delete=0";
	        	rs=connStmt.statement.executeQuery(sqlString);
	        	
	        	while(rs.next())
	        	{
	        		FeedbackMail mail=new FeedbackMail();
	        		
	        		mail.setSenderUsername(username);
	        		mail.setTitle(rs.getString("title"));
	        		
	        		java.util.Date date=rs.getTimestamp("time");
					Calendar time = Calendar.getInstance();
					time.setTime(date);
	        		mail.setTime(time);
	        		
	        		mail.setContent(rs.getString("content"));
	        		mail.setIsNeedFeedback(rs.getBoolean("is_feedback"));
	        		mail.setFeedbackSum(rs.getInt("feedback_sum"));
	        		mail.setReceiverSum(rs.getInt("receiver_sum"));
	        	
	        		mailList.add(mail);    		
	        	}
	        	
	        } catch(SQLException e){
	        	System.out.println("Exception when handling getSentMailList......");
				//e.printStackTrace();
			}
		
	        close(connStmt);
		}	
		return mailList;
	}

	
	@Override
	/** ��ȡ�ѷ��ż�����ϸ������Ϣ */
	public FeedbackMail getFeedbackMail(FeedbackMail mail) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		String sqlString = null;
		FeedbackMail resultFeedbackMail = new FeedbackMail();
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
	        try {
				Statement subStatement = connStmt.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet subRS = null;
				
	        	String senderUsername = mail.getSenderUsername();
	        	Calendar calendar = mail.getTime();
	        	
	        	java.util.Date date=calendar.getTime();
				java.sql.Timestamp time=new java.sql.Timestamp(date.getTime());
	        	
				ArrayList<String> feedbackList=new ArrayList<String>();
	    		ArrayList<String> receiverList=new ArrayList<String>();
	    		
				sqlString="select m_id from mail_table where sender_username='"+senderUsername+"' and time='"+time+"'";
				rs=connStmt.statement.executeQuery(sqlString);
								
				if(rs.next())
				{
					int m_id=rs.getInt("m_id");
					
					sqlString="select receiver_username, is_read from receiver_mail_table where m_id="+m_id+"";
					subRS=subStatement.executeQuery(sqlString);
					
					while(subRS.next())
					{
		    			receiverList.add(subRS.getString("receiver_username"));
		    			
		    			if(subRS.getInt("is_read")==1)
		    				feedbackList.add(subRS.getString("receiver_username"));  //���is_read��Ϊ1�����Ѷ������ռ���������������б�
		    		}	
				}
	    		
				resultFeedbackMail.setSenderUsername(mail.getSenderUsername());
				resultFeedbackMail.setSenderName(mail.getSenderName());
				resultFeedbackMail.setTitle(mail.getTitle());
				resultFeedbackMail.setTime(mail.getTime());
				resultFeedbackMail.setContent(mail.getContent());
				resultFeedbackMail.setIsNeedFeedback(mail.getIsNeedFeedback());
				resultFeedbackMail.setIsRead(mail.getIsRead());
				
	    		resultFeedbackMail.setFeedbackList(feedbackList);
	    		resultFeedbackMail.setReceiverList(receiverList);
	    		
	        } catch(SQLException e){
	        	System.out.println("Exception when handling getFeedbackMail......");
				//e.printStackTrace();
			}
			
	        close(connStmt);
		}	
		return resultFeedbackMail;
	}
	

	@Override
	/**
	 * ******�ӷ�������ɾ���ż�******
	 */
	public boolean deleteSentMail(String senderUsername, Calendar calendar){
		// TODO Auto-generated method stub
		
		int flag=0;
		ConnectionStatement connStmt = new ConnectionStatement();
		String sqlString = null;
		
		open(connStmt);

		if(connStmt.connection != null)
		{
			try{
				java.util.Date date=calendar.getTime();
				java.sql.Timestamp time=new java.sql.Timestamp(date.getTime());
				
				sqlString="update mail_table set is_delete=1 where sender_username='"+senderUsername+"' and time='"+time+"'";
				flag=connStmt.statement.executeUpdate(sqlString);
				
			}catch(SQLException e){
				System.out.println("Exception when handling deleteSentMail......");
				//e.printStackTrace();
			}
						
			close(connStmt);		
		}
		return flag==1;
	}

	
	//=================================================================================
	@Override
	/** ��ȡ˽�е�ַ���б� */
	public ArrayList<AddressGroup> getPrivateAddressGroupList(String username) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		ArrayList<AddressGroup> addressGroupList = new ArrayList<AddressGroup>();
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = null;
			
			try {
				//��ʼ���Ӳ�ѯ����Ʒ
				ResultSet subRs = null;
				Statement subStatement = connStmt.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet subRs2 = null;
				Statement subStatement2 = connStmt.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				
				//��private_address_group_table����group_number, group_name
				sqlString = "select group_number, group_name from " + privateAddressGroupTable + " where username='" + username + "'";
				rs = connStmt.statement.executeQuery(sqlString);
				
				while(rs.next())
				{
					int groupNumber = rs.getInt("group_number");
					String groupName = rs.getString("group_name");
					
					//��ʼ��һ��AddressGroup����
					AddressGroup addressGroup = new AddressGroup();
					addressGroup.setGroupName(groupName);
					addressGroup.setAddressList(new ArrayList<Address>());
					
					
					//��private_address_table���ҷ�����
					sqlString = "select contact_username from " + privateAddressTable + " where username='" + username + "' and group_number=" + groupNumber;
					subRs = subStatement.executeQuery(sqlString);
					
					while(subRs.next())
					{
						String contactUsername = subRs.getString("contact_username");
						
						//��user_table��ȡname, grade
						sqlString = "select name, grade from " + userTable + " where username='" + contactUsername + "'";
						subRs2 = subStatement2.executeQuery(sqlString);
						
						if(subRs2.next())
						{
							String name = subRs2.getString("name");
							int grade = subRs2.getInt("grade");
							
							//��ʼ��һ��Address����
							Address address = new Address();
							address.setUsername(contactUsername);
							address.setName(name);
							address.setGrade(grade);
							
							if(grade == 0)
							{
								address.setIsTeacher(true);
							}
							else
							{
								address.setIsTeacher(false);
							}
							
							//��address�ӵ�addressGroup��ȥ
							addressGroup.addAddress(address);
						}					
					}//end while for contact_username Query
					
					//��addressGroup�ӵ�addressGroupList��ȥ
					addressGroupList.add(addressGroup);
					
				}// end while for group_number Query
				
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("Exception when handling getPrivateAddressGroupList.....");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}
		return addressGroupList;
	}
	
	@Override
	/** ��ȡȫ�ֵ�ַ���б� */
	public ArrayList<AddressGroup> getGlobalAddressGroupList() {
		// TODO Auto-generated method stub
		
		ArrayList<AddressGroup> globalAddressGroupList = new ArrayList<AddressGroup>();  //���ȫ�ֵ�ַ��
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
				
		open(connStmt);		
			
		if(connStmt.connection != null)
		{
			String sqlString;
				
			//��ʼ����ʱ��AddressGroup����
			AddressGroup[] addressGroupArray = new AddressGroup[5];
			for(int i=0; i<5; i++)
			{
				addressGroupArray[i] = new AddressGroup();
				addressGroupArray[i].setAddressList(new ArrayList<Address>());
					
				if(i == 0)
				{
					addressGroupArray[i].setGroupName("��ʦ��");
				}
				else
				{
					addressGroupArray[i].setGroupName("��" + i + "��");
				}
			}
			
			try {
				sqlString = "select username, name, grade from " + userTable;
				rs = connStmt.statement.executeQuery(sqlString);
		
				while(rs.next())
				{
					//��ʼ��һ��Address����
					Address address = new Address();
					address.setUsername(rs.getString("username"));
					address.setName(rs.getString("name"));
					int grade = rs.getInt("grade");
					address.setGrade(grade);
						
					if(grade == 0)
					{
						address.setIsTeacher(true);
					}
					else 
					{
						address.setIsTeacher(false);
					}
						
					//���뵽��Ӧ��AddressGroup������ȥ
					addressGroupArray[grade].addAddress(address);
				}
				
				//����ʱAddressGroup����ӵ�globalAddressGroupList��ȥ
				for(int i=0; i<5; i++)
				{
					globalAddressGroupList.add(addressGroupArray[i]);
				}
				
			} catch (SQLException e) {
				System.out.println("Exception when getGlobalAddressGroup......");
				//e.printStackTrace();
			}
			
			close(connStmt);			
		}		
		return globalAddressGroupList;
	}

	@Override
	/** ɾ��ĳ���� */
	public boolean deleteGroup(String username, String groupName) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		boolean result = false;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = null;
			
			try {
				//��private_address_group_table����group_number
				sqlString = "select group_number from " + privateAddressGroupTable + " where username='" + username + "' and group_name='" + groupName + "'";
				rs = connStmt.statement.executeQuery(sqlString);
				
				if(rs.next())
				{
					int groupNumber = rs.getInt("group_number");
					
					//ɾ��private_address_table�еĶ�Ӧ��
					sqlString = "delete from " + privateAddressTable + " where username='" + username + "' and group_number=" + groupNumber;
					connStmt.statement.executeUpdate(sqlString);
					
					//ɾ��private_address_group_table�еĶ�Ӧ��
					sqlString = "delete from " + privateAddressGroupTable + " where username='" + username + "' and group_number=" + groupNumber;
					connStmt.statement.executeUpdate(sqlString);
					
					//���в����ɹ�
					result = true;
				}
				
			} catch (SQLException e) {
				// TODO: handle exception
				result = false;
				System.out.println("Exception when handling deleteGroup.....");
				//e.printStackTrace();
			}
	
			close(connStmt);
		}
		return result;
	}

	@Override
	/** ��ĳ�������һЩ�ˣ������鲻���ڣ����¿�һ�� */
	public boolean addAddress(String username, String groupName, ArrayList<String> usernameList) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		boolean result = false;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = null;
			int groupNumber = 0;
			
			try {
				//��private_address_group_table����group_number
				sqlString = "select group_number from " + privateAddressGroupTable + " where username='" + username + "' and group_name='" + groupName + "'";
				rs = connStmt.statement.executeQuery(sqlString);
				
				//����������
				if(rs.next())
				{
					groupNumber = rs.getInt("group_number");		
				}
				//������鲻����
				else
				{
					//�����group_number
					sqlString = "select max(group_number) from " + privateAddressGroupTable + " where username='" + username + "'";
					rs = connStmt.statement.executeQuery(sqlString);
					
					//����Ѿ�������
					if(rs.next())
					{
						groupNumber = rs.getInt(1);
						
						//�¿�һ�飬group_number��1
						groupNumber++;
					}
					//��������ǵ�һ�ο���
					else
					{
						groupNumber = 1;
					}
	
					//��private_address_group_table����Ӷ�Ӧ��
					sqlString = "insert into " + privateAddressGroupTable + " (username, group_number, group_name) values('" + username + 
							"', " + groupNumber + ", '" + groupName + "')";
					connStmt.statement.executeUpdate(sqlString);
				}
				
				
				//��private_address_table����Ӷ�Ӧ��
				for(int i=0; i<usernameList.size(); i++)
				{
					try {
						sqlString = "insert into " + privateAddressTable + " (username, contact_username, group_number) values('" + username + 
							"', '" + usernameList.get(i) + "', " + groupNumber + ")";
						connStmt.statement.executeUpdate(sqlString);
						
					} catch (SQLException e) {
						// TODO: handle exception
						
					}					
				}
				
				//���в����ɹ�
				result = true;
				
			} catch (SQLException e) {
				// TODO: handle exception
				result = false;
				System.out.println("Exception when handling addAddress.....");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}
		return result;
	}

	@Override
	/** ɾ��ĳ���еĲ����� */
	public boolean deleteAddress(String username, String groupName, ArrayList<String> usernameList) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		boolean result = false;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = null;
			
			try {
				//��private_address_group_table����group_number
				sqlString = "select group_number from " + privateAddressGroupTable + " where username='" + username + "' and group_name='" + groupName + "'";
				rs = connStmt.statement.executeQuery(sqlString);
				
				if(rs.next())
				{
					int groupNumber = rs.getInt("group_number");
							
					//ɾ��private_address_table�еĶ�Ӧ��
					for(int i=0; i<usernameList.size(); i++)
					{
						sqlString = "delete from " + privateAddressTable + " where username='" + username + "' and group_number=" + groupNumber + "" +
								" and contact_username='" + usernameList.get(i) + "'";
						connStmt.statement.executeUpdate(sqlString);
					}
					
					result = true;
				}
				
			} catch (SQLException e) {
				// TODO: handle exception
				result = false;
				System.out.println("Exception when handling deleteAddress.....");
				//e.printStackTrace();
			}
	
			close(connStmt);
		}
		return result;
	}

	
/*	public static void main(String[] args)
	{
		MailOperation mailOperation = new MailOperation();
		
		Mail mail = new Mail();
		mail.setTitle("����");
		mail.setContent("aaaaaaa");
		mail.setIsNeedFeedback(false);
		
		ArrayList<Integer> hobbyNumList = new ArrayList<Integer>();
		hobbyNumList.add(1);
		hobbyNumList.add(2);
		ArrayList<Integer> adeptnessNumList = new ArrayList<Integer>();
		adeptnessNumList.add(4);
		adeptnessNumList.add(5);

		mailOperation.sendMail("001", hobbyNumList, adeptnessNumList, mail);
		
	}*/
}
