package idz.a.core;

import idz.a.input.InputAdapter;
import idz.a.output.OutputAdapter;

/**
 * @author Bartosz Domin
 * 
 */

public class AppCore {

	static String configPath = "src/main/java/idz/a/core/Config.txt";
	static InputAdapter in;
	static OutputAdapter out;
	static Configuration conf;
	static QueueManager queue;
	int batchSize = 0;
	static String inputName, outputName;
	/**
		 * Konstruktor przyjmujｹcy za parametr 彡ieｿk� pliku inicjalizujｹcego konfiguracji.
		 * Tworzy nowｹ konfiguracje w oparciu o podanｹ 彡iezke.
		 * Na podstawie konfiguracji ustala rozmiar kolejki log oraz wej彡iowy i wyj彡iowy 
		 * adapter wedｳug nazwy.
	 * @param path
	 */
	public AppCore(String path) {
		conf = new Configuration(path);
		batchSize = conf.getBatchSize();
		inputName = conf.getInputAdapter();
		outputName = conf.getOutputAdapter();
	}
	
	/**
	 * Tworzy obiekt adaptera implementujｹcego interface 
	 *  InputAdapter o zadanej nazwie.
	 * @param name nazwa interfejsu
	 */
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
	
	/**
	 *    Tworzy obiekt adaptera implementujｹcego interfejs
	 * Output InputAdapter o zadanej nazwie
	 * @param name nazwa parametru
	 */
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
	/** 
	 * Wykorzystuje metody ｳadujｹce adapter wej彡iowy i wyj彡iowy 
	 *  ze 彡ieｿki o zadanej nazwie i powoduje nowｹ instancj� menadｿera kolejki
	 *  */
	private static void setUp() {
		loadInputAdapter("idz.a.input." + inputName);
		loadOutputAdapter("idz.a.output." + outputName);
		queue = new QueueManager();
		queue.setBatchSize(conf.getBatchSize());
	}
	
	/**
	 * Inicjalizuje pola adapter wykorzystujｹc istniejacｹ konfiguracje
	 *  Dolacza obiekt menadzera kolejki inicjalizuje pola adaptera wyjsciowego
	 */
	private static void invokeAdapterMethods() {
		in.setupConfig(conf);
		in.connectToQueueManager(queue);
		out.setupConfig(conf);
	}
	
	/**
	 * Tworzy obiekt ApCore
	 *  Wywoluje  setUp i invokeAdapterMethod
	 *  oraz wczytuje logi w nieskonczonej petl
	 * @param args
	 */
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
