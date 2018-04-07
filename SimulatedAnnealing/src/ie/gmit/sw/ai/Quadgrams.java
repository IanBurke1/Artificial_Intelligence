/*
 * Author: Ian Burke - G00307742
 */
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

/*
 * This class is responsible for parsing in the 4grams.txt file that contains 389,373 4-grams (gram = word or letter)
 * substring of word(s) of length 4. The grams are used to measure how similar some decrypted text is to English. 
 * A fitness measure or heuristic score can be computed from the frequency of occurrence of a 4-gram.
 */
public class Quadgrams {
	// Variables
	private static final String FILENAME = "4grams.txt";
	private long totalCount;
	private Map<String, Integer> ngrams; //In order to work with 4grams, put them into a hashmap<key, value>
	
	//========== CONSTRUCTOR ==============================================================================
	public Quadgrams() {
		this.ngrams = new HashMap<String, Integer>();
	}//constructor end
	
	// parseGrams method is used to parse in the 4grams file
	public Map<String, Integer> parseGrams() throws Exception {
		// Read the file
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(FILENAME))));
		String line = "";
		long count = 0; //get a count of grams
		
		//read each line until end of file
		while((line = br.readLine()) != null) {
			// split the grams from the frequencies and place in hashmap<gram, frequency>
			ngrams.put(line.split(" ")[0], Integer.parseInt(line.split(" ")[1]));
			//add to count
			count += Double.parseDouble(line.split(" ")[1]);
		}
		//set the total count of grams
		setTotalCount(count);
		// close the stream
		br.close();
		
		//return hashmap
		return this.ngrams;
	}//parseGrams end
	
	//getFitness method is used to determine the fitness of the ciphertext compared to english i.e. how close the text is to english
	// pass in the cipher text
	public double getFitness(String cipherText) {
		double result = 0;
		int frequency = 0;
		
		//loop through the cipher every 4 letters..
		for (int i = 0; i<cipherText.length() - 4; i++) {
			// if substring not equal to null then..
			if(ngrams.get(cipherText.substring(i, i + 4)) != null) {
				// get frequency of gram
				frequency = ngrams.get(cipherText.substring(i, i+4));
				
			}else {
				frequency = 1;
			}
			// sum the log probabilities
			result += Math.log10((double) frequency / this.getTotalCount());
		}
		
		return result;
	}// getFitness end

	//========= GETTERS AND SETTERS ======================================================
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	
	
} //Class end
