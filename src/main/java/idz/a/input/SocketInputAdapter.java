package idz.a.input;

import idz.a.core.Configuration;
import idz.a.core.Event;
import idz.a.core.QueueManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *Klasa  SocketInputAdapter
 *
 * @author Lukasz Kot
 */
public class SocketInputAdapter implements InputAdapter {
		private Configuration config;
		private QueueManager queue;
		private Socket socket = null;
		private int port;
		private ServerSocket serverSocket = null;
		
		/**
		 * 
		 * Inicjalizuje nowy obiekt SocketInputAdapter
		 */
		public SocketInputAdapter() {
			super();
		}
		/**
		 * 
		 * Metoda analizuje zawartoœæ linii i dodaje zdarzenie do kolejki.
		 * 
		 * @param log -przeczytany z loga.
		 * @return zmienne typu boolean zwracajca prawde 
         *     jezeli dodano do kolejki lub fa³sz w przypatku niepowodzenia
		 */
	private boolean parseEvent(final String log) {
		boolean readEvent = false;
		String tDate;
		String tTime;
		String tLevel;
		String tDetails = null;
		Timestamp timestamp = null;
		Event.Enum.LogLevel logLevel = null;
		Event event = null;
		final int logLength = 32;
		final int tDateSubsSec = 11;
		final int tTimeSubsFir = 12;
		final int tTimeSubsSec = 25;
		if (log.length() >= logLength) {
			tDate = log.substring(1, tDateSubsSec);
			tTime = log.substring(tTimeSubsFir, tTimeSubsSec);
			tDate = tDate + " " + tTime;
			DateFormat formatter = new
SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				Date parsedDate = formatter.parse(tDate);
				timestamp = new Timestamp(parsedDate.getTime());
			} catch (ParseException e) {
				return false;
			}
			final int tLevelSubsFir = 27;
			final int tLevelSubsSec = 28;
			tLevel = log.substring(tLevelSubsFir, tLevelSubsSec);
			int positionDetails = 0;
			final int posDetInf = 32;
			final int posDetWar = 35;
			final int posDetErr = 33;
			final int posDetSev = 34;
			switch (tLevel) {
			case "I":
				logLevel = Event.Enum.LogLevel.INFO;
				positionDetails = posDetInf;
				break;
			case "W":
				logLevel = Event.Enum.LogLevel.WARNING;
				positionDetails = posDetWar;
				break;
			case "E":
				logLevel = Event.Enum.LogLevel.ERROR;
				positionDetails = posDetErr;
				break;
			case "S":
				logLevel = Event.Enum.LogLevel.SEVERE;
				positionDetails = posDetSev;
				break;
			default: System.out.println("Error");
				break;
			}
			tDetails = log.substring(positionDetails);
			event = new Event(timestamp, tDetails, logLevel);
			if (queue.acceptEvent(event)) { readEvent = true; } else { readEvent = false; }
		}
		return readEvent;
	}
	
	/**
	 * Method awaits for the connection and
	 * creates socket if connection is made.
	 * 
	 * @return true if connected to source
	 * 			or false if not connected to source.
	 */
	public final boolean connectToSource() {
		try {
			serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
		} catch (IOException e) {
			return false;
		}
		if (socket != null)
			return true;
		else
			return false;
	}
	
	/**
	 * Method reads line from socket and invokes method parseEvent.
	 * 
	 * @return true if method parseEvent returns true;
	 * 			in any other case returns false.
	 */
	public final boolean readLog() {
		boolean readEvent = false;
		BufferedReader brinp = null;
		try {
			brinp = new BufferedReader(
new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			readEvent = false;
		}
		final int sleepTime = 200;
		String line = null;
		try {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				readEvent = false;
			}
			line = brinp.readLine();
			if (line != null) {
				if (parseEvent(line))
					readEvent = true;
				else
					readEvent = false;
			}
}
		catch (IOException e) {
			readEvent = false;
		}
		return readEvent;
	}

	/**
	 * Method closes Socket and ServerSocket objects.
	 */
	public final void closeSocket() {
		try {
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method setups Configuration object.
	 * 
	 * @param config - Configuration object.
	 */
	public final void setupConfig(final Configuration config) {
		this.config = config;
		port = config.getInputSocket();

	}

	/**
	 * Method setups QueueManager object.
	 * 
	 * @param queue - QueueManager object
	 */
	public final void connectToQueueManager(final QueueManager queue) {
		this.queue = queue;
	}
}
