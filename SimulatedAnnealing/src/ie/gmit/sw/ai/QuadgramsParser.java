package ie.gmit.sw.ai;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QuadgramsParser {public static String convertStreamToString(InputStream is) {
	@SuppressWarnings("resource")
	Scanner s = new Scanner(is).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
}
private static Map<String,Double> getFrequencies(String path) throws FileNotFoundException {
	Map<String,Double> frequencies = new HashMap<String, Double>();
	BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
	String allText = convertStreamToString(in);
	Map<String,Long> count = new HashMap<String, Long>();
	long totalCnt = 0;
	for (String current : allText.split("\n"))
	{
		String [] split = current.split(" ");
		long cnt = Long.parseLong(split[1]);
		count.put(split[0], cnt);
		totalCnt+=cnt;
	}
	for (Map.Entry<String, Long> entry : count.entrySet())
	{
		frequencies.put(entry.getKey(), (entry.getValue()+0.0)/totalCnt);
	}
	return frequencies;
}

public static Map<String,Double> getQuadgramFrequencies() throws FileNotFoundException
{
	return getFrequencies("4grams.txt");
}
public static void main(String[] args) throws FileNotFoundException {
	getQuadgramFrequencies();
	//System.out.println(freq2);
	//double total = 0 ;
	//for (Map.Entry<String, Double> entry:freq2.entrySet())total+=entry.getValue();
}
}
