package idz.a.core;

import idz.a.input.InputAdapter;
import idz.a.output.OutputAdapter;

/**
 * @author Bartosz Domin 
 * 
 */
public class dokumentacjaAppCore{
	

/** 
 *@autor Kamil Klarecki 
 *  
 *  */
	
}
public class AppCore {

	static String configPath = "project/idz/a/core/Config.txt";
	static InputAdapter in;
	static OutputAdapter out;
	static Configuration conf;
	static QueueManager queue;
	int batchSize = 0;
	static String inputName, outputName;

	public AppCore(String path) {
		conf = new Configuration(path);
		batchSize = conf.getBatchSize();
		inputName = conf.getInputAdapter();
		outputName = conf.getOutputAdapter();
	}
	public class dokumentacjaloadInputAdapter{
		

		/** 
		 *@autor Kamil Klarecki 
		 *  
		 *  */
			
		}
	private static void loadInputAdapter(String name) {

		try {
			Class input = Class.forName(name);
			in = (InputAdapter) input.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void loadOutputAdapter(String name) {

		try {
			Class output = Class.forName(name);
			out = (OutputAdapter) output.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	public class dokumentacjasetUp{
		

		/** 
		 *@autor Kamil Klarecki 
		 *  
		 *  */
			
		}
	private static void setUp() {
		loadInputAdapter("idz.a.input." + inputName);
		loadOutputAdapter("idz.a.output." + outputName);
		queue = new QueueManager();
	}
public class dokumentacjainvokeAdapterMethods{
		

		/** 
		 *@autor Kamil Klarecki 
		 *  
		 *  */
			
		}
	private static void invokeAdapterMethods() {
		in.setupConfig(conf);
		in.connectToQueueManager(queue);
		out.setupConfig(conf);
	}
public class dokumentacjaMain{
		

		/** 
		 *@autor Kamil Klarecki 
		 *  
		 *  */
			
		}
	public static void main(String[] args) {
		new AppCore(configPath);
		setUp();
		invokeAdapterMethods();
		while (true) {
			in.readLog();
			queue.sendEvents();
		}
	}
}
