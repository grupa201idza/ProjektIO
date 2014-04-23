package idz.a.core;

import java.sql.Timestamp;

public class Event {
	
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

public static void main(String[] args) {
}

}
