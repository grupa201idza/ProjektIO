package idz.a.core;

import java.util.*;

public class QueueManager {

	private int batchSize=0;
	private static List<Event> queue = new ArrayList<Event>();
	
	public QueueManager(){
		
	}
	
	public boolean acceptEvent(Event event){
		return true;
	}
	
	 public boolean sendEvents(){
		 return true;
	 }
	
	 void removeEvents(){
		 queue.remove(0);
	 }
	 
	 public int currentSize(){
		 return batchSize;
	 }
}
