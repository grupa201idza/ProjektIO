package input.methods;

import javax.security.auth.login.Configuration;

public interface InputAdapter {
	public abstract void setupConfig(Configuration config);
	public abstract void connectToQueueManager(QueueManager queue);

}
