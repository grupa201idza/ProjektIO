package idz.a.input;

import idz.a.core.Configuration;
import idz.a.core.Event;
import idz.a.core.QueueManager;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Scanner;

/**
 *Klasa zawierajaca wejscie z pliku
 *
 * @author Przemyslaw Springer
 */
public class FileInputAdapter implements InputAdapter {

	private Configuration config;
	private QueueManager queue;
	private String logPath;
	private static final Charset ENCODING = StandardCharsets.UTF_8;
	private static final int connectionAmount = 5;
	private Event event;
	private Scanner scanner;
	private boolean isConnected;

/**
 * Konstruktor ktory przyjmuje sciezke pliku i sprawdza czy jest polaczenie
 */
	public FileInputAdapter() {
		super();
	}

	/**
	 * Zwraca sciezke z String path.
	 *
	 * @param fileName -  pelna nazwa 
	 * @return zwraca otrzymany wynik
	 */
	private Path getPath(final String fileName) {
		return Paths.get(fileName);
	}

	/**
	 *Sprawdza, czy istnieje podana œcie¿ka w  pliku loga. 
	 * @return true je¿eli pod³¹czono do Ÿród³a lub false jeœli nie pod³aczono.
	 */
	private boolean connectToSource() {
		int connAttempts = 0;
		while (connAttempts < connectionAmount) {
			try {
				scanner = new Scanner(getPath(logPath),
						ENCODING.name());
				break;
			} catch (IOException e) {
				System.out.println("Attempting to connect"
						+ " to source");
				connAttempts++;
				continue;
			}
		}
		return connAttempts < connectionAmount;
	}

	/**
	 * Czyta liniam plik logu.
	 */
	public boolean readLog() {
		if (isConnected) {
			if (scanner.hasNextLine()
					&& queue.currentSize()
					< config.getBatchSize()) {
				event = parseEvent(scanner.nextLine());
				if (!(event==null)
						&& !event.getTimestamp().equals(
								null)
						&& !event.getDetails().equals(
								null)
						&& !event.getLoglevel().equals(
								null)) {
					queue.acceptEvent(event);
					return true;
				} else {
					System.out.println(
							"Unprocessable line");
					return true;
				}
			}
		} else {
			System.err.println("Log source not found!");
		}
		return false;
	}

	/**

	 * Analizuje zawartoœæ lini do wydarzenia.
	 *
	 * @param log -  skanowana linia z pliku loga 
	 * @return analizowany obiekt Event 
     * lub niewazny, jeœli jest pusta lub nieprawid³ow¹ linia w pliku loga
	 */
	public final Event parseEvent(final String log) {
		final Scanner scan = new Scanner(log);
		scan.useDelimiter(" ");
		if (scan.hasNext()) {
			Event evt = new Event(null, null, null);
			try {
				final String date = scan.next();
				evt.setTimestamp(Timestamp
						.valueOf(dateTrim(dateReplace(
								date))));
			} catch (IllegalArgumentException e) {
				System.err.println("Wrong Timestamp");
				evt.setTimestamp(null);
			}

			try {
				final String level = scan.next();
				evt.setLoglevel(
						Event.Enum.LogLevel.valueOf(
								level));
			} catch (IllegalArgumentException e) {
				System.err.println("Wrong Loglevel");
				evt.setLoglevel(null);
			}

			try {
				String detail = new String();
				while (scan.hasNext()) {
					detail = detail.concat(
							" " + scan.next());
				}
				evt.setDetails(detail.trim());
			} catch (IllegalArgumentException e) {
				System.err.println("Wrong Detail");
				evt.setDetails(null);
			}
			closeScanner(scan);
			return evt;
		} else {
			System.out.println("Empty or invalid line."
					+ "Unable to process.");
			closeScanner(scan);
			return null;
		}
	}

	/**
	 * Formatuje otrzymanya date z logu
	 *
	 * @param logDate - data z logu do formatowania
	 * @return data w formacie zgodnym z Timestamp
	 */
	private String dateReplace(final String logDate) {
		return logDate.replaceAll("[(T)]", " ");
	}

	/**
	 *Obiekt formatujacy date
	 *
	 * @param logDate - data do sformatowania
	 * 
	 * @return - sformatowana datalogu
	 */
	private String dateTrim(final String logDate) {
		return logDate.trim();
	}

	/**
	 * Zamkniecie obiektu skanera.
	 *
	 * @param scan obiekt Scanner do zamkniêcia
	 *
	 */
	public void closeScanner(final Scanner scan) {
		scan.close();
	}
	
	/**
	 * Konfiguracja obiektu Configuration .
	 *
	 * @param configCon obiekt Configuration 
	 */
	public void setupConfig(final Configuration configCon) {
		config = configCon;
		logPath = config.getInputFilePath();
		isConnected = connectToSource();
	}

	/**
	 * Obiekt konfiguracji QueueManager.
	 *
	 * @param queueMan - obiekt QueueManager 
	 */
	public void connectToQueueManager(final QueueManager queueMan) {
		this.queue = queueMan;
	}

}