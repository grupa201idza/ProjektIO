package idz.a.output;

import java.util.List;
import idz.a.core.Configuration;
import idz.a.core.Event;

/*
 * OutputManagement serves just as a set of methods that need to be implemented in each OutputAdapter
 */
/**
 * Interfejs zawieraj¹cy zestaw metod które musz¹ byæ zrealizowane w kazdym adapterze wyjsciowym
 */
public interface OutputAdapter {
public void setupConfig(Configuration config);
public boolean storeEvents(List<Event> batch);
}
