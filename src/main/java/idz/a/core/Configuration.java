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
 *  Klasa przechowujaca dane konfiguracyjne dla
 *   adapterow wejsciowych i wyjsciowych oraz funkcjonowania programu
 *  */	
public class Configuration {

	private static String inputAdapter, outputAdapter, inputFilePath,
			outputFilePath, inputURL, DBLogin, DBPassword, DBName, DBTable;
	private static int inputSocket, batchSize, DBPort;
	Properties prop = new Properties();
	InputStream input = null;
	
	/** 
	 * Inicjalizacja instancji konfiguracji poprzez odwolanie 
	 *   sie do pliku znajduj¹cego sie na sciezce przekazanej w  
	 *  lancuchu jako parametr konstruktora
	 *  Wykorzystanie klasy property do zebrania danych z pliku tekstowego
	 *   przechowujacego dane konfiguracyjne.
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
	
	 * Zwraca lancuch z nazwa wybranego adaptera wejsciowego
	 * 
	 * @return nazwa adaptera
	 */
	public String getInputAdapter() {
		return inputAdapter;
	}
	
/**
 * 	/** 
	 * Zwraca lancuch z nazwa wybranego adaptera wyjsciowego
	 *
 * @return nazwa adatera
 */
	public String getOutputAdapter() {
		return outputAdapter;
	}
	/**
	 * ** 
	 * Zwraca lancuch z sciezk¹ pliku dla adaptera wejsciowego
	 * @return sciezka pliku
	 */
	public String getInputFilePath() {
		return inputFilePath;
	}
	/**
	 * /** 
	 * Zwraca lancuch z sciezk¹ pliku dla adaptera wyjsciowego
	 * 
	 * @return sciezka pliku
	 */
	public String getOutputFilePath() {
		return outputFilePath;
	}
/**
 *  
 * @return Zwraca gniazdo wejsciowe
 */
	public int getInputSocket() {
		return inputSocket;
	}
/**
 * 
 * @return Zwraca wejsciowy adres URl 
 */
	public String getInputURL() {
		return inputURL;
	}
/**
 * 
 * @return Zwraca wielkosc
 */
	public int getBatchSize() {
		return batchSize;
	}
/**
 * 
 * @return Zwraca login.
 */
	public String getDBLogin() {
		return DBLogin;
	}
/**
 * 
 * @return Zwraca haslo.
 */
	public String getDBPassword() {
		return DBPassword;
	}
/**
 * 
 * @return Zwraca nazwe
 */
	public String getDBName() {
		return DBName;
	}
/**
 * 
 * @return Zwraca tabele.
 */
	public String getDBTable() {
		return DBTable;
	}
/**
 * 
 * @return Zwraca port.
 */ 
	public int getDBPort() {
		return DBPort;
	}

}