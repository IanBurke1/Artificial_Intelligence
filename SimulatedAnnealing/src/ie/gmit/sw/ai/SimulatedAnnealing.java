/*
 * Author: Ian Burke - G00307742
 */
package ie.gmit.sw.ai;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.xml.sax.ErrorHandler;

/*
 * This class is responsible for carrying out Simulated Annealing algorithm for breaking the cipher.
 * SA uses randomization to avoid 'local maximum' as seen in the Hillclimbing algorithm.
 * SA is very successful in solving lengthy ciphers.
 * The idea is to start with a random key, called the parent key, and to make a change (for example by swapping two letters) to create a child key. 
 * Plaintext is made from parent and from child, and if plaintext from the child is ‘better’ than from the parent, the child replaces the parent. 
 * Quality of plaintext is judged by scoring each tetragraph/4gram according to its frequency in the English language. 
 */

public class SimulatedAnnealing {
	
	// Variables
	private int temperature;
	private int transitions;
	//private SecureRandom random;
	private GenerateKey key;
	private Quadgrams grams;
	private PlayfairDecryption playfair;
	private Map<String, Integer> ngramsMap;
	
	// ========= CONSTRUCTOR ==========================================================================
	public SimulatedAnnealing(int temperature, String cipherText) {
		super();
		this.playfair = new PlayfairDecryption();
		this.playfair.setCipherText(cipherText);
		this.grams = new Quadgrams();
		this.key = GenerateKey.genKeyInstance();
		this.temperature = temperature;
		this.transitions = 55000;
		
	}//Contructor end
	
	// annealing method is where the annealing process is conducted
	public void annealing() throws Throwable {
		// load in our ngrams/tetragraphs
		ngramsMap = grams.parseGrams();
		
		//Step 1: Generate a random 25 letter key called parent
		String parent = key.genkey();
		//Step 2: Decrypt the cipher-text with the parent key
		String decrypt = playfair.decrypt(parent);
		//Step 3: Score the fitness of the key using ngrams and log probability
		double parentFitness = grams.getFitness(decrypt);
		double bestFitness = parentFitness;
		double probability;
		double fitness = bestFitness;
		
		Random random = ThreadLocalRandom.current();
		
		//Step 4: Start at high temperature and Loop through each temperature (cooling schedule)
		for(int temp = temperature; temp > 0; temp--) {
			//Step 5: Loop through each transition
			for(int trans = transitions; trans > 0; trans--) {
				
				//Step 6: Set child key to a shuffled parent key
				String child = key.shuffle(parent);
				decrypt = playfair.decrypt(child); //decrypt the child key
				
				//Step 7: Score the fitness of the child key
				double childFitness = grams.getFitness(decrypt);
				//Step 8: Set delta to logProbability(child) - logProbability(parent)
				double delta = childFitness - parentFitness;
				// Step 9: If delta is bigger than zero then new key is better
				if(delta > 0) {
					//Step 10: set parent key to child key
					parent = child;
					// and also set parentFitness to child fitness
					parentFitness = childFitness;
				}//if end
				//Step 11: If delta is less than zero then new key is worse
				else 
				{
					//Step 12: set parent to child with probability e^(delta / temp)
					probability = (Math.exp(delta / temp));
					if(probability > random.nextDouble()) {
						parent = child;
						parentFitness = childFitness;
					}
					
				}//else end
				// parentFitness score is bigger than previous score then..
				if(parentFitness > bestFitness) {
					//set bestFitness score to parentfitness score
					bestFitness = parentFitness;
					//System.out.printf("\nTemp: %d Transition: %d Key: %s Score: %.2f", temp, trans, parent, bestFitness);
					System.out.println("\nTemperature: " + temp + "\n" + "Transition: " + trans + "\n" + "Key: " + parent + "\n" + "Fitness score: " + bestFitness);
					System.out.println("\nDecrypted message: " + "\n" + playfair.decrypt(parent));
				}//if end
			} // transition loop end
			
			//if current bestfitness score is bigger than previous score/1.5
			if(bestFitness > (fitness/1.5)) {
				if(bestFitness > (fitness/1.6)) 
					break;
			}
			System.out.println("\n Best Key found: " + parent + "\nDecrypted Message: " + playfair.decrypt(parent));
			
			try(PrintWriter out = new PrintWriter("plainText.txt")){
			out.println("Key: \n" + parent + "\n" + "Decrypted Text: \n" + "\n" + playfair.decrypt(parent));
			out.close();
			}
			
		} // temperature loop end
	} //annealing end
} //Class end
