
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
	
  public String getInputAdapter() {
		return inputAdapter;
	}

	public String getOutputAdapter() {
		return outputAdapter;
	}

	public String getInputFilePath() {
		return inputFilePath;
	}

	public String getOutputFilePath() {
		return outputFilePath;
	}

	public int getInputSocket() {
		return inputSocket;
	}

	public int getBatchSize() {
		return batchSize;
	}

}