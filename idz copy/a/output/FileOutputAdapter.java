package idz.a.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import idz.a.core.Configuration;
import idz.a.core.Event;
import idz.a.core.ForeseenException;

//TODO test JSON File Output especially "storeEvents"
/*
 * As for now made for local file system
 * 
 * As for tests: setupConfig with null Configuration and null outputFilePath will be safe
 * 				storeEvents will not check if has place for other files
 */
public class FileOutputAdapter implements OutputAdapter {

	private String outputFilePath;

	FileOutputAdapter(Configuration config){
		setupConfig(config);
	}
	
	/*
	 * Applies valid configuration
	 */
	@Override
	public void setupConfig(Configuration config) {
		try {
			if (config != null & config.getOutputFilePath() != null)
				this.outputFilePath = config.getOutputFilePath();
			else if (config.getOutputFilePath() == null)
				throw new ForeseenException(
						"Denied: Tried to apply UnindentifiedFilePath");

		} catch (ForeseenException fore) {
			System.out.println("ForeseenException catched");
		}
	}

	/*
	 * method theorically able to create needed file if it do not exists
	 */
	@Override
	public boolean storeEvents(List<Event> batch) {
		File storageFile = new File(outputFilePath);
		if (!storageFile.exists()) {
			storageFile.mkdirs();
			try {
				storageFile.createNewFile();
			} catch (IOException e) {
				System.out
						.println("Target file not exists yet it was impossible to create it");
				e.printStackTrace();
				return false;
			}
		}
		/*
		 * main executive part of method if given real file and program has
		 * rights to write in it then it starts storing Events
		 */
		if (storageFile.isFile() & storageFile.canWrite()) {

			/*
			 * Operative variables to use in loop Loop works on Iterator object
			 * and continues till iterator is able to return next object
			 */
			String entryEvent = new String();
			Event temporary;
			for (Iterator<Event> batchScope = batch.iterator(); batchScope
					.hasNext();) {
				temporary = batchScope.next();
				entryEvent = new String("{ \"time\" : "
						+ temporary.getTimestamp() + " \"detail\" : "
						+ temporary.getDetails() + " \"logLevel\" : "
						+ temporary.getLoglevel() + " \" }");
				/*
				 * attempts to write line to file under given filepath
				 */
				try (PrintWriter out = new PrintWriter(new BufferedWriter(
						new FileWriter(storageFile.getAbsolutePath(), true)))) {
					out.println(entryEvent);
				} catch (IOException e) {
					System.err.println(e);
				}
			}

			return true;
		}

		return false;
	}

}
