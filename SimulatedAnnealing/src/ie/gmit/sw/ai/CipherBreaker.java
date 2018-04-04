/*
 * Author: Ian Burke - G00307742
 */
package ie.gmit.sw.ai;

import java.util.Scanner;

public class CipherBreaker {
	
	// =========== MAIN METHOD =============================
	public static void main(String[] args) {
		
		//Creating a scanner object to read in user input
		Scanner scan = new Scanner(System.in);
		
		System.out.println("======= Main Menu ==========");
		System.out.println("1. Ecrypt \n2. Decrypt \n3. Simulated Annealing");
		System.out.println();
		int option = scan.nextInt(); //Scan user input 
		
		switch (option) {
		case 1: System.err.println("Encyption..");
			break;
		case 2: System.out.println("Decryption..");
			break;
		case 3: System.out.println("Simulated Annealing");
			break;
		default: System.out.println("Error");
			break;
		
		}
	}

}
