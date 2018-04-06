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
	private Map<String, Integer> ngrams;

	public Quadgrams() {
		this.ngrams = new HashMap<String, Integer>();
	}
	
	public Map<String, Integer> parseGrams() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(FILENAME))));
		String line = "";
		long count = 0;
		
		while((line = br.readLine()) != null) {
			ngrams.put(line.split(" ")[0], Integer.parseInt(line.split(" ")[1]));
			
			count += Double.parseDouble(line.split(" ")[1]);
		}
		setTotalCount(count);
		br.close();
		
		return this.ngrams;
	}
	
	public double getFitness(String cipherText) {
		double result = 0;
		int frequency = 0;
		
		for (int i = 0; i<cipherText.length()-4; i++) {
			if(ngrams.get(cipherText.substring(i, i+4)) != null) {
				frequency = ngrams.get(cipherText.substring(i, i+4));
				
			}else {
				frequency = 1;
			}
			result += Math.log10((double) frequency / this.getTotalCount());
		}
		return result;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	
	
} //Class end
