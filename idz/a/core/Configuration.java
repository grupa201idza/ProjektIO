
package idz.a.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class Configuration {
	
	private static String inputAdapter, outputAdapter, inputFilePath, outputFilePath;
	private static int inputSocket,batchSize;
	Properties prop = new Properties();
	InputStream input = null;

	
	public Configuration(String path){
		
		
		try {	
			File conf = new File(path);
			input = new FileInputStream(conf);	 
			// load a config file
			prop.load(input);	 
			// read values
			inputAdapter=prop.getProperty("InputAdapter");
			outputAdapter=prop.getProperty("OutputAdapter");
			inputFilePath=prop.getProperty("inputFilePath");
			outputFilePath=prop.getProperty("outputFilePath");
			batchSize=Integer.parseInt(prop.getProperty("BatchSize"));
			inputSocket=Integer.parseInt(prop.getProperty("inputSocket"));
			
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
	
  public static String getInputAdapter() {
		return inputAdapter;
	}

	public static String getOutputAdapter() {
		return outputAdapter;
	}

	public static String getInputFilePath() {
		return inputFilePath;
	}

	public static String getOutputFilePath() {
		return outputFilePath;
	}

	public static int getInputSocket() {
		return inputSocket;
	}

	public static int getBatchSize() {
		return batchSize;
	}

public static void main(String[] args) {
 
	  new Configuration("src/idz/a/core/Config.txt");
	  //System.out.println(getInputAdapter()); //TEST
	  //System.out.println(getBatchSize()); //TEST
  }
}