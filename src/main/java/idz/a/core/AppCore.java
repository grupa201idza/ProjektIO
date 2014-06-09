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
 *  Rdze� funkcjonalny aplikacji.
 *  Przechowuje obiekty konfiguracji i adapter�w.
 *  Posiada �a�cuch �cie�ki inicjalizuj�cej.
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
	
public class dokumentacjaAppCore(String path){

		/** 
		 *@autor Kamil Klarecki 
		 *  Konstruktor przyjmuj�cy za parametr �ci�zk� pliku incjalizucego 
		 *  konfiguracji.
		 *  Two�y now� konfiguracje w oparciu o podan� scie�k�.
		 *  Na podstawie konfiguraci ustala rozmiar kolejki log�w oraz wej�ciowy i 
		 *  wyj�ciowy adapter wed�ug nazwy.
		 *  */
			
		}
	public AppCore(String path) {
		conf = new Configuration(path);
		batchSize = conf.getBatchSize();
		inputName = conf.getInputAdapter();
		outputName = conf.getOutputAdapter();
	}
	public class dokumentacjaloadInputAdapter{
		

		/** 
		 *@autor Kamil Klarecki 
		 *  *  Tworzy obiekt adaptera implementuj�cego interface 
		 *  InputAdapter o zadanej nazwie
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
	
	public class dokumentacjaloadOutputAdapter{
		

		/** 
		 *@autor Kamil Klarecki 
		 *  *  Tworzy obiekt adaptera implementuj�cego interface 
		 * Output InputAdapter o zadanej nazwie
		 *  */
			
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
		 *  Wyko�ystuje metody �aduj�ce adapter wej�ciowy i wyjsciowy 
		 *  ze �cie�ki o zadanej nazwie i powoduje now� instancj� menad�era kolejki
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
		 *  Inicjalizuje pola adapter wyko�ystuj�c istniej�c� konfiruracj�
		 *  Do��cza obiekt menad�era kolejki incjalizuje pola adaptera wyj�ciowego
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
		 *  Tworzy obiekt ApCore
		 *  Wywo�uje  setUp i invokeAdapterMethod
		 *  oraz wcztuje logi w niesko�czonej p�tli
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
