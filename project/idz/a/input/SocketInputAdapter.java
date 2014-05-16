package idz.a.input;

import javax.security.auth.login.Configuration;


public class SocketInputAdapter implements InputAdapter, Runnable{

	Configuration config;
	QueueManager queue;
	
	public void run() {
			
	}

	public void setupConfig(Configuration config) {
		this.config = config;
	}

	public void connectToQueueManager(QueueManager queue) {
		this.queue = queue;
		
	}

	@Override
	public void setupConfig(Configuration config) {
		
	}

}
