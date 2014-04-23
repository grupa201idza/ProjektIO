package idz.a.input;


public class SocketInputAdapter implements InputAdapter, Runnable{

	Configuration config;
	QueueManager queue;
	
	public void run() {
			
	}

	public void setupConfig(Configuration config) {
		// TODO Auto-generated method stub
		this.config = config;
	}

	public void connectToQueueManager(QueueManager queue) {
		// TODO Auto-generated method stub
		this.queue = queue;
		
	}

}
