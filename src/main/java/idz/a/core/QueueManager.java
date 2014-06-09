package idz.a.core;

import java.util.*;

import idz.a.core.AppCore;
import idz.a.output.OutputAdapter;

/**
 * @author Bartosz Domin
 * 
 */
public class dokumentacjaQM{

	/** 
	 *@autor Kamil Klarecki 
	 * Tworzy liste w formie tablicy o okre�lonym rozmiarze, domy�nie tablica ma rozmiar zero
	 * Posiada wska�nik na adapter wyj�ciowy.
	 *  */
	}

public class QueueManager {

	private int batchSize = 0;
	private static List<Event> queue = new ArrayList<Event>();

	OutputAdapter output;
	
	public class dokumentacjaQM{

		/** 
		 *@autor Kamil Klarecki 
		 *  Pod��cza adapter wyj�ciowy
		 * 
		 *  */
		}
	public QueueManager() {
		connectOutputAdapter();
	}

	public class dokumentacjacQM{

		/** 
		 *@autor Kamil Klarecki 
		 * Pod��cza do wyj�cia AppCore
		 * 
		 *  */
		}
	public void connectOutputAdapter() {
		output = AppCore.out;
	}
	
	public class dokumentacjaaE{

		/** 
		 *@autor Kamil Klarecki 
		 * Akceptuje wydarzenie i informacj� o w formie zmiennej boolean o
		 *  powodzeniu lub pora�ce
		 * 
		 *  */
		}
	public boolean acceptEvent(Event event) {
		if (currentSize() < batchSize) {
			queue.add(event);
			return true;
		} else
			return false;
	}
	public class dokumentacjasetevents{

		/** 
		 *@autor Kamil Klarecki 
		 *  Pr�buje wys�a� wydarzenie oraz zwraca w formie boolean informacje o 
		 *  powodzeniu lub pora�ce
		 *  */
		}
	public boolean sendEvents() {
		if (output.storeEvents(queue)) {
			removeEvents();
			return true;
		} else
			return false;
	}
	public class dokumentacjarE{

		/** 
		 *@autor Kamil Klarecki 
		 *  Przechowuje informacje o czasie, szczeg�ach
		 * 
		 *  */
		}
	private void removeEvents() {
		queue.remove(0);
	}
	
	public class dokumentacjaCS{

		/** 
		 *@autor Kamil Klarecki 
		 *  Przechowuje informacje o czasie, szczeg�ach
		 * 
		 *  */
		}
	public int currentSize() {
		return queue.size();
	}
}
