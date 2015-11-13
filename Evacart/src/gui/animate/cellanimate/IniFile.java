
package gui.animate.cellanimate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


class IniFile 
{
	private String filename;
	private Hashtable groupList = new Hashtable();
	private Vector groupNames = new Vector();
	
	private void add(String groupName, String key, String value)
	{
		groupName = groupName.toLowerCase();
		key = key.toLowerCase();
		Hashtable group = (Hashtable)groupList.get(groupName);
		if(group==null)
		{
			group = new Hashtable();
			groupList.put(groupName, group);
			groupNames.addElement(groupName);
		}
		Vector item = (Vector)(group.get(key));
		if(item==null)
		{
			item = new Vector();
			group.put(key, item);
		}
		item.add(value);
	}
	
	public String GetFileName() { return filename; }
	
	public Vector get(String groupName, String key)
	{
		Hashtable group = (Hashtable)groupList.get(groupName.toLowerCase());
		if(group!=null)
		{
			return (Vector)group.get(key.toLowerCase());
		}
		return null;
	}

	
	public IniFile() throws IOException 
	{
		this("simu.ma"); 
	}

	public IniFile(String ma_file) throws IOException
	{
		filename = ma_file;
		String groupName = "unknown";
		String key = null;
		String value = null;
			
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(filename));
		String data = reader.readLine();
		while(data!=null)
		{
			int index = data.indexOf('%');
			if(index>=0)
			{
				data = data.substring(0, index);
			}
			data = data.trim();
			if(data.length()>0 && data.charAt(0)!='#')
			{
				if(data.charAt(0)=='[' && data.charAt(data.length()-1)==']')
				{
					groupName = data.substring(1, data.length()-1).trim();
				}
				else
				{
					index = data.indexOf(" : ");
					if(index>0)
					{
						key = data.substring(0, index).trim();
						value = data.substring(index+3).trim();
						this.add(groupName, key, value);
					}
				}
				
			}
			data = reader.readLine();
		}
		reader.close();
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		for(int j=0; j<groupNames.size(); j++)
		{
			String groupName = (String) groupNames.elementAt(j);
			buf.append("["+ groupName + "]\n");
			Hashtable group = (Hashtable)groupList.get(groupName);
			Enumeration iter2 = group.keys();
			while(iter2.hasMoreElements())
			{
				String key = (String) iter2.nextElement();
				Vector list = (Vector)group.get(key);
				for(int i=0; i<list.size(); i++)
				{
					buf.append(key + " : " + list.elementAt(i) + "\n");
				}
			}
			buf.append("\n");
		}
		return buf.toString();
	}

	public String[] findAllCellComponents()
	{
		Vector list = new Vector();
		for(int i=0; i<groupNames.size(); i++)
		{
			String groupName = (String) groupNames.elementAt(i);
			Vector items = get(groupName, "type");
			if(items!=null && "cell".equalsIgnoreCase((String)items.elementAt(0)))
			{
				list.addElement(groupName);
			}
		}
		if(list.size()>0)
		{
			String[] cells = new String[list.size()];
			list.toArray(cells);
			return cells;
		}
		return new String[0];
		
	}

	public static void main(String[] args) 
	{
		try {

			IniFile ini = new IniFile("Traffic.ma");
			String[] cells = ini.findAllCellComponents();
			for(int i=0; i<cells.length; i++)
			{
				System.out.println(cells[i]);
			}

			//System.out.println(ini.toString());

		}catch(IOException e) {

			System.out.println(e.getMessage());

		}


	}
}
