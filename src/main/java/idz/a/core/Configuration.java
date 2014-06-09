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
public class dokumentacjaConfiguration{
	

	/** 
	 *@autor Kamil Klarecki 
	 *  Klasa przechowuj�ca dane konfiguracyjne dla
	 *   adapter�w wej�ciowych i wyj�ciowych oraz funkcjonowania programu
	 *  */
		
	}
public class Configuration {

	private static String inputAdapter, outputAdapter, inputFilePath,
			outputFilePath, inputURL, DBLogin, DBPassword, DBName, DBTable;
	private static int inputSocket, batchSize, DBPort;
	Properties prop = new Properties();
	InputStream input = null;
	
	public class dokumentacjaConfiguration(String path){

		/** 
		 *@autor Kamil Klarecki 
		 * Inicjalizacja instancji konfiguracji poprzez odwo�anie 
		 *  si� do pliku znajduj�cego si� na �cie�ce przekazanej w 
		 *  �a�cuchu jako parametr konstruktora
		 *  Wykorzystanie klas� property do zebrania danych z pliku tekstowego
		 *   przechowuj�cego dane konfiguracyjne.
		 *   W razie b��du zamyka kana� wej�ciowy pliku

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
	
	public class dokumentacjaString getInputAdapter()){

		/** 
		 *@autor Kamil Klarecki 
		 *  Zwraca �a�cuch z nazw� wybranego adaptera wej�ciowego
		 *  */
	public static String getInputAdapter() {
		return inputAdapter;
	}
	public class dokumentacjaString getInputAdapter()){

		/** 
		 *@autor Kamil Klarecki 
		 *  Zwraca �a�cuch z nazw� wybranego adaptera wyj�ciowego
		 *  */
	public static String getOutputAdapter() {
		return outputAdapter;
	}
	/** 
	 *@autor Kamil Klarecki 
	 *  Zwraca �a�cuch z �cie�k� pliku dla adaptera wyj�ciowego
	 *  */
	public static String getInputFilePath() {
		return inputFilePath;
	}
	public class dokumentacja{
	/** 
	 *@autor Kamil Klarecki 
	 *  Zwraca �a�cuch z �cie�k� pliku dla adaptera wej�ciowego
	 *  */
	}
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