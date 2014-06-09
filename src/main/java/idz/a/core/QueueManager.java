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
	 * Tworzy liste w formie tablicy o okreœlonym rozmiarze, domyœnie tablica ma rozmiar zero
	 * Posiada wskaŸnik na adapter wyjœciowy.
	 *  */
	}

public class QueueManager {

	private int batchSize = 0;
	private static List<Event> queue = new ArrayList<Event>();

	OutputAdapter output;
	
	public class dokumentacjaQM{

		/** 
		 *@autor Kamil Klarecki 
		 *  Pod³¹cza adapter wyjœciowy
		 * 
		 *  */
		}
	public QueueManager() {
		connectOutputAdapter();
	}

	public class dokumentacjacQM{

		/** 
		 *@autor Kamil Klarecki 
		 * Pod³¹cza do wyjœcia AppCore
		 * 
		 *  */
		}
	public void connectOutputAdapter() {
		output = AppCore.out;
	}
	
	public class dokumentacjaaE{

		/** 
		 *@autor Kamil Klarecki 
		 * Akceptuje wydarzenie i informacjê o w formie zmiennej boolean o
		 *  powodzeniu lub pora¿ce
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
		 *  Próbuje wys³aæ wydarzenie oraz zwraca w formie boolean informacje o 
		 *  powodzeniu lub pora¿ce
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
		 *  Przechowuje informacje o czasie, szczegó³ach
		 * 
		 *  */
		}
	private void removeEvents() {
		queue.remove(0);
	}
	
	public class dokumentacjaCS{

		/** 
		 *@autor Kamil Klarecki 
		 *  Przechowuje informacje o czasie, szczegó³ach
		 * 
		 *  */
		}
	public int currentSize() {
		return queue.size();
	}
}
