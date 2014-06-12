package idz.a.core;

import idz.a.core.QueueManager;
import idz.a.core.Configuration;
import idz.a.output.OutputAdapter;

import java.lang.String;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueueManagerTest {
	
	/**
	 * Utworzenie pliku konfiguracyjnego oraz Obiektu klasy QueueManager,
	 * ktory bedzie poddawany testom.
	 */
	
	static String configPath = "src/main/java/idz/a/core/Config.txt";
	
	Configuration conf = new Configuration(configPath);
	
	QueueManager manager = new QueueManager();
	
	private String exampleString = "Example details";
	
	/**
	 * 
	 * Klasa implementujaca "pusty" adapter wyjsciowy
	 * niezbedny do dzialania metody sendEvents
	 *
	 */
	
	private class OutputTest implements OutputAdapter {

		@Override
		public void setupConfig(Configuration config) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean storeEvents(List<Event> batch) {
			// TODO Auto-generated method stub
			return true;
		}
		
	}
	
	OutputTest OT = new OutputTest();
	
	@Test
	
	/**
	 * Test metody acceptEvent klasy QuereManager
	 * Test 1:
	 * Przypadek, w ktorym aktualna ilosc batchy jest mniejsza od ilosci maksymalnej.
	 * Oczekiwany rezultat:
	 * Zwrocona wartosc typu boolean: true 
	 */
	
	public void acceptEventTest1() {
		
		/**
		 * Ustawianie kolejki wedlug pliku konfiguracyjnego
		 */
		
		manager.setBatchSize(conf.getBatchSize());
	
		/**
		 * Tworzenie 7 przykladowych obiektow klasy Event
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
		 * Sprawdzanie odpowiedzi metody acceptEvent,
		 * gdzie oczekiwana odpowiedzia jest wartosc true
		 */
		
		assertEquals( "Accept Event", true, manager.acceptEvent(sample[numberOfEvents-1]));
		
	}
	
	@Test
	
	/**
	 * Test metody acceptEvent klasy QuereManager
	 * Test 2:
	 * Przypadek, w ktorym aktualna ilosc batchy jest wypelniona do ilosci maksymalnej,
	 * i dochodzi nastepny Event.
	 * Oczekiwany rezultat:
	 * Zwrocona wartosc typu boolean: false 
	 */
	
	public void acceptEventTest2() {
		
		/**
		 * Ustawianie kolejki wedlug pliku konfiguracyjnego
		 */
		
		manager.setBatchSize(conf.getBatchSize());
	
		/**
		 * Tworzenie 11 przykladowych obiektow klasy Event
		 */
	
		int numberOfEvents = 11;
	
		Event[] sample = new Event[numberOfEvents];
	
		for (int counter = 0; counter < numberOfEvents; counter++) {
		
			sample[counter] = new Event( null, exampleString, null);
		
		}
		
		for ( int t2 = 0; t2 < numberOfEvents-1; t2++) {
		
			manager.acceptEvent(sample[t2]);
			
		}
	
		/**
		 * Kluczowy moment testu:
		 * W tym momencie kolejka jest juz przepelniona 
		 * i nastepuje dojscie kolejnego Eventu
		 * Oczekiwana wartosc zwrotna metody: false
		 */
		
		assertEquals( "Reject Event", false, manager.acceptEvent(sample[numberOfEvents-1]));
		
	}

	@Test
	
	/**
	 * Test metody sendEvents klasy QuereManager
	 * Test 3:
	 * Przypadek, w ktorym kolejka zostaje zapelniona,
	 * poczym dziala metoda sendEvents.
	 * Oczekiwany rezultat:
	 * Ilosc Eventow po dzialaniu metody sendEvents wynosi 0
	 */
	
	public void sendEventsTest1() {
		
		/**
		 * Ustawianie kolejki wedlug pliku konfiguracyjnego
		 */
		
		manager.setBatchSize(conf.getBatchSize());
	
		/**
		 * Przypisanie adaptera do managera kolejki
		 */
		
		manager.output = OT;
		
		
		/**
		 * Tworzenie 10 przykladowych obiektow klasy Event
		 */
	
		int numberOfEvents = 10;
	
		Event[] sample = new Event[numberOfEvents];
	
		for (int counter = 0; counter < numberOfEvents; counter++) {
		
			sample[counter] = new Event( null, exampleString, null);
		
		}
		
		for ( int t3 = 0; t3 < numberOfEvents; t3++) {
		
			manager.acceptEvent(sample[t3]);
			
		}	
		
		/**
		 * Kluczowy moment testu:
		 * Sprawdzanie poprawnosci dzialania metody
		 * sendEvents
		 * Oczekiwana ilosc obiektow typu Event po wywolaniu metody: 0
		 */
	
		manager.sendEvents();
		assertEquals("Number of Events must be 0", 0, manager.currentSize());
		
	}
	
}