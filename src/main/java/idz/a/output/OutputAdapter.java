package src.main.java.idz.a.output;

import java.util.List;
import src.main.java.idz.a.core.Configuration;
import src.main.java.idz.a.core.Event;

/*
 * OutputManagement serves just as a set of methods that need to be implemented in each OutputAdapter
 */

public interface OutputAdapter {
public void setupConfig(Configuration config);
public boolean storeEvents(List<Event> batch);
}
