package idz.a.core;

import java.util.*;

import idz.a.core.AppCore;
import idz.a.output.OutputAdapter;

/**
 * @author Bartosz Domin
 * 
 */

public class QueueManager {

	private int batchSize = 0;
	private static List<Event> queue = new ArrayList<Event>();

	OutputAdapter output;

	public QueueManager() {
		connectOutputAdapter();
	}

	public void connectOutputAdapter() {
		output = AppCore.out;
	}

	public boolean acceptEvent(Event event) {
		if (currentSize() < batchSize) {
			queue.add(event);
			return true;
		} else
			return false;
	}

	public boolean sendEvents() {
		if (output.storeEvents(queue)) {
			removeEvents();
			return true;
		} else
			return false;
	}

	private void removeEvents() {
		queue.remove(0);
	}

	public int currentSize() {
		return queue.size();
	}
}
