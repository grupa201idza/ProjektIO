package idz.a.core;

import idz.a.input.InputAdapter;
import idz.a.output.OutputAdapter;

/**
 * @author Bartosz Domin
 * 
 */

public class AppCore {

	static String configPath = "project/idz/a/core/Config.txt";
	static InputAdapter in;
	static OutputAdapter out;
	static Configuration conf;
	static QueueManager queue;
	int batchSize = 0;
	static String inputName, outputName;
	/**
		 * Konstruktor przyjmuj¹cy za parametr œcie¿kê pliku inicjalizuj¹cego konfiguracji.
		 * Tworzy now¹ konfiguracje w oparciu o podan¹ œciezke.
		 * Na podstawie konfiguracji ustala rozmiar kolejki logów oraz wejœciowy i wyjœciowy 
		 * adapter wed³ug nazwy.
	 * @param path
	 */
	public AppCore(String path) {
		conf = new Configuration(path);
		batchSize = conf.getBatchSize();
		inputName = conf.getInputAdapter();
		outputName = conf.getOutputAdapter();
	}
	
	/**
	 * Tworzy obiekt adaptera implementuj¹cego interface 
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
	 *    Tworzy obiekt adaptera implementuj¹cego interfejs
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
	 * Wykorzystuje metody ³aduj¹ce adapter wejœciowy i wyjœciowy 
	 *  ze œcie¿ki o zadanej nazwie i powoduje now¹ instancjê menad¿era kolejki
	 *  */
	private static void setUp() {
		loadInputAdapter("idz.a.input." + inputName);
		loadOutputAdapter("idz.a.output." + outputName);
		queue = new QueueManager();
	}
	
	/**
	 * Inicjalizuje pola adapter wykorzystuj¹c istniejac¹ konfiguracje
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
