package idz.a.output;

import java.net.Socket;
import java.net.URI;
import java.util.List;
import java.sql.Connection;
import idz.a.core.Configuration;

public class DBOutputAdapter implements OutputAdapter {
	
	private int port;
	private URI url;
	private String login;  
	private String password;
	private String table; 
	
	public DBOutputAdapter(Configuration configuration){
		setupConfig(configuration);
	}

	@Override
	public void setupConfig(Configuration config) {
		/*login = config.getDBLogin();
		password = config.getDBPassword();
		port = config.getDBPort();
		url = config.getDBUrl();
		table = config.getDBTable();*/
		
	}
	
	boolean connectToSource(){
		return false;
	}
	
	@Override
	public boolean storeEvents(List batch) {
		// JDBC methods
		return false;
	}
	
	public Connection getConnection(){
		Connection dbConnection = null;
		// setting dbConnection
		return dbConnection;
	}
}
