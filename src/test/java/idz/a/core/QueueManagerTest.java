package idz.a.core;

import idz.a.core.QueueManager;

import idz.a.core.Configuration;

import java.lang.String;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueueManagerTest {
	
	static String configPath = "src/main/java/idz/a/core/Config.txt";
	
	Configuration conf = new Configuration(configPath);
	
	QueueManager manager = new QueueManager();
	
	private String exampleString = "Example";
	
	
	@Test
	
	/**
	 * Test metody acceptEvent klasy QuereManager
	 * Test 1:
	 * Przypadek, w którym aktualna ilosc batchy jest mniejsza od ilosci maksymalnej.
	 * Oczekiwany rezultat:
	 * Zwrócona wartosc typu boolean: true 
	 */
	
	public void test() {
		
		/**
		 * Ustawianie kolejki wed³ug pliku konfiguracyjnego
		 */
		
	manager.setBatchSize(conf.getBatchSize());
	
	/**
	 * Tworzenie 7 przyk³adowych obiektow klasy Event
	 */
	
	int numberOfEvents = 7;
	
	Event[] sample = new Event[numberOfEvents];
	
	for (int counter = 0; counter < numberOfEvents; counter++) {
		
		sample[counter] = new Event( null, exampleString, null);
		
	}
		for ( int t1 = 0; t1 < numberOfEvents-1; t1++) {
		
			manager.acceptEvent(sample[t1]);
			
		}
			
		/**
		 * Kluczowy moment testu:
		 * Sprawdzanie odpowiedzi metody acceptEvent przez kolejne 10 powtorzen,
		 * gdzie oczekiwana odpowiedzia jest wartosc true
		 */
		
		assertEquals( "Accept Event", true, manager.acceptEvent(sample[numberOfEvents-1]));
		
	}

}