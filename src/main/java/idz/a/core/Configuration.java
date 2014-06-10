package main.java.idz.a.core;

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
 *  Klasa przechowujｹca dane konfiguracyjne dla
 *   adapter wej彡iowych i wyj彡iowych oraz funkcjonowania programu
 *  */	
public class Configuration {

	private static String inputAdapter, outputAdapter, inputFilePath,
			outputFilePath, inputURL, DBLogin, DBPassword, DBName, DBTable;
	private static int inputSocket, batchSize, DBPort;
	Properties prop = new Properties();
	InputStream input = null;
	
	/** 
	 * Inicjalizacja instancji konfiguracji poprzez odwoｳanie 
	 *  si� do pliku znajdujｹcego si� na 彡ieｿce przekazanej w 
	 *  ｳauchu jako parametr konstruktora
	 *  Wykorzystanie klas� property do zebrania danych z pliku tekstowego
	 *   przechowujｹcego dane konfiguracyjne.
	 *   W razie bｳ鹽u zamyka kanaｳ wej彡iowy pliku
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
	 *  Zwraca ｳauch z nazwｹ wybranego adaptera wej彡iowego
	 *  */
	public static String getInputAdapter() {
		return inputAdapter;
	}
	
	/** 
	 *  Zwraca ｳauch z nazwｹ wybranego adaptera wyj彡iowego
	 *  */
	public static String getOutputAdapter() {
		return outputAdapter;
	}
	/** 
	 *  Zwraca ｳauch z 彡ieｿkｹ pliku dla adaptera wej彡iowego
	 *  */
	public static String getInputFilePath() {
		return inputFilePath;
	}
	/** 
	 *  Zwraca ｳauch z 彡ieｿkｹ pliku dla adaptera wyj彡iowego
	 *  */
	public static String getOutputFilePath() {
		return outputFilePath;
	}

	public static int getInputSocket() {
		return inputSocket;
	}

	public static String getInputURL() {
		return inputURL;
	}

	public static int getBatchSize() {
		return batchSize;
	}

	public static String getDBLogin() {
		return DBLogin;
	}

	public static String getDBPassword() {
		return DBPassword;
	}

	public static String getDBName() {
		return DBName;
	}

	public static String getDBTable() {
		return DBTable;
	}

	public static int getDBPort() {
		return DBPort;
	}

}