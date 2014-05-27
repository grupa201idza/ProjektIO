package idz.a.input;

import javax.security.auth.login.Configuration;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketInputAdapter implements InputAdapter, Runnable{

	Thread thread;
	Configuration config;
	QueueManager queue;
	boolean running = false;
	
	public SocketInputAdapter(){
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
			ServerSocket serverSocket = null;
			Socket socket = null;
			if (!running){			
			try {
					serverSocket = new ServerSocket(config.inputSocket);
			} catch (IOException e) {
				System.out.println("Blad przy tworzeniu gniazda serwerowego");
				System.exit(-1);
			}
			
			
			while (true){
				try {
					socket = serverSocket.accept();
					running = true;
				} catch (IOException e) {
					running = false;
					System.out.println("Blad wejscia-wyjscia");
				}
				new Thread(new SocketInputThread(socket, queue)).start();
			}
			}
			
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
