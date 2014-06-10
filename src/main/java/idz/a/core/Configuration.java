package idz.a.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Bartosz Domin
 * 
 */


/** 
 *  Klasa przechowuj�ca dane konfiguracyjne dla
 *   adapter�w wej�ciowych i wyj�ciowych oraz funkcjonowania programu
 *  */	
public class Configuration {

	private static String inputAdapter, outputAdapter, inputFilePath,
			outputFilePath, inputURL, DBLogin, DBPassword, DBName, DBTable;
	private static int inputSocket, batchSize, DBPort;
	Properties prop = new Properties();
	InputStream input = null;
	
	/** 
	 * Inicjalizacja instancji konfiguracji poprzez odwolanie 
	 *  si� do pliku znajduj�cego sie na sciezce przekazanej w 
	 *  �a�cuchu jako parametr konstruktora
	 *  Wykorzystanie klas� property do zebrania danych z pliku tekstowego
	 *   przechowuj�cego dane konfiguracyjne.
	 *   W razie bledu zamyka kanal wejsciowy pliku
	  *  */
		
	public Configuration(String path) {

		try {
			File conf = new File(path);
			input = new FileInputStream(conf);
			// load a config file
			prop.load(input);
			// read values
			inputAdapter = prop.getProperty("InputAdapter");
			outputAdapter = prop.getProperty("OutputAdapter");
			inputFilePath = prop.getProperty("inputFilePath");
			outputFilePath = prop.getProperty("outputFilePath");
			batchSize = Integer.parseInt(prop.getProperty("BatchSize"));
			inputSocket = Integer.parseInt(prop.getProperty("inputSocket"));
			inputURL = prop.getProperty("inputURL");
			DBLogin = prop.getProperty("DBLogin");
			DBPassword = prop.getProperty("DBPassword");
			DBPort = Integer.parseInt(prop.getProperty("DBPort"));
			DBName = prop.getProperty("DBName");
			DBTable = prop.getProperty("DBTable");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * /** 
	 *  Zwraca lancuch z nazw� wybranego adaptera wejsciowego
	 *  
	 * @return nazwa adaptera
	 */
	public static String getInputAdapter() {
		return inputAdapter;
	}
	
/**
 * 	/** 
	 *  Zwraca lancuch z nazw� wybranego adaptera wyjsciowego
	 *
 * @return nazwa adatera
 */
	public static String getOutputAdapter() {
		return outputAdapter;
	}
	/**
	 * ** 
	 *  Zwraca lancuch z �cie�k� pliku dla adaptera wejsciowego
	 * @return sciezka pliku
	 */
	public static String getInputFilePath() {
		return inputFilePath;
	}
	/**
	 * /** 
	 *  Zwraca lancuch z sciezk� pliku dla adaptera wyjsciowego
	 * 
	 * @return sciezka pliku
	 */
	public static String getOutputFilePath() {
		return outputFilePath;
	}
/**
 * 
 * @return
 */
	public static int getInputSocket() {
		return inputSocket;
	}
/**
 * 
 * @return
 */
	public static String getInputURL() {
		return inputURL;
	}
/**
 * 
 * @return
 */
	public static int getBatchSize() {
		return batchSize;
	}
/**
 * 
 * @return
 */
	public static String getDBLogin() {
		return DBLogin;
	}
/**
 * 
 * @return
 */
	public static String getDBPassword() {
		return DBPassword;
	}
/**
 * 
 * @return
 */
	public static String getDBName() {
		return DBName;
	}
/**
 * 
 * @return
 */
	public static String getDBTable() {
		return DBTable;
	}
/**
 * 
 * @return
 */
	public static int getDBPort() {
		return DBPort;
	}

}