package idz.a.core;

import java.util.*;

import idz.a.core.AppCore;
import idz.a.output.OutputAdapter;

/**
 * @author Bartosz Domin
 * 
 */


/** 
 * Tworzy liste w formie tablicy o okreslonymonym rozmiarze, domysnie tablica ma rozmiar zero.
 * Posiada wskaznik na adapter wysciowy.
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
	 * Wysyla wydarzenie i zwraca informacje o powodzeniu.
	 * @return wartoss boolean czy sie udalo.
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
	 * @return Zwraca rozmiar kolejki.
	 */
	public int currentSize() {
		return queue.size();
	}
}
