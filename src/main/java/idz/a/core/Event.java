package idz.a.core;

import java.sql.Timestamp;

/**
 * @author Bartosz Domin
 * 
 */

/** 
 *  Przechowuje informacje o czasie, szczegó³ach, 
 *  rodzaju loga.
 *  */
public class Event {

	public Event(Timestamp ts, String det, Enum.LogLevel ll) {
		timestamp = ts;
		details = det;
		loglevel = ll;
	}

	private Timestamp timestamp;
	private String details;
	private Enum.LogLevel loglevel;
	
	/**  
	 *  przechowuje typ loga:
	 *   informacjê,ostrze¿enie, b³¹d, naruszenie.
	 *  */
	public static class Enum {
		public enum LogLevel {
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