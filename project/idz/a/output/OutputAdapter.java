package idz.a.output;

import java.util.List;
import idz.a.core.Configuration;

/*
 * OutputManagement serves just as a set of methods that need to be implemented in each OutputAdapter
 */

public interface OutputAdapter {
public void setupConfig(Configuration config);
public boolean storeEvents(List<Event> batch);
}
