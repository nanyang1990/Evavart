import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class logReader {

	BufferedReader br;
	String line;
	String saver;
	String data;

	public logReader(File logfile) {

		try {
			br = new BufferedReader(new FileReader(logfile));
			line = br.readLine();

			while (line != null) {
				saver=line;
				line=br.readLine();
			}
			//System.out.println(line);
			//System.out.println(saver);
			int start = saver.indexOf("/")+2;
			//System.out.println("start="+start);
			int end = saver.substring(start).indexOf("/") + start-1;
			//System.out.println("saver.substring(start).indexOf"+saver.substring(start).indexOf("/"));
			//System.out.println("end="+end);
			data = saver.substring(start, end);
			System.out.println("execution time="+data);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected String getData() {
		return data;
	}
}