package idz.a.output;

import java.net.Socket;
import java.net.URI;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import idz.a.core.Configuration;
import idz.a.core.Event;
/**
 * Klasa nawiazuje polaczenie z baza danych oraz dopisuje do niej logi
 */
public class DBOutputAdapter implements OutputAdapter {

	private int port;
	private String dbName;
	private String login;
	private String password;
	private String table;
	public boolean connectToSourceFlag = false;
	private Connection connection = null;
	private String sql;

	public DBOutputAdapter() {
		
	}

	@Override
	/**
	 * Pobiera z konfiguracji dane dla funkcjonowania adaptera
	 */
	public void setupConfig(Configuration config) {
		  login = config.getDBLogin(); 
		  password = config.getDBPassword(); 
		  port = config.getDBPort(); 
		  dbName = config.getDBName(); 
		  table = config.getDBTable();
	}

	@Override
	/**
	 * Wstawia w tabelê logow nowy wiersz zawierajacy dane loga
	 */
	public boolean storeEvents(List<Event> batch) {
		PreparedStatement stmt = null;
		sql = "INSERT INTO "+ table +" (TIMESTAMP, DETAILS, LOGLEVEL) VALUES (?, ?, ?)";
		if (connection != null) {
			for (Event go : batch) {
				try {
					stmt = connection.prepareStatement(sql);
					stmt.setString(1, go.getTimestamp().toString());
					stmt.setString(2, go.getDetails());
					stmt.setString(3, go.getLoglevel().toString());
					stmt.addBatch();
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
				
				try {
					stmt.executeBatch();
					System.out.println(batch.size() + " records added to database.");
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
			return true;
		}
		else{
			System.out.println("No connection.");
		}
		return false;
	}

	public boolean connectToSource() {
		System.out.println("Connecting to database...");
		connection = getConnection();
		if (connection != null) {
			System.out.println("Connected successfully.");
			connectToSourceFlag = true;
			return true;
		} else {
			System.out.println("Failed to connect.");
			connectToSourceFlag = false;
		}
		return false;
	}

	public Connection getConnection() {
		Connection connection = null;
		String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
		String url = "jdbc:db2://localhost:" + port + "/" + dbName;
		try {
			Class.forName(jdbcClassName);
			connection = DriverManager.getConnection(url, login, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
