package idz.a.input;

import idz.a.core.Configuration;
import idz.a.core.QueueManager;

public interface InputAdapter {

	public abstract void setupConfig(Configuration config);
	public abstract void connectToQueueManager(QueueManager queue);
}
