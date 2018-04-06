package ie.gmit.sw.ai;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class SimulatedAnnealing {
	
	
	private int temputure = 10;
	private int transitions = 50000;
	private Random random;
	private GenerateKey key;
	private Quadgrams grams;
	private PlayfairDecryption playfair;
	
	
	private Map<String, Integer> ngramsMap;
	
	public SimulatedAnnealing(String cipherText) {
		super();
		this.playfair.setCipherText(cipherText);
	}
	
	public void annealing() throws NumberFormatException, IOException {
		
		
		String parent = key.genkey();
		ngramsMap = grams.parseGrams();
		String decrypt = playfair.decrypt(parent);
		double parentFitness = grams.getFitness(decrypt);
		double bestFitness = parentFitness;
		
		double probability;
		
		random = new Random();
		
		double fitness = bestFitness;
		
		for(int temp = 10; temp > 0; temp--) {
			
			for(int trans = transitions; trans > 0; trans--) {
				
				
				String child = key.shuffle(parent);
				
				decrypt = playfair.decrypt(child);
				double childFitness = grams.getFitness(decrypt);
				
				double delta = childFitness - parentFitness;
				
				if(delta > 0) {
					
					parent = child;
					parentFitness = childFitness;
					
				}
				else 
				{
					probability = (Math.exp(delta / temp));
					if(probability > random.nextDouble()) {
						parent = child;
						parentFitness = childFitness;
					}
					
				}
				
				if(parentFitness > bestFitness) {
					bestFitness = parentFitness;
					System.out.printf("\nTemp: %d Transition: %d Key: %s Score: %.2f", temp, trans, parent, bestFitness);
				}
			} // for (trans) end
			if(bestFitness > (fitness/1.5)) {
				System.out.printf("\n----------------------------------------\nHIT! Temp: %d Key: %s Score: %.2f\n----------------------------------------\n", temp, parent, bestFitness);
				if(bestFitness > (fitness/1.6)) break;
			}
			System.out.println("\n\nKey found: " + parent + "\nDecrypted message: " + playfair.decrypt(parent));
		} // for (temp) end
	} //annealing end
	
	
	
} //Class end
