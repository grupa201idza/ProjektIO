package main.java.idz.a.core;

import java.util.*;

import main.java.idz.a.core.AppCore;
import main.java.idz.a.output.OutputAdapter;

/**
 * @author Bartosz Domin
 * 
 */


/** 
 * Tworzy liste w formie tablicy o okre徑onym rozmiarze, domy從ie tablica ma rozmiar zero
 * Posiada wska殤ik na adapter wyj彡iowy.
 *  */
public class QueueManager {

	private int batchSize = 0;
	private static List<Event> queue = new ArrayList<Event>();

	OutputAdapter output;
	
	/** 
	 *  Podｳｹcza adapter wyj彡iowy
	 *  */
	public QueueManager() {
		connectOutputAdapter();
	}
	
	/** 
	 * Podｳｹcza do wyj彡ia AppCore
	 *  */
	public void connectOutputAdapter() {
		output = AppCore.out;
	}
	/** 
	 * Akceptuje wydarzenie i informacj� o w formie zmiennej boolean o
	 *  powodzeniu lub poraｿce
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
