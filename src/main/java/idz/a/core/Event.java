package idz.a.core;

import java.sql.Timestamp;

/**
 * @author Bartosz Domin
 * 
 */
}

public class dokumentacjaEvent{

	/** 
	 *@autor Kamil Klarecki 
	 *  Przechowuje informacje o czasie, szczegó³ach, 
	 *  rodzaju loga
	 *  
	 *  */

}


public class Event {

	public Event(Timestamp ts, String det, Enum.LogLevel ll) {
		timestamp = ts;
		details = det;
		loglevel = ll;
	}

	private Timestamp timestamp;
	private String details;
	private Enum.LogLevel loglevel;
	
	public class dokumentacjaEnum{

		/** 
		 *@autor Kamil Klarecki 
		 *  przechowuje typ loga:
		 *   informacjê,ostrze¿enie, b³¹d, naruszenie 
		 *  */
	
	public static class Enum {
		public enum LogLevel {
			INFO, WARNING, ERROR, SEVERE
		};
	}

	public class dokumentacjaTimestamp getTimestamp(){

		/** 
		 *@autor Kamil Klarecki 
		 *  
		 *  */
		
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	public class dokumentacjasetTimestamp{

		/** 
		 *@autor Kamil Klarecki 
		 *  Zwraca znacznik czasowy
		 *  
		 *  
		 *  */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public class dokumentacjasetTimestamp{

	/** 
	 *@autor Kamil Klarecki 
	 *  Przypisuje znacznik czasowy
	 *  
	 *  
	 *  */
	public String getDetails() {
		return details;
	}

	/** 
	 *@autor Kamil Klarecki 
	 *  Zswraca ³añcuch zawieraj¹cy szczegó³y loga

	 *  */
	public void setDetails(String details) {
		this.details = details;
	}

	/** 
	 *@autor Kamil Klarecki 
	 *  Zwracarodzaj loga
	 *  
	 *  */
	public Enum.LogLevel getLoglevel() {
		return loglevel;
	}


	
	public class dokumentacjsetLoglevel{

		/** 
		 *@autor Kamil Klarecki 
		 *  Zwraca poziom loga
		 *  
		 *  */
	public void setLoglevel(Enum.LogLevel loglevel) {
		this.loglevel = loglevel;
	}
}