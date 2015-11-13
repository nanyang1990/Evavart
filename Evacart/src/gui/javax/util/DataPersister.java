package gui.javax.util;

import gui.javax.io.CommentBufferedReader;
import gui.javax.io.CommentBufferedWriter;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * @author jicidre@dc.uba.ar
 *
 */
public abstract class DataPersister {
	private Map groups;
	private Map keysAndValues;

	protected DataPersister() {
		setGroups(new Hashtable());
		keysAndValues = new Hashtable();
	}



	protected void dumpGroup(CommentBufferedWriter writer, Map group) throws IOException {
		writer.writeln("   group size", group.size());
		Iterator keys = group.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String)keys.next();
			String value = (String)group.get(key);
			writer.writeln("     a Key", key);
			writer.writeln("     a Value", value);
		}
	}

	protected Map loadGroup(CommentBufferedReader reader) throws IOException {
		int cant = Integer.parseInt(reader.readLine());
		Map retr = new Hashtable(cant);
		for (int i = 0; i < cant; i++) {
			String key = reader.readLine();
			String value = reader.readLine();
			retr.put(key, value);
		}
		return retr;
	}

	public void put(String groupName, String key, String value) {
		Map group;
		if (getGroups().containsKey(groupName)) {
			group = (Map)getGroups().get(groupName);
		}
		else {
			group = new Hashtable();
			getGroups().put(groupName, group);
		}
		group.put(key, value);
	}

	public static void main(String args[]) {
		try {
			/*
			DataPersister c = DataPersister.getInstance();
			c.put("grupo1", "key1", "val1");
			c.put("grupo1", "key2", "val2");
			c.put("grupo2", "key1", "val1");
			c.put("grupo2", "key2", "val2");

			c.saveTo(new File("c:\\xxxxx.txt"));

			c.loadFrom(new File("c:\\xxxxx.txt"));
			/*
					System.err.println(c.get("grupo1","key1"));
							System.err.println(c.get("grupo1","key2"));		
							System.err.println(c.get("grupo2","key1"));
							System.err.println(c.get("grupo2","key2"));			
							System.err.println(c.get("grupo2","key3"));			
							System.err.println(c.get("grupo3","key2"));					
							*/
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public String get(String groupName, String key, String dflt) {
		String retr = get(groupName, key);
		if (retr == null) {
			retr = dflt;
		}
		return retr;
	}

	public String get(String groupName, String key) {
		if (key != null) {
			Map group = (Map)getGroups().get(groupName);
			if (group != null) {
				return (String)group.get(key);
			}
			else {
				return null;
			}
		}
		return null;
	}

	public void remove(String groupName, String key) {
		Map group = (Map)this.getGroups().get(groupName);
		if (group != null) {
			group.remove(key);
		}
	}

	public void remove(String groupName) {
		getGroups().remove(groupName);
	}
	
	public abstract void loadFrom(CommentBufferedReader reader) throws IOException;
	public abstract void saveTo(CommentBufferedWriter writer) throws IOException;

	protected void setGroups(Map groups) {
		this.groups = groups;
	}

	protected Map getGroups() {
		return groups;
	}
}
