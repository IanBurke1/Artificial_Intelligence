/*
 * Author: Ian Burke - G00307742
 */
package ie.gmit.sw.ai;

import java.io.IOException;
import java.util.Scanner;

public class CipherBreaker {
	
	// =========== MAIN METHOD =============================
	public static void main(String[] args) throws IOException {
		
		//Creating a scanner object to read in user input
		Scanner scan = new Scanner(System.in);
		
		System.out.println("1. Decrypt Text");
		System.out.println("2. Exit ");
		
		int option = scan.nextInt();
		
		switch(option) {
		case 1: 
			System.out.print("Enter cypher text: ");
			String cypherText = scan.nextLine();
			
			
			long startTime = System.currentTimeMillis();
			
			SimulatedAnnealing sa = new SimulatedAnnealing(cypherText);
			sa.annealing();
			System.out.println("\nExecution time: " + ((System.currentTimeMillis() - startTime)/1000) + "s");
			break;
			
		case 2: System.out.println("Exit");
		break;
		}
	}

}
