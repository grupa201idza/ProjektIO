package idz.a.core;

import java.sql.Timestamp;

/**
 * 
 * 
 */

/**
 * @author Bartosz Domin 
 *  Przechowuje informacje o czasie, szczegolach, 
 *  rodzaju loga. 
 */
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
	 *  Przechowuje typ loga:
	 *   informacje,ostrzezenie, blad, naruszenie. 
	 */
	public static class Enum {
		public enum LogLevel {
			INFO, WARNING, ERROR, SEVERE
		};
	}
/**
 *Metoda zwracajaca znacznik czasowy
 * @return znacznik czasowy
 */
	public Timestamp getTimestamp() {
		return timestamp;
	}
/**
 *  ustawia znacznik czasowy
 * @param timestamp znacznik czasowy
 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
/**
 * Zwraca lancuch zawierajacy szczegoly
 * @return szczegoly
 */
	public String getDetails() {
		return details;
	}
/**
 * GEneracja szczegolow
 * @param details szczegoly
 */
	public void setDetails(String details) {
		this.details = details;
	}
/**
 * Parametr zwracajacy poziom loga
 * @return poziom loga
 */
	public Enum.LogLevel getLoglevel() {
		return loglevel;
	}
/**
 * Generuje poziom loga
 * @param loglevel poziom loga
 */
	public void setLoglevel(Enum.LogLevel loglevel) {
		this.loglevel = loglevel;
	}
}