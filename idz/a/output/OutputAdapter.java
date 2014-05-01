package idz.a.output;

/*
 * OutputManagement serves just as a set of methods that need to be implemented in each OutputAdapter
 */

public interface OutputAdapter {
public void setupConfig(Configuration config);
public boolean storeEvents(List batch);
}
