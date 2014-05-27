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

	Configuration config;
	QueueManager queue;
	//File inputFile = new File("D:\\test.txt");
	static String logFile = new String("D:\\server.log.1.txt"); // roboczy path
	BufferedReader in;
	FileReader fileRead;
	Path filePath;
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	
	FileInputAdapter(String in) {
		super();
	}
	
	FileInputAdapter(Configuration config, QueueManager queue) {
		setupConfig(config);
		connectToQueueManager(queue);
	}
	
	/** @param fileName full name of an existing, readable file */
	Path getPath(String fileName) {
	    return Paths.get(fileName);
	}
	
	/** Method for processing log file line by line  */
	private void processLineByLine() throws IOException {
		Scanner scanner =  new Scanner(getPath(logFile), ENCODING.name());
		while (scanner.hasNextLine()){
			parseEvent(scanner.nextLine());
		}
		scanner.close();
	}
	
	/** Method for parsing each line's content
	 *  @param log - scanned line from log file */
	private void parseEvent(String log){
		Scanner scanner = new Scanner(log);
		scanner.useDelimiter(" ");
		if (scanner.hasNext()){
			String date = scanner.next();
			System.out.println(timeFormat(date));
			Timestamp ts = Timestamp.valueOf(timeFormat(date));
			System.out.println("Timestamp: " +ts);
			
			String level = scanner.next();
			System.out.println(level);
			
			String detail = new String();
			while (scanner.hasNext()) {
				detail = detail.concat(" " +scanner.next());
			}
			System.out.println("Detail: " +detail.trim());
		}
		else {
			System.out.println("Empty or invalid line. Unable to process.");
	    }
		scanner.close();
	}
	
	/** Method formating log date
	 *  @param in - date to format */
	private String timeFormat (String in) {
		return in.replace("(", "").replace(")","").replace("T", " ");
	}
	
	String getLog() {
		return null;
	}
	
//	Event parseEvent(String log) {
//		return null;
//	}
	
	void addEvent(Event log) {
		System.out.println("There an event will be added - test");
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
		FileInputAdapter f = new FileInputAdapter(logFile);
		f.processLineByLine();
	}
}