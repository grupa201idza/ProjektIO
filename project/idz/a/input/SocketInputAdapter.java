package idz.a.input;

import idz.a.core.Configuration;
import idz.a.core.Event;
import idz.a.core.QueueManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketInputAdapter implements InputAdapter {
		Configuration config;
		QueueManager queue;
		Socket socket;
		public SocketInputAdapter(Socket socket, QueueManager queue) {
		this.socket = socket;
		this.queue = queue;
	}
	private void parseEvent(final String log) {
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
				return;
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
			if (queue.acceptEvent(event)) { System.out.println("Added to queue."); } else { System.out.println("Not added to queue."); }
		}
	}
	final void start() {
		BufferedReader brinp = null;
		try {
			brinp = new BufferedReader(
new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println(
"Blad przy tworzeniu strumienia " + e);
		}
		String line = null;
		while (true) {
		try {
			line = brinp.readLine();
			if (line != null) {
				parseEvent(line);
			}
}
		catch (IOException e) {
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
