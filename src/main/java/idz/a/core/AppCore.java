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
 *  Rdzeñ funkcjonalny aplikacji.
 *  Przechowuje obiekty konfiguracji i adapterów.
 *  Posiada ³añcuch œcie¿ki inicjalizuj¹cej.
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
		 *  Konstruktor przyjmuj¹cy za parametr œciêzkê pliku incjalizucego 
		 *  konfiguracji.
		 *  Two¿y now¹ konfiguracje w oparciu o podan¹ scie¿kê.
		 *  Na podstawie konfiguraci ustala rozmiar kolejki logów oraz wejœciowy i 
		 *  wyjœciowy adapter wed³ug nazwy.
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
		 *  *  Tworzy obiekt adaptera implementuj¹cego interface 
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
		 *  *  Tworzy obiekt adaptera implementuj¹cego interface 
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
		 *  Wyko¿ystuje metody ³aduj¹ce adapter wejœciowy i wyjsciowy 
		 *  ze œcie¿ki o zadanej nazwie i powoduje now¹ instancjê menad¿era kolejki
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
		 *  Inicjalizuje pola adapter wyko¿ystuj¹c istniej¹c¹ konfiruracjê
		 *  Do³¹cza obiekt menad¿era kolejki incjalizuje pola adaptera wyjœciowego
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
		 *  Wywo³uje  setUp i invokeAdapterMethod
		 *  oraz wcztuje logi w nieskoñczonej pêtli
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
