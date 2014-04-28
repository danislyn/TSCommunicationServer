package database.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.connection.ConnectionStatement;
import database.connection.PublicConnection;

import beans.information.Information;
import beans.information.OnlinePerson;
import beans.information.TeacherInfo;
import beans.teacher.Schedule;
import server.interfaces.InformationOperationInterface;

public class InformationOperation implements InformationOperationInterface {
	
	private static String userTable="user_table";
	private static String personalHobbyTable="personal_hobby_table";
	private static String personalAdeptnessTable="personal_adeptness_table";
	private static String personalFavouriteTeacherTable="personal_favourite_teacher_table";
	private static String onlineUserTable="online_user_table";
    private static String hobbyDefinitionTable="hobby_definition_table";
    private static String adeptnessDefinitionTable="adeptness_definition_table";
    private static String scheduleTable="schedule_table";
    
    //内置列表，省得每次都去查
    private static ArrayList<String> hobbyDefinitionList = new ArrayList<String>();
    private static ArrayList<String> adeptnessDefinitionList = new ArrayList<String>();
	
    //静态初始化hobbyDefinitionList and adeptnessDefinitionList
    static
    {
    	ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
				
		while(connStmt.connection == null)
		{
			open(connStmt);		
			
			if(connStmt.connection != null)
			{
				String sqlString;
			
				try {
					sqlString = "select hobby_definition from " + hobbyDefinitionTable;
					rs = connStmt.statement.executeQuery(sqlString);
		
					while(rs.next())
					{
						String hobby = rs.getString("hobby_definition");
						hobbyDefinitionList.add(hobby);
					}
				
					sqlString = "select adeptness_definition from " + adeptnessDefinitionTable;
					rs = connStmt.statement.executeQuery(sqlString);
					
					while(rs.next())
					{
						String adeptness = rs.getString("adeptness_definition");
						adeptnessDefinitionList.add(adeptness);
					}
				
				} catch (SQLException e) {
					System.out.println("Exception when static initialization......");
					//e.printStackTrace();
				}
			
				close(connStmt);
				break;
			}
		}		
    }
	
