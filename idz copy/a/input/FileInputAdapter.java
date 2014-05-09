package idz.a.input;

import java.io.BufferedReader;
import java.io.File;

import idz.a.core.Configuration;
import idz.a.core.QueueManager;


public class FileInputAdapter implements InputAdapter {

	Configuration config;
	QueueManager queue;
	File inputFile;
	BufferedReader in;
	
	FileInputAdapter() {
		setupConfig(config);
		connectToQueueManager(queue);
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
