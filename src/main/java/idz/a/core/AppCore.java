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
		 * Konstruktor przyjmuj�cy za parametr �cie�k� pliku inicjalizuj�cego konfiguracji.
		 * Tworzy now� konfiguracje w oparciu o podan� �ciezke.
		 * Na podstawie konfiguracji ustala rozmiar kolejki log�w oraz wej�ciowy i wyj�ciowy 
		 * adapter wed�ug nazwy.
	 * @param path
	 */
	public AppCore(String path) {
		conf = new Configuration(path);
		batchSize = conf.getBatchSize();
		inputName = conf.getInputAdapter();
		outputName = conf.getOutputAdapter();
	}
	
	/**
	 * Tworzy obiekt adaptera implementuj�cego interface 
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
	 *    Tworzy obiekt adaptera implementuj�cego interfejs
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
	 * Wykorzystuje metody �aduj�ce adapter wej�ciowy i wyj�ciowy 
	 *  ze �cie�ki o zadanej nazwie i powoduje now� instancj� menad�era kolejki
	 *  */
	private static void setUp() {
		loadInputAdapter("idz.a.input." + inputName);
		loadOutputAdapter("idz.a.output." + outputName);
		queue = new QueueManager();
	}
	
	/**
	 * Inicjalizuje pola adapter wykorzystuj�c istniejac� konfiguracje
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