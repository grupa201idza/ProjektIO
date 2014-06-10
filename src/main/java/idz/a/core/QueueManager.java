package idz.a.core;

import java.util.*;

import idz.a.core.AppCore;
import idz.a.output.OutputAdapter;

/**
 * @author Bartosz Domin
 * 
 */


/** 
 * Tworzy liste w formie tablicy o okreœlonym rozmiarze, domyœnie tablica ma rozmiar zero
 * Posiada wskaŸnik na adapter wyjœciowy.
 *  */
public class QueueManager {

	private int batchSize = 0;
	private static List<Event> queue = new ArrayList<Event>();

	OutputAdapter output;
	
	/** 
	 *  Pod³¹cza adapter wyjœciowy
	 *  */
	public QueueManager() {
		connectOutputAdapter();
	}
	
	/** 
	 * Pod³¹cza do wyjœcia AppCore
	 *  */
	public void connectOutputAdapter() {
		output = AppCore.out;
	}
	/** 
	 * Akceptuje wydarzenie i informacjê o w formie zmiennej boolean o
	 *  powodzeniu lub pora¿ce
	 *  */
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
