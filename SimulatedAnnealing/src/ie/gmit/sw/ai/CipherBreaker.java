/*
 * Author: Ian Burke - G00307742
 */
package ie.gmit.sw.ai;

import java.io.IOException;
import java.util.Scanner;

public class CipherBreaker {
	
	// =========== MAIN METHOD =============================
	public static void main(String[] args) throws Exception, Throwable {
		
		//Creating a scanner object to read in user input
		Scanner scan = new Scanner(System.in);
		
		System.out.println("1. Decrypt Text");
		System.out.println("2. Exit ");
		
		int option = scan.nextInt();
		
		switch(option) {
		case 1: 
			System.out.print("Enter cypher text: ");
			String cypherText = scan.nextLine();
			cypherText = cypherText.toUpperCase().replaceAll("J", "I");
			cypherText = cypherText.replaceAll("\\s+", "");
			cypherText = cypherText.replaceAll("[^a-zA-Z]", "");
			if (cypherText.length()%2 != 0){
				
				//add a extra x to it to make even
				System.out.println("not even length of text so adding extra X to end of text");
				cypherText = cypherText.concat("X");
			}
			int temp = (int) ((12 + 0.087 * (cypherText.length() - 84)));
			int optimumTemp = temp / 3;
			long startTime = System.currentTimeMillis();
			
			SimulatedAnnealing sa = new SimulatedAnnealing(optimumTemp, cypherText);
			sa.annealing();
			System.out.println("\nExecution time: " + ((System.currentTimeMillis() - startTime)/1000) + "s");
			break;
			
		case 2: System.out.println("Exit");
		break;
		}
	}

}
