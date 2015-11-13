/*
 * Created on 24/06/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package gui.javax.util;

import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * @author jcidre
 * A DataPersister that persist to a file
 */
public class FileDataPersister extends DataPersister {
	private static DataPersister instance;

	/**
	 * 
	 */
	public FileDataPersister() {
		super();
	}

	public static DataPersister getInstance() {
		if (instance == null) {
			instance = new FileDataPersister();
		}
		return instance;
	}

	public void loadFrom(CommentBufferedReader reader) throws IOException {
		getGroups().clear();
		try {
			int cantGroups = Integer.parseInt(reader.readLine());
			for (int i = 0; i < cantGroups; i++) {
				String aGroupName = reader.readLine();
				Map aGroup = loadGroup(reader);
				getGroups().put(aGroupName, aGroup);
			}
		}
		catch (NumberFormatException num) {
			System.err.println("No Persisted Data or Database corrupted!");
		}
	}

	public void saveTo(CommentBufferedWriter writer) throws IOException {
		writer.writeln("cantGroups ", getGroups().size());
		Iterator keys = getGroups().keySet().iterator();
		while (keys.hasNext()) {
			String key = (String)keys.next();
			Map group = (Map)getGroups().get(key);
			writer.writeln("a Group", key);
			dumpGroup(writer, group);
		}
		writer.flush();
		writer.close();
	}

}
