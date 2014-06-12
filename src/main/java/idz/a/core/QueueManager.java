package idz.a.core;

import java.util.*;

import idz.a.core.AppCore;
import idz.a.output.OutputAdapter;

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
 * 	
 *  Podlacza adapter wyjsciowy.
 */
	public QueueManager() {
		connectOutputAdapter();
	}
	
		public void setBatchSize(int size){
		batchSize=size;
	}
	
		/** 
		 * Podlacza do wyjscia AppCore.
		 *  */
	public void connectOutputAdapter() {
		output = AppCore.out;
	}
	
	/** 
	 * Akceptuje wydarzenie i informacj� o w formie zmiennej boolean o
	 *  powodzeniu lub poraｿce.
	 *  @return powodzenie lub porazka.
	 *  */
	public boolean acceptEvent(Event event) {
		if (currentSize() < batchSize) {
			queue.add(event);
			return true;
		} else
			return false;
	}
	/**
	 * Wysyｳa wydarzenie i zwraca informacje o powodzeniu.
	 * @return wartocs boolean czy sie udalo.
	 */
	public boolean sendEvents() {
		if (output.storeEvents(queue)) {
			removeEvents();
			return true;
		} else
			return false;
	}
	/**
	 * Usuwa wydarzenie.
	 */
	private void removeEvents() {
		queue.clear();
	}
	/**
	 * 
	 * @return rozmiar kolejki.
	 */
	public int currentSize() {
		return queue.size();
	}
}
