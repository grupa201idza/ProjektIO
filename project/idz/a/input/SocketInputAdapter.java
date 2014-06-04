package idz.a.input;

import javax.security.auth.login.Configuration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketInputAdapter implements InputAdapter{

	Configuration config;
	QueueManager queue;
	Socket socket;
	
	public SocketInputAdapter(Socket socket, QueueManager queue)
	{
		this.socket = socket;
		this.queue = queue;
	}
	
	private void parseEvent(String log){
		
		String tDate;
		String tTime;
		String tLevel;
		String tDetails = null;
		Timestamp timestamp = null;
		Event.Enum.LogLevel logLevel = null;
		Event event = null;
		
		if (log.length()>=32)
		{
			tDate = log.substring(1, 11);
			tTime = log.substring(12, 25);
			tDate = tDate + " " + tTime;
			
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				Date parsedDate = formatter.parse(tDate);
				timestamp = new Timestamp(parsedDate.getTime());
			} catch (ParseException e) {
				return;
			}
			
			tLevel = log.substring(27, 28);
			
			switch (tLevel) {
			case "I": 
				logLevel = Event.Enum.LogLevel.INFO;
				tDetails = log.substring(32);
				break;
			case "W": 
				logLevel = Event.Enum.LogLevel.WARNING;
				tDetails = log.substring(35);
				break;
			case "E": 
				logLevel = Event.Enum.LogLevel.ERROR;
				tDetails = log.substring(33);
				break;
			case "S": 
				logLevel = Event.Enum.LogLevel.SEVERE;
				tDetails = log.substring(34);
				break;
			}
			event = new Event(timestamp, tDetails, logLevel);
			if (queue.acceptEvent(event))
				System.out.println("Added to queue.");
			else
				System.out.println("Not added to queue.");
		}
	}
	
	public void start() {
		
		BufferedReader brinp = null;
		
		try {
			brinp = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Blad przy tworzeniu strumienia " + e);
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
			System.out.println("Blad wejscia-wyjscia");
			return;
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
