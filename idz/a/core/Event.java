package idz.a.core;

import java.sql.Timestamp;

public class Event {
	
	public Event(Timestamp ts, String det, LogLevel ll){
		timestamp=ts;
		details=det;
		loglevel=ll;
	}
	
private Timestamp timestamp;
private String details;
private LogLevel loglevel;
private enum LogLevel {
	INFO,
	WARNING,
	ERROR,
	SEVERE
};

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

public LogLevel getLoglevel() {
	return loglevel;
}

public void setLoglevel(LogLevel loglevel) {
	this.loglevel = loglevel;
}

/*
 * Function for manual Event Objects tests and listings for Output Adapter
 * tests, may be used in other occasions The reason of creation is
 * restrictec visibility of enum LogLevel thus problemswith creating new
 * LogLevels with no example Events with those states
 */

public void setLogLevel(String level) throws ForeseenException {
	switch (level) {
	case "INFO":
		this.loglevel = LogLevel.INFO;
		break;
	case "WARNING":
		this.loglevel = LogLevel.WARNING;
		break;
	case "ERROR":
		this.loglevel = LogLevel.ERROR;
		break;
	case "SEVERE":
		this.loglevel = LogLevel.SEVERE;
		break;
	default:
		throw new ForeseenException(
				"Called logLevel not defined in setLogLevel by integer");

	}
}
}