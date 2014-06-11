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
	static int batchSize = 0;
	static int countIn = 0, countOut = 0;
	static String inputName, outputName;

	/**
	 * Konstruktor przyjmujacy za parametr sciezke pliku inicjalizujacego
	 * konfiguracji. Tworzy nowa konfiguracje w oparciu o podana sciezke. Na
	 * podstawie konfiguracji ustala rozmiar kolejki logow oraz wejsciowy i
	 * wyjsciowy adapter wedlug nazwy.
	 * 
	 * @param path
	 */
	public AppCore(String path) {
		conf = new Configuration(path);
		batchSize = conf.getBatchSize();
		inputName = conf.getInputAdapter();
		outputName = conf.getOutputAdapter();
	}

	/**
	 * Tworzy obiekt adaptera implementujacego interface InputAdapter o zadanej
	 * nazwie.
	 * 
	 * @param name
	 *            nazwa interfejsu
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
	 * Tworzy obiekt adaptera implementujacego interfejs Output InputAdapter o
	 * zadanej nazwie
	 * 
	 * @param name
	 *            nazwa parametru
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
	 * Wykorzystuje metody ladujace adapter wejsciowy i wyjsciowy ze sciezki o
	 * zadanej nazwie i powoduje nowa instancj�ｿｽ instancj� menadzera kolejki
	 * */
	private static void setUp() {
		loadInputAdapter("idz.a.input." + inputName);
		loadOutputAdapter("idz.a.output." + outputName);
		queue = new QueueManager();
		queue.setBatchSize(conf.getBatchSize());
		batchSize = conf.getBatchSize();
	}

	/**
	 * Inicjalizuje pola adapter wykorzystujac istniejaca konfiguracje Dolacza
	 * obiekt menadzera kolejki inicjalizuje pola adaptera wyjsciowego
	 */
	private static void invokeAdapterMethods() {
		in.setupConfig(conf);
		in.connectToQueueManager(queue);
		out.setupConfig(conf);
	}

	/**
	 * Tworzy obiekt ApCore Wywoluje setUp i invokeAdapterMethod oraz wczytuje
	 * logi w nieskonczonej petl
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new AppCore(configPath);
		setUp();
		invokeAdapterMethods();
		while (true) {
			if (countIn > 10 || countOut > 10)
				break;
			if (queue.currentSize() < batchSize) {
				if (!in.readLog()) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					countIn++;
				} else
					countIn = 0;
			} else
				System.out.println("Queue is full");
			if (queue.currentSize() > 0) {
				if (!queue.sendEvents()) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					countOut++;
				} else
					countOut = 0;
			} else
				System.out.println("Queue is empty");
		}
	}
}