	//与数据库取得连接，初始化statement
	private static void open(ConnectionStatement connectionStatement){
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
	private static void close(ConnectionStatement connectionStatement){
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
	/** 根据username得到个人信息 */
	public Information getPersonalInformation(String username) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		Information info=new Information();
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{			
			String sqlString;
			
			try {			
				//查user_table，得到name,grade,klass,signature
				sqlString="select name,grade,klass,signature from "+userTable+" where username='"+username+"'";
				rs=connStmt.statement.executeQuery(sqlString);
			
				if(rs.next()){
					String name=rs.getString("name");
					int grade=rs.getInt("grade");
					int klass=rs.getInt("klass");
					String signature=rs.getString("signature");
					info.setUsername(username);
					info.setName(name);
					info.setGrade(grade);
					info.setKlass(klass);
					info.setSignature(signature);			
				}
			
				/** //查hobby_definition_table，得到hobbyDefinitionList
				sqlString="select * from "+hobbyDefinitionTable;
				rs=connStmt.statement.executeQuery(sqlString);
			
				ArrayList<String> hobbyDefinitionList=new ArrayList<String>();
				while(rs.next()){
					String hobby=rs.getString("hobby_definition");
					hobbyDefinitionList.add(hobby);
				}*/
				
				//查personal_hobby_table，得到hobbyList
				sqlString="select hobby from "+personalHobbyTable+" where username='"+username+"'";
				rs=connStmt.statement.executeQuery(sqlString);
				
				ArrayList<String> hobbyList = new ArrayList<String>();
				ArrayList<Integer> hobbyNumList = new ArrayList<Integer>();
				while(rs.next()){
					int hobbyNum=rs.getInt("hobby");
					String hobby=hobbyDefinitionList.get(hobbyNum-1);
					hobbyList.add(hobby);
					hobbyNumList.add(hobbyNum);
				}
				//hobbyList OK!
				info.setHobbyList(hobbyList);
				info.setHobbyNumList(hobbyNumList);
			
			
				/** //查adeptness_definition_table，得到adeptnessDefinitionList
				sqlString="select * from "+adeptnessDefinitionTable;
				rs=connStmt.statement.executeQuery(sqlString);
			
				ArrayList<String> adeptnessDefinitionList=new ArrayList<String>();		
				while(rs.next()){
					String adeptness=rs.getString("adeptness");
					adeptnessDefinitionList.add(adeptness);
				}*/
			
				//查personal_adeptness_table，得到adeptnessList
				sqlString="select adeptness from "+personalAdeptnessTable+" where username='"+username+"'";
				rs=connStmt.statement.executeQuery(sqlString);
			
				ArrayList<String> adeptnessList = new ArrayList<String>();		
				ArrayList<Integer> adeptnessNumList = new ArrayList<Integer>();
				while(rs.next()){
					int adeptnessNum=rs.getInt("adeptness");
					String adeptness=adeptnessDefinitionList.get(adeptnessNum-1);
					adeptnessList.add(adeptness);
					adeptnessNumList.add(adeptnessNum);
				}
				//adeptnessList OK!
				info.setAdeptnessList(adeptnessList);
				info.setAdeptnessNumList(adeptnessNumList);
				
			
				//查personal_favourite_teacher_table，得到favouriteTeacherList
				sqlString="select favourite_teacher from "+personalFavouriteTeacherTable+" where username='"+username+"'";
				rs=connStmt.statement.executeQuery(sqlString);
			
				ArrayList<String> favouriteTeacherList=new ArrayList<String>();
				while(rs.next()){
					String favouriteTeacher=rs.getString("favourite_teacher");
					favouriteTeacherList.add(favouriteTeacher);
				}
				//favouriteTeacherList OK!
				info.setFavouriteTeacherList(favouriteTeacherList);
			
			} catch(SQLException e){
				System.out.println("Exception when handling getPersonalInformation");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}	
		return info;
	}
		
	@Override
	/** 修改user_table, personal_hobby_table, personal_adeptness_table, personal_favourite_teacher_table中的对应条目 */
	public boolean revisePersonalInformation(String username, Information info) {
		// TODO Auto-generated method stub
		
		String name = info.getName();
		int klass = info.getKlass();
		int grade = info.getGrade();
		String signature = info.getSignature();
		ArrayList<Integer> hobbyNumList = info.getHobbyNumList();
		ArrayList<Integer> adeptnessNumList = info.getAdeptnessNumList();
		ArrayList<String> favouriteTeacherList = info.getFavouriteTeacherList();
		
		ConnectionStatement connStmt = new ConnectionStatement();
		boolean result = false;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString;
			
			try {
				//更新user_table
				sqlString="update "+userTable+" set name='"+name+"',grade="+grade+",klass="+klass+",signature='"+signature+"' where username='"+username+"'";
				connStmt.statement.executeUpdate(sqlString);
		    
				//删除personal_hobby_table中的对应条目
				sqlString="delete from "+personalHobbyTable+" where username='"+username+"'";
				connStmt.statement.executeUpdate(sqlString);
		    
				//插入personal_hobby_table新条目
				int hobbyNum;
				for(int i=0; i<hobbyNumList.size(); i++)
				{
					hobbyNum = hobbyNumList.get(i);
					sqlString="insert into "+personalHobbyTable+" (username, hobby) values('"+username+"',"+hobbyNum+")";
					connStmt.statement.executeUpdate(sqlString);
				}
			
				//删除personal_adeptness_table中的对应条目		    
				sqlString="delete from "+personalAdeptnessTable+" where username='"+username+"'";
				connStmt.statement.executeUpdate(sqlString);
		    
				//插入personal_adeptness_table新条目
				int adeptnessNum;
				for(int i=0; i<adeptnessNumList.size(); i++)
				{
					adeptnessNum = adeptnessNumList.get(i);
					sqlString="insert into "+personalAdeptnessTable+" (username, adeptness) values('"+username+"',"+adeptnessNum+")";
					connStmt.statement.executeUpdate(sqlString);
				}
				
				//删除personal_favourite_teacher_table中的对应条目
				sqlString="delete from "+personalFavouriteTeacherTable+" where username='"+username+"'";
				connStmt.statement.executeUpdate(sqlString);
		    
				//插入personal_favourite_teacher_table新条目
				String favouriteTeacher;
				for(int i=0; i<favouriteTeacherList.size(); i++)
				{
					favouriteTeacher = favouriteTeacherList.get(i);
					sqlString="insert into "+personalFavouriteTeacherTable+" (username, favourite_teacher) values('"+username+"','"+favouriteTeacher+"')";
					connStmt.statement.executeUpdate(sqlString);
				}
				
				//全部操作完成
				result = true;
				
			} catch (SQLException e) {
				result = false;
				System.out.println("Exception when handling revisePersonalInformation");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}		
		return result;
	}
	
	@Override
	/** 修改user_table中的对应条目 */
	public boolean revisePassword(String username, String password) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		boolean result = false;
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{			
			String sqlString = "update " + userTable + " set password=" + "'" + password + "'" + " where username=" + "'" + username +"'";
			
			try {
				connStmt.statement.executeUpdate(sqlString);
				result = true;
				
			} catch (SQLException e) {
				result = false;
				System.out.println("Exception when handling revisePassword......");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}
		return result;
	}

	@Override
	/** 从online_user_table中得到所有在线用户列表 */
	public ArrayList<OnlinePerson> getOnlinePersonList() {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		ArrayList<OnlinePerson> onlinePersonList = new ArrayList<OnlinePerson>();
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString;
			
			try {
				//初始化子查询所需品 
				ResultSet subRS = null;
				Statement subStatement = connStmt.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				
				sqlString = "select * from "+onlineUserTable;
				rs = connStmt.statement.executeQuery(sqlString);
							
				while(rs.next()){
					OnlinePerson onlinePerson = new OnlinePerson();
					String tempUsername = rs.getString("username");
					onlinePerson.setUsername(tempUsername);
					onlinePerson.setLocation(rs.getInt("location"));
					
					//可省略========
					//ResultSet subRS = null;
					//Statement subStatement = connStmt.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					//=============
					sqlString = "select name, authority from " + userTable + " where username='" + tempUsername + "'";
					subRS = subStatement.executeQuery(sqlString);
					
					if(subRS.next())
					{
						onlinePerson.setName(subRS.getString("name"));
						onlinePerson.setAuthority(subRS.getInt("authority"));
					}
					//=============
					
					onlinePersonList.add(onlinePerson);
				}		
				
			} catch(SQLException e){
				System.out.println("Exception when handling getOnlinePersonList......");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}
		return onlinePersonList;
	}

	@Override
	/** 获取老师特有信息列表  */
	public ArrayList<TeacherInfo> getAllTeacherInfoList() {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		ArrayList<TeacherInfo> teacherInfoList = new ArrayList<TeacherInfo>();
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = null;
			
			try {
				ResultSet subRS = null;
				Statement subStatement = connStmt.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				
				//查user_table
				sqlString = "select username, name, signature from " + userTable + " where grade=0";
				rs = connStmt.statement.executeQuery(sqlString);
				
				while(rs.next())
				{
					Information info = new Information();
					
					String username = rs.getString("username");	
					
					info.setUsername(username);
					info.setName(rs.getString("name"));
					info.setSignature(rs.getString("signature"));
					info.setGrade(0);
					info.setKlass(0);
					
					//查personal_hobby_table，得到hobbyList
					sqlString = "select hobby from "+personalHobbyTable+" where username='"+username+"'";
					subRS = subStatement.executeQuery(sqlString);
					
					ArrayList<String> hobbyList=new ArrayList<String>();
					while(subRS.next())
					{
						int hobbyNum = subRS.getInt("hobby");
						String hobby = hobbyDefinitionList.get(hobbyNum-1);
						hobbyList.add(hobby);
					}
					//hobbyList OK!
					info.setHobbyList(hobbyList);
					
					
					ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
					
					//查schedule_table
					sqlString = "select year, month, day, content, location from "+scheduleTable+" where username='"+username+"'";
					subRS = subStatement.executeQuery(sqlString);
					
					while(subRS.next())
					{
						Schedule schedule=new Schedule();						
						schedule.setUsername(username);
							
						schedule.setYear(subRS.getInt("year"));
						schedule.setMonth(subRS.getInt("month"));						
						schedule.setDay(subRS.getInt("day"));
						schedule.setContent(subRS.getString("content"));
						schedule.setLocation(subRS.getInt("location"));
							
						scheduleList.add(schedule);
					}
					
					//All OK
					TeacherInfo teacherInfo = new TeacherInfo();
					teacherInfo.setInformation(info);
					teacherInfo.setScheduleList(scheduleList);
					
					teacherInfoList.add(teacherInfo);
				}
				
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("Exception when handling getAllTeacherInfoList......");
				//e.printStackTrace();
			}
			
			close(connStmt);
		}
		return teacherInfoList;
	}

	@Override
	/** 返回内置的兴趣列表，即hobbyDefinitionList */
	public ArrayList<String> getHobbyDefinitionList() {
		// TODO Auto-generated method stub
		
		return hobbyDefinitionList;
	}

	@Override
	/** 返回内置的特长列表，即adeptnessDefinitionList */
	public ArrayList<String> getAdeptnessDefinitionList() {
		// TODO Auto-generated method stub
		
		return adeptnessDefinitionList;
	}

	@Override
	/** 搜索适合做项目的学生 
	 *  只查了特长表*/
	public ArrayList<Information> getSuitableStudentList(String teacherUsername, ArrayList<Integer> adeptnessNumList) {
		// TODO Auto-generated method stub
		
		ConnectionStatement connStmt = new ConnectionStatement();
		ResultSet rs = null;
		ArrayList<Information> studentList = new ArrayList<Information>();
		
		open(connStmt);
		
		if(connStmt.connection != null)
		{
			String sqlString = null;
			
			try {
				ArrayList<String> usernameList = new ArrayList<String>();
				
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
						usernameList = tempList;
					}
					else
					{
						usernameList = joinList(usernameList, tempList);
					}					
				}
				
				//get information
				for(int i=0; i<usernameList.size(); i++)
				{
					String username = usernameList.get(i);
					
					Information info = new Information();
					
					//查user_table，得到name,grade,klass,signature
					sqlString="select name,grade,klass,signature from "+userTable+" where username='"+username+"'";
					rs=connStmt.statement.executeQuery(sqlString);
				
					if(rs.next()){
						String name=rs.getString("name");
						int grade=rs.getInt("grade");
						int klass=rs.getInt("klass");
						String signature=rs.getString("signature");
						info.setUsername(username);
						info.setName(name);
						info.setGrade(grade);
						info.setKlass(klass);
						info.setSignature(signature);			
					}
					
					//查personal_hobby_table，得到hobbyList
					sqlString="select hobby from "+personalHobbyTable+" where username='"+username+"'";
					rs=connStmt.statement.executeQuery(sqlString);
					
					ArrayList<String> hobbyList=new ArrayList<String>();
					while(rs.next()){
						int hobbyNum=rs.getInt("hobby");
						String hobby=hobbyDefinitionList.get(hobbyNum-1);
						hobbyList.add(hobby);
					}
					//hobbyList OK!
					info.setHobbyList(hobbyList);
				
					//查personal_adeptness_table，得到adeptnessList
					sqlString="select adeptness from "+personalAdeptnessTable+" where username='"+username+"'";
					rs=connStmt.statement.executeQuery(sqlString);
				
					ArrayList<String> adeptnessList=new ArrayList<String>();			
					while(rs.next()){
						int adeptnessNum=rs.getInt("adeptness");
						String adeptness=adeptnessDefinitionList.get(adeptnessNum-1);
						adeptnessList.add(adeptness);
					}
					//adeptnessList OK!
					info.setAdeptnessList(adeptnessList);
								
					//查personal_favourite_teacher_table，得到favouriteTeacherList
					sqlString="select favourite_teacher from "+personalFavouriteTeacherTable+" where username='"+username+"'";
					rs=connStmt.statement.executeQuery(sqlString);
				
					ArrayList<String> favouriteTeacherList=new ArrayList<String>();
					while(rs.next()){
						String favouriteTeacher=rs.getString("favourite_teacher");
						favouriteTeacherList.add(favouriteTeacher);
					}
					//favouriteTeacherList OK!
					info.setFavouriteTeacherList(favouriteTeacherList);
				
					
					//info对象全部初始化完成
					studentList.add(info);
				}
				
			} catch (SQLException e) {
				// TODO: handle exception
				System.out.println("Exception when handling getSuitableStudentList......");
				//e.printStackTrace();
			}
			
			close(connStmt);			
		}	
		return studentList;
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


/*	public static void main(String[] args)
	{
		InformationOperation informationOperation = new InformationOperation();
		
		ArrayList<Integer> adeptnessNumList = new ArrayList<Integer>();
		adeptnessNumList.add(5);
		adeptnessNumList.add(4);
		
		ArrayList<Information> studentList = informationOperation.getSuitableStudentList("001", adeptnessNumList);
		
		System.out.println(studentList.size());
		System.out.println(studentList.get(studentList.size()-1).getHobbyList().get(0));
	}*/
	
}
