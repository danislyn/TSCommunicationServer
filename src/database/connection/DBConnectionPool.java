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
	private int checkedOut;  //当前连接数
	
	private ArrayList<Connection> freeConn;

	/**
	 * 初始化连接池
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
	* 从连接池获得一个可用连接.如没有空闲的连接且当前连接数小于最大连接
	* 数限制,则创建新连接.如原来登记为可用的连接不再有效,则从向量删除之,
	* 然后递归调用自己以尝试新的可用连接.
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
	 * 从连接池获取可用连接.可以指定客户程序能够等待的最长时间
	 * 
	 * @param timeout  等待连接的最大时间，超时则返回null
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
	 * 新建一个Connection
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
	 * 将不再使用的连接返回给连接池
	 * 
	 * @param conn 客户程序释放的连接
	 */
	public synchronized void freeDBConnection(Connection conn){
		if(conn != null)
		{
			freeConn.add(conn);
			checkedOut--;
			notifyAll(); //重新启动wait（）所在的函数
		}
	}


	/**
	 * 关闭所有的连接
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
