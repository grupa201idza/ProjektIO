package idz.a.core;

import java.util.*;

import idz.a.core.AppCore;
import idz.a.output.OutputAdapter;

/**
 * @author Bartosz Domin
 * 
 */


/** 
 * Tworzy liste w formie tablicy o okre�lonym rozmiarze, domy�nie tablica ma rozmiar zero
 * Posiada wska�nik na adapter wyj�ciowy.
 *  */
public class QueueManager {

	private int batchSize = 0;
	private static List<Event> queue = new ArrayList<Event>();

	OutputAdapter output;
	
	/** 
	 *  Pod��cza adapter wyj�ciowy
	 *  */
	public QueueManager() {
		connectOutputAdapter();
	}
	
	/** 
	 * Pod��cza do wyj�cia AppCore
	 *  */
	public void connectOutputAdapter() {
		output = AppCore.out;
	}
	/** 
	 * Akceptuje wydarzenie i informacj� o w formie zmiennej boolean o
	 *  powodzeniu lub pora�ce
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
