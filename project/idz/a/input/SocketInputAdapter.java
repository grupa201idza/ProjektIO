package idz.a.input;

import javax.security.auth.login.Configuration;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketInputAdapter implements InputAdapter, Runnable{

	Thread thread;
	Configuration config;
	QueueManager queue;
	
	public SocketInputAdapter(){
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
			ServerSocket serverSocket = null;
			Socket socket = null;
			
			try {
				serverSocket = new ServerSocket(config.port);
			} catch (IOException e) {
				System.out.println("B³¹d przy tworzeniu gniazda serwerowego");
				System.exit(-1);
			}
			
			
			while (true){
				try {
					socket = serverSocket.accept();
				} catch (IOException e) {
					System.out.println("B³¹d wejœcia-wyjœcia");
				}
				new Thread(new SocketInputThread(socket, queue)).start();
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
