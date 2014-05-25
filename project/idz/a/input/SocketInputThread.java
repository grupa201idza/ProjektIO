package idz.a.input;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.sql.Timestamp;


public class SocketInputThread implements Runnable{
	
	Event event;
	QueueManager queue;
	protected Socket socket;
	
	public SocketInputThread(Socket socket, QueueManager queue)
	{
		this.socket = socket;
		this.queue = queue;
	}
	
	private void parseEvent(String log){
		
	}
	
	private Timestamp getTimeStamp()
    {
         return(new Timestamp((new Date()).getTime()));
    }
	
	public void run(){
		
		// deklaracja zmiennych
		BufferedReader brinp = null;
		DataOutputStream out = null;
		String threadName = Thread.currentThread().getName();
		
		// inicjalizacja strumieni
		try {
			brinp = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println(threadName + "| Blad przy tworzeniu strumieni " + e);
		}
		String line = null;
		
		while(true){
		try{
			line = brinp.readLine();
			if (line != null){
				parseEvent(line);
			}
		}
		catch (IOException e){
			System.out.println(threadName + "| Blad wejscia-wyjscia");
			return;
		}
		}
	}
}
