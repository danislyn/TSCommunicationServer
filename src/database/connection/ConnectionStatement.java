package database.connection;

import java.sql.Connection;
import java.sql.Statement;


public class ConnectionStatement {

	public Connection connection;
	public Statement statement;
	
	/**
	 * ����
	 */
	public ConnectionStatement()
	{
		connection = null;
		statement = null;
	}
	
}
