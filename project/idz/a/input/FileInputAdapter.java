package idz.a.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Scanner;

import idz.a.core.Configuration;
import idz.a.core.Event;
import idz.a.core.QueueManager;


public class FileInputAdapter implements InputAdapter {

	private Configuration config;
	private QueueManager queue;
	private String logFile = new String("D:\\server.log.1.txt"); // roboczy path
	private String logPath;
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	Event event;
	
	public FileInputAdapter() {
		super();
	}
	
	/** Constructor */
	public FileInputAdapter(Configuration config, QueueManager queue) {
		setupConfig(config);
		connectToQueueManager(queue);
		logPath = config.getInputFilePath();
		
	}
	
	/** Method returning Path from given String path
	 *  @param fileName - full name of an existing, readable file */
	private Path getPath(String fileName) {
	    return Paths.get(fileName);
	}
	
	/** Method checking if given log file path exists */
	private boolean connectToSource() {
		int connectionAttempts = 0;
		while (connectionAttempts < 5) {
			try {
				Scanner sc = new Scanner(getPath(logFile), ENCODING.name());
				sc.close();
			} catch (IOException e) {
				System.out.println("Attempting to connect to source");
				connectionAttempts++;
				continue;
			}
			return true;
		}
		return false;
	}
	
	/** Method processing log file line by line 
	 * @throws IOException - Exception caught in connectToSource() */
	private void processLogFile() throws IOException {
		if (connectToSource()) {
			Scanner scanner =  new Scanner(getPath(logFile), ENCODING.name());
			// any log left? is there space in queue?
			while (scanner.hasNextLine() && queue.currentSize() < queue.batchSize){
				event = parseEvent(scanner.nextLine());
				if (!event.equals(null)) {
					queue.acceptEvent(event);
				}
				else continue;
			}
			scanner.close();
		}
		else {
			System.err.println("Log source not found!");
			System.exit(1);
		}
	}
	
	/** Method for parsing line's content to Event
	 *  @param log - scanned line from log file */
	private Event parseEvent(String log){
		Scanner scanner = new Scanner(log);
		scanner.useDelimiter(" ");
		if (scanner.hasNext()){
			String date = scanner.next();
			Timestamp ts = Timestamp.valueOf(timeFormat(date));
			System.out.println("Timestamp: " +ts);
			
			String level = scanner.next();
			Event.Enum.LogLevel loglvl = Event.Enum.LogLevel.valueOf(level);
			System.out.println(loglvl);
			
			String detail = new String();
			while (scanner.hasNext()) {
				detail = detail.concat(" " +scanner.next());
			}
			detail = detail.trim();
			System.out.println("Detail: " +detail.trim());
			
			scanner.close();
			return new Event(ts, detail, loglvl);
		}
		else {
			System.out.println("Empty or invalid line. Unable to process.");
			scanner.close();
			return null;
	    }
	}
	
	/** Method formating log date
	 *  @param in - date to format */
	private String timeFormat (String in) {
		return in.replace("(", "").replace(")","").replace("T", " ");
	}
	
	@Override
	public void setupConfig(Configuration config) {
		this.config = config;
	}

	@Override
	public void connectToQueueManager(QueueManager queue) {
		this.queue = queue;
	}
	
	
	public static void main(String[] args) throws IOException {
		FileInputAdapter f = new FileInputAdapter();
		f.processLogFile();
	}
}