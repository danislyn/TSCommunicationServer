package database.connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;

public class PublicConnection {
	
	private  static String user;
	
	private static String password;
	
	private static String url;
	
	private static String driver;
	
	private static DBConnectionPool pool;
	
	//静态初始化
	static
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("db.ini")));
			
			user = br.readLine();
			password = br.readLine();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("db.int not found");
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("read from db.int exception");
			e.printStackTrace();
		}
		
		url = "jdbc:mysql://localhost:3306/ts_communication?useUnicode=true&characterEncoding=GBK";
		driver = "com.mysql.jdbc.Driver";
		
		pool = new DBConnectionPool(user, password, url, driver, 98);
	}
	
	
	/**
	 * 申请一个连接（默认最大等待时间为1000毫秒）
	 * @return Connection
	 */
	public static Connection getConnection() 
	{
		//设置最大等待时间为1000毫秒
		return pool.getDBConnection(1000);
	}
	
	/**
	 * 归还一个连接
	 * @param connection 可用连接
	 */
	public static void freeConnection(Connection connection)
	{
		pool.freeDBConnection(connection);
	}
	
}
