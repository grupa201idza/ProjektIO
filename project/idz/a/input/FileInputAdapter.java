package idz.a.input;

import idz.a.core.Configuration;
import idz.a.core.Event;
import idz.a.core.QueueManager;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Scanner;


public class FileInputAdapter implements InputAdapter {

	private Configuration config;
	private QueueManager queue;
	private String logPath;
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	private Event event;
	
	public FileInputAdapter() {
		super();
	}
	
	/** Constructor of FileInputAdapter */
	public FileInputAdapter(Configuration config, QueueManager queue) {
		setupConfig(config);
		connectToQueueManager(queue);
		logPath = Configuration.getInputFilePath();
		
	}
	
	/** Method returning Path from given String path
	 *  @param fileName full name of an existing, readable file */
	private Path getPath(String fileName) {
	    return Paths.get(fileName);
	}
	
	/** Method checking if given log file path exists */
	private boolean connectToSource() {
		int connectionAttempts = 0;
		while (connectionAttempts < 5) {
			try {
				Scanner sc = new Scanner(getPath(logPath), ENCODING.name());
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
	 * @throws IOException Exception caught in connectToSource() */
	private void processLogFile() throws IOException {
		if (connectToSource()) {
			Scanner scanner =  new Scanner(getPath(logPath), ENCODING.name());
			// any log left? is there space in queue?
			while (scanner.hasNextLine() && queue.currentSize() < Configuration.getBatchSize()){
				event = parseEvent(scanner.nextLine());
				if (!event.equals(null) && !event.getTimestamp().equals(null) && !event.getDetails().equals(null) && !event.getLoglevel().equals(null)) {
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
	 *  @param log scanned line from log file */
	public Event parseEvent(String log){
		Scanner scanner = new Scanner(log);
		scanner.useDelimiter(" ");
		if (scanner.hasNext()){
			Event event = new Event(null, null, null);
			try {
				String date = scanner.next();
				event.setTimestamp(Timestamp.valueOf(timeFormat(date)));
			} catch (IllegalArgumentException e) {
				System.err.println("Wrong Timestamp");
				event.setTimestamp(null);
			}
			
			try {
				String level = scanner.next();
				event.setLoglevel(Event.Enum.LogLevel.valueOf(level));;
			} catch (IllegalArgumentException e) {
				System.err.println("Wrong Loglevel");
				event.setLoglevel(null);
			}
			
			try {
				String detail = new String();
				while (scanner.hasNext()) {
					detail = detail.concat(" " +scanner.next());
				}
				event.setDetails(detail.trim());
			} catch (IllegalArgumentException e) {
				System.err.println("Wrong Detail");
				event.setDetails(null);
			}
			scanner.close();
			return event;
		}
		else {
			System.out.println("Empty or invalid line. Unable to process.");
			scanner.close();
			return null;
	    }
	}
	
	/** Method formating log date
	 *  @param in date to format */
	private String timeFormat (String in) {
		return in.replace("(", "").replace(")","").replace("T", " ");
	}
	
	public void setupConfig(Configuration config) {
		this.config = config;
	}
	
	public void connectToQueueManager(QueueManager queue) {
		this.queue = queue;
	}

}