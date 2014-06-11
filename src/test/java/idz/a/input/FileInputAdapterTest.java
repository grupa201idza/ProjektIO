package idz.a.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import idz.a.core.Event;

import org.junit.Test;
/**
 * 
 */
public class FileInputAdapterTest {

	private FileInputAdapter fia = new FileInputAdapter(44);
	private final Event event1 = fia.parseEvent(getLog1());
	private final Event event2 = fia.parseEvent(getLog2());

	@Test
	/**
	 * Klasa testowa testujaca FileInputAdapter
	 */
	public void typeTest1() {
		assertTrue("Error - object is not Event",
				fia.parseEvent(getLog1()) instanceof Event);
	}

	@Test
	/**
	 * 
	 */
	public void timestampTest1() {
		assertTrue("Error - object is not Timestamp",
				event1.getTimestamp() instanceof Timestamp);
	}

	@Test
	/**
	 * 
	 */
	public void loglevelTest1() {
		assertTrue("Error - object is not Loglevel",
				event1.getLoglevel()
				instanceof	Event.Enum.LogLevel);
	}

	@Test
	/**
	 * 
	 */
	public void detailsTest1() {
		assertTrue("Error - object is not String",
				event1.getDetails() instanceof String);
	}

	@Test
	/**
	 * 
	 */
	public void timestampValue1() {
		assertEquals("Values are equal",
				getTimestamp1(), event1.getTimestamp());
	}

	@Test
	/**
	 * 
	 */
	public void loglevelValue1() {
		assertEquals("Values are equal",
				getLoglevel1(), event1.getLoglevel());
	}

	@Test
	/**
	 * 
	 */
	public void detailsValue1() {
		assertEquals("Values are equal",
				getDetails1(), event1.getDetails());
	}

	@Test
	/**
	 * 
	 */
	public void typeTest2() {
		assertTrue("Error - object is not Event",
				fia.parseEvent(getLog2()) instanceof Event);
	}

	@Test
	/**
	 * 
	 */
	public void timestampTest2() {
		assertTrue("Error - object is not Timestamp",
				event2.getTimestamp() instanceof Timestamp);
	}

	@Test
	/**
	 * 
	 */
	public void loglevelTest2() {
		assertTrue("Error - object is not Loglevel",
				event2.getLoglevel()
				instanceof Event.Enum.LogLevel);
	}

	@Test
	/**
	 * 
	 */
	public void detailsTest2() {
		assertTrue("Error - object is not String",
				event2.getDetails() instanceof String);
	}

	@Test
	/**
	 * 
	 */
	public void timestampValue2() {
		assertEquals("Values are equal",
				getTimestamp2(), event2.getTimestamp());
	}

	@Test
	/**
	 * 
	 */
	public void loglevelValue2() {
		assertEquals("Values are equal",
				getLoglevel2(), event2.getLoglevel());
	}

	@Test
	/**
	 * 
	 */
	public void detailsValue2() {
		assertEquals("Values are equal",
				getDetails2(), event2.getDetails());
	}
/**
 * 
 */
	private String getLog1() {
		return "(2014-05-22T07:25:25.0912) INFO [org.jboss."
				+ "stdio.AbstractLoggingWriter.write("
				+ "AbstractLoggingWriter.java:71)] (default"
				+ " task-2) cwmp NS =urn:dslforum-org:cwmp-1-1";
	}
/**
 * @return
 */
	private Timestamp getTimestamp1() {
		return Timestamp.valueOf("2014-05-22 07:25:25.0912");
	}

	private Event.Enum.LogLevel getLoglevel1() {
		return Event.Enum.LogLevel.INFO;
	}

	private String getDetails1() {
		return "[org.jboss.stdio.AbstractLoggingWriter."
				+ "write(AbstractLoggingWriter.java:71)]"
				+ " (default task-2) cwmp NS"
				+ " =urn:dslforum-org:cwmp-1-1";
	}

	private String getLog2() {
		return "(2014-05-22T08:06:13.0703) WARNING [is.iWeb."
				+ "sentinel.logic.model.utils.Version.Set("
				+ "Version.java:30)] (Thread-381128) Invalid"
				+ " version string: SV1.0 Non numeric"
				+ " elements assumed to be 0";
	}

	private Timestamp getTimestamp2() {
		return Timestamp.valueOf("2014-05-22 08:06:13.0703");
	}

	private Event.Enum.LogLevel getLoglevel2() {
		return Event.Enum.LogLevel.WARNING;
	}

	private String getDetails2() {
		return "[is.iWeb.sentinel.logic.model.utils.Version."
				+ "Set(Version.java:30)] (Thread-381128)"
				+ " Invalid version string: SV1.0 Non"
				+ " numeric elements assumed to be 0";
	}
}
