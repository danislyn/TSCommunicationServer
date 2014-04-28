package database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;


public class DBConnectionPool {
	
	private String dbUserName;
	private String dbPassWord;
	private String dbConnUrl;
	private String dbDriver;
	
	private int maxConn;
	private int checkedOut;  //��ǰ������
	
	private ArrayList<Connection> freeConn;

	/**
	 * ��ʼ�����ӳ�
	 */
	public DBConnectionPool(String dbUserName, String dbPassWord, String dbConnUrl, String dbDriver, int maxConn) {
		super();
		// TODO Auto-generated constructor stub
		this.dbUserName = dbUserName;
		this.dbPassWord = dbPassWord;
		this.dbConnUrl = dbConnUrl;
		this.dbDriver = dbDriver;
		this.maxConn = maxConn;
		this.freeConn = new ArrayList<Connection>();
	}

	/**
	* �����ӳػ��һ����������.��û�п��е������ҵ�ǰ������С���������
	* ������,�򴴽�������.��ԭ���Ǽ�Ϊ���õ����Ӳ�����Ч,�������ɾ��֮,
	* Ȼ��ݹ�����Լ��Գ����µĿ�������.
	*/
	public synchronized Connection getDBConnection()
	{
		Connection conn=null;
		if(freeConn.size()>0)
		{
			conn=freeConn.get(0);
			freeConn.remove(0);
			try {
				if(conn.isClosed())
				{
					conn=getDBConnection();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
		}
		else if(maxConn==0 || checkedOut<maxConn)
		{
			conn=newDBConnection();
		}
       
		if(conn!=null){
			checkedOut++;
		}
       
		return conn;
	}

	/**
	 * �����ӳػ�ȡ��������.����ָ���ͻ������ܹ��ȴ����ʱ��
	 * 
	 * @param timeout  �ȴ����ӵ����ʱ�䣬��ʱ�򷵻�null
	 */          
	public synchronized Connection getDBConnection(long timeout)
	{
		long starttime=System.currentTimeMillis();
		Connection conn = null;
       
		while((conn=getDBConnection()) == null)
		{
			try {
				wait(timeout);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(System.currentTimeMillis()-starttime >= timeout)
			{
				return null;
			}
		}
		return conn;
	}

	/**
	 * �½�һ��Connection
	 * @return Connection
	 */
	private synchronized Connection newDBConnection()
	{
		Connection conn=null;
		try{
			if(this.dbUserName==null)
			{
				try {
					Class.forName(dbDriver);
					conn=DriverManager.getConnection(this.dbConnUrl);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
			else
			{
				try {
					Class.forName(dbDriver);
					conn=DriverManager.getConnection(this.dbConnUrl,this.dbUserName,this.dbPassWord);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
		return conn;
	}

	/**
	 * ������ʹ�õ����ӷ��ظ����ӳ�
	 * 
	 * @param conn �ͻ������ͷŵ�����
	 */
	public synchronized void freeDBConnection(Connection conn){
		if(conn != null)
		{
			freeConn.add(conn);
			checkedOut--;
			notifyAll(); //��������wait�������ڵĺ���
		}
	}


	/**
	 * �ر����е�����
	 */
	public synchronized void release(){
		Iterator<Connection> iter=freeConn.iterator();
		Connection conn;
		while(iter.hasNext()){
			try{
				conn=iter.next();
				conn.close();
			}catch(SQLException ex){
				ex.printStackTrace(); 
			}
		}
	
		while(iter.hasNext())
		{
			freeConn.remove(iter.next());
		}
	}

}
