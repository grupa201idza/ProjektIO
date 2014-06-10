package idz.a.output;

import java.util.List;
import idz.a.core.Configuration;
import idz.a.core.Event;

/*
 * OutputManagement serves just as a set of methods that need to be implemented in each OutputAdapter
 */
/**
 * Interfejs zawieraj�cy zestaw metod kt�re musz� by� zrealizowane w kazdym adapterze wyjsciowym
 */
public interface OutputAdapter {
public void setupConfig(Configuration config);
public boolean storeEvents(List<Event> batch);
}
