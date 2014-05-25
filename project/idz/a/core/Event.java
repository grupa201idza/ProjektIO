package idz.a.core;

import java.sql.Timestamp;

public class Event {

	public Event(Timestamp ts, String det, Enum.LogLevel ll) {
		timestamp = ts;
		details = det;
		loglevel = ll;
	}

	private Timestamp timestamp;
	private String details;
	private Enum.LogLevel loglevel;

	public static class Enum {
		private enum LogLevel {
			INFO, WARNING, ERROR, SEVERE
		};
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Enum.LogLevel getLoglevel() {
		return loglevel;
	}

	public void setLoglevel(Enum.LogLevel loglevel) {
		this.loglevel = loglevel;
	}
}