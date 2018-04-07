/*
 * Author: Ian Burke - G00307742
 */
package ie.gmit.sw.ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/*
 * This class is responsible for running the Main method i.e. Run the program
 */

public class CipherBreaker {
	
	// =========== MAIN METHOD =============================
	public static void main(String[] args) throws Exception, Throwable {
		
		//Creating a scanner object to read in user input
		Scanner scan = new Scanner(System.in);
		int temp = 0;
		int optimumTemp = 0;
		long startTime = 0;
		
		System.out.println("============== Playfair Cipher =================");
		System.out.println("|                                              |");
		System.out.println("| Options:                                     |");
		System.out.println("|        1. Enter Cipher Text to decrypt       |");
		System.out.println("|        2. Decrypt a file             	       |");
		System.out.println("|        3. Encrypt                            |");
		System.out.println("|        4. Exit                               |");
		System.out.println("================================================");
		System.out.print("Enter option here: ");
		int option = scan.nextInt();
		
		switch(option) {
		case 1: 
			System.out.print("Enter the cipher: ");
			String cypherText = scan.next(); 
			cypherText = cypherText.toUpperCase();
			cypherText = cypherText.replaceAll("J", "I");
			cypherText = cypherText.replaceAll("\\s+", "");
			cypherText = cypherText.replaceAll("[^a-zA-Z]", "");
			if (cypherText.length()%2 != 0){
				cypherText = cypherText.concat("X");
			}
			System.out.println();
			System.out.println("Simulating Annealing...");
			//To find the optimum temperature = 10 + 0.087*(length - 84)
			// 12 instead of 10 seems to work better?
			temp = (int) ((12 + 0.087 * (cypherText.length() - 84)));
			optimumTemp = temp / 3;
			startTime = System.currentTimeMillis();
			
			SimulatedAnnealing sa = new SimulatedAnnealing(optimumTemp, cypherText);
			sa.annealing();
			System.out.println("\nExecution time: " + ((System.currentTimeMillis() - startTime)/1000) + "s");
			break;
			
		case 2:
			System.out.println("Enter file name (filename.txt): ");
			String filename = scan.next();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
			String cipherText = br.readLine();
			cipherText = cipherText.toUpperCase();
			cipherText = cipherText.replaceAll("J", "I");
			cipherText = cipherText.replaceAll("\\s+", "");
			cipherText = cipherText.replaceAll("[^a-zA-Z]", "");
			if (cipherText.length()%2 != 0){
				cypherText = cipherText.concat("X");
			}
			System.out.println();
			System.out.println("Simulating annealing...");
			temp = (int) ((12 + 0.087 * (cipherText.length() - 84)));
			optimumTemp = temp / 3;
			startTime = System.currentTimeMillis();
			
			SimulatedAnnealing sa2 = new SimulatedAnnealing(optimumTemp, cipherText);
			sa2.annealing();
			System.out.println("\nExecution time: " + ((System.currentTimeMillis() - startTime)/1000) + "s");
			break;
		
		case 3: 
			Playfair pf = new Playfair();
			pf.encrypt();
			break;
		case 4: 
			System.out.println("Exiting..");
			System.exit(0);
			break;
		
			}// switch end
					
	}//main() end

}//class end
