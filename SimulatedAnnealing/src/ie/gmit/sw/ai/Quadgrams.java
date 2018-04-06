package ie.gmit.sw.ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

public class Quadgrams {
	private static final String FILENAME = "4grams.txt";
	private long totalCount;
	private Map<String, Integer> ngrams = new HashMap<String, Integer>();

	public Map<String, Integer> parseGrams() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(FILENAME))));
		String line;
		while((line = br.readLine()) != null) {
			ngrams.put(line.split(" ")[0], Integer.parseInt(line.split(" ")[1]));
			totalCount += Double.parseDouble(line.split(" ")[1]);
		}
		br.close();
		System.out.println(totalCount);
		return ngrams;
	}
	
	public double getFitness(String text) {
		double result = 0;
		int frequency = 0;
		
		for (int i = 0; i<text.length()-4; i++) {
			if(ngrams.get(text.substring(i, i+4)) != null) {
				frequency = ngrams.get(text.substring(i, i+4));
				
			}else {
				frequency = 1;
			}
			result += Math.log10((double) frequency/totalCount);
		}
		return result;
	}
	
} //Class end
