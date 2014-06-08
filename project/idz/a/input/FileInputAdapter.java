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
 * FileInputAdapter class.
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
	 * Initializes new FileInputAdapter object.
	 */
	public FileInputAdapter() {
		logPath = Configuration.getInputFilePath();
		isConnected = connectToSource();
	}

	/** FileInputAdapter constructor for testing
	 * 		purposes.
	 * @param test garbage parameter
	 */
	public FileInputAdapter(final int test) {
		super();
	}

	/**
	 * Returns Path from given String path.
	 *
	 * @param fileName
	 *            full name of an existing, readable file
	 * @return resulting Path from given String
	 */
	private Path getPath(final String fileName) {
		return Paths.get(fileName);
	}

	/**
	 * Checks if given log file path exists.
	 *
	 * @return true if connected to source; false if not connected to source
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
	 * Reads log file line by line.
	 */
	public final void readLog() {
//		Scanner scanner;
		if (isConnected) {
			if (scanner.hasNextLine()
					&& queue.currentSize()
					< Configuration.getBatchSize()) {
				event = parseEvent(scanner.nextLine());
				if (!event.equals(null)
						&& !event.getTimestamp().equals(
								null)
						&& !event.getDetails().equals(
								null)
						&& !event.getLoglevel().equals(
								null)) {
					queue.acceptEvent(event);
				} else {
					System.out.println(
							"Unprocessable line");
				}
			} else {
				closeScanner(scanner);
			}
		} else {
			System.err.println("Log source not found!");
		}
	}

	/**
	 * Parses line's content to Event.
	 *
	 * @param log
	 *            scanned line from log file
	 * @return parsed Event object
	 * or null if meets empty or invalid line in log file
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
	 * Formats given String log date.
	 *
	 * @param logDate
	 *            String date to format
	 * @return date in format compatible with Timestamp
	 */
	private String dateReplace(final String logDate) {
		return logDate.replaceAll("[(T)]", " ");
	}

	/**
	 * Trims formated date.
	 *
	 * @param logDate
	 *            date to trim
	 * @return trimmed log date
	 */
	private String dateTrim(final String logDate) {
		return logDate.trim();
	}

	/**
	 * Closes Scanner object.
	 *
	 * @param scan
	 * 			  Scanner object to close
	 *
	 */
	private void closeScanner(final Scanner scan) {
		scan.close();
	}

	/**
	 * Setups Configuration object.
	 *
	 * @param configCon
	 *            Configuration object
	 */
	public final void setupConfig(final Configuration configCon) {
		this.config = configCon;
	}

	/**
	 * Setups QueueManager object.
	 *
	 * @param queueMan
	 *            QueueManager object
	 */
	public final void connectToQueueManager(final QueueManager queueMan) {
		this.queue = queueMan;
	}

}