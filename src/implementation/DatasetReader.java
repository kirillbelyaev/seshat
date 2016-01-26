package implementation;

/**
 * @author: sandeepk
 * Implementation of Dataset Reader 1.6
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class DatasetReader {

	public static int num = 1;
	static Engine engine = new Engine();
	private static ArrayList<String> replace = new ArrayList<String>();
	static boolean success = false;
	static FileWriter fw;

	public static void main(String args[]) throws IOException {
		fw = new FileWriter(new File("orders_time_logOR"));
		String datasetPath = null;
		String queryPath = null;

		if (args.length > 0) {

			if (args.length != 4) {
				System.out
						.println("Invalid arguments. Must have -f and -q along with file paths");
			} else {
				if (args[0].compareTo("-f") == 0) {
					datasetPath = args[1];
					queryPath = args[3];
				} else {
					datasetPath = args[3];
					queryPath = args[1];
				}

			}
		} else {
			datasetPath = "orders.xml";
			queryPath = "query_OR";

		}
		try {
			FileWriter pw = new FileWriter(new File("orders_space_logOR"), true);
			int mb = 1024 * 1024;
			//
			// //Getting the runtime reference from system
			Runtime runtime = Runtime.getRuntime();
			runtime.gc();
			long memory = runtime.totalMemory() - runtime.freeMemory();
			long start = System.nanoTime();
			processDataSet(datasetPath, queryPath, pw, start);
			success = true;
			double d = ((double) (System.nanoTime() - start) / 1E9);
			pw.write("\n\n" + d);
			System.out.println(d);

			pw.close();
			fw.close();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private static void processDataSet(String datasetPath, String queryPath,
			FileWriter pw, long start) throws ParserConfigurationException,
			SAXException, IOException {

		Scanner scan, scan1;

		File q = new File(queryPath);
		scan1 = new Scanner(q);
		scan1.useDelimiter(System.getProperty("line.separator"));
		String query = "";
		while (scan1.hasNext()) {
			String temp = scan1.next().trim();

			query += temp + " , ";
			engine.addQuery(temp);
		}
		scan1.close();

		File f = new File(datasetPath);
		scan = new Scanner(f);
		scan.useDelimiter(System.getProperty("line.separator"));
		int i = 0;
		int mb = 1024 * 1024;
		while (scan.hasNext()) {
			String xmlMessage = scan.next();
			System.out.println((num++) + "\t" + engine.runQueries(xmlMessage)
					+ "\t" + query);

			// Getting the runtime reference from system
			Runtime runtime = Runtime.getRuntime();
			long memory = runtime.totalMemory() - runtime.freeMemory();
			pw.write("\n" + (runtime.totalMemory() - runtime.freeMemory()) / mb);
			fw.write("\n" + ((double) (System.nanoTime() - start) / 1E9));

		}

		replace.clear();
		scan.close();
	}

	public boolean getSuccess() {
		return success;
	}

}
