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
 *  Klasa przechowuj¹ca dane konfiguracyjne dla
 *  adapterow wejœciowych i wyjsciowych oraz funkcjonowania programu
 */
public class Configuration {

	private static String inputAdapter, outputAdapter, inputFilePath,
			outputFilePath, inputURL, DBLogin, DBPassword, DBName, DBTable;
	private static int inputSocket, batchSize, DBPort;
	Properties prop = new Properties();
	InputStream input = null;
	
	/**
	 * Inicjalizacja instancji konfiguracji poprzez odwolanie 
	 *  siê do pliku znajduj¹cego sie na sciezce przekazanej w 
	 *  lancuchu jako parametr konstruktora
	 *  Wykorzystanie klase property do zebrania danych z pliku tekstowego
	 *   przechowuj¹cego dane konfiguracyjne.
	 *   W razie b³êdu zamyka kana³ wejœciowy plik
	 */
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
	 *  Zwraca ³añcuch z nazw¹ wybranego adaptera wejœciowego
	 *  */
	public static String getInputAdapter() {
		return inputAdapter;
	}
	
	/** 
	 *  Zwraca ³añcuch z nazw¹ wybranego adaptera wyjœciowego
	 *  */
	public static String getOutputAdapter() {
		return outputAdapter;
	}
	/** 
	 *  Zwraca ³añcuch z œcie¿k¹ pliku dla adaptera wejœciowego
	 *  */
	public static String getInputFilePath() {
		return inputFilePath;
	}
	/** 
	 *  Zwraca ³añcuch z œcie¿k¹ pliku dla adaptera wyjœciowego
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