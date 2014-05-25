package idz.a.input;

import java.io.BufferedReader;
import java.io.File;
import idz.a.core.Configuration;
import idz.a.core.Event;
import idz.a.core.QueueManager;


public class FileInputAdapter implements InputAdapter {

	Configuration config;
	QueueManager queue;
	File inputFile;
	BufferedReader in;
	
	FileInputAdapter(Configuration config, QueueManager queue) {
		setupConfig(config);
		connectToQueueManager(queue);
	}

	String getLog() {
		return null;
	}
	
	Event parseEvent(String log) {
		return null;
	}
	
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

}
