/*
 * Author: Ian Burke - G00307742
 */
package ie.gmit.sw.ai;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*
 * This class is responsible for generating a random key to use with Simulated Annealing to try and decrypt a cipher (encrypted text).
 */

public class GenerateKey {
	// Variables
	private static GenerateKey instance; // Create our class instance variable. default is null
	private String key = "ABCDEFGHIKLMNOPQRSTUVWXYZ"; // Letters must be all upper case and 'J' is removed.
	
	// ========== Constructor =============================================
	public GenerateKey() {
		
	}//constructor end
	
	// Singleton Responsibility
	// Ensure that only ONE instance of class is created i.e. Only ONE key is generated
	public static GenerateKey genKeyInstance() {
		// if instance equals null then generate new key instance otherwise return instance
		return (instance == null) ? new GenerateKey() : instance;
	}//genKeyInstance end
	
	// genkey method generates a 25 letter random key
	public String genkey() {
		//The key must be 25 letter long with no repeated letters i.e. unique
		// To achieve this, we use The Fisher-Yates Shuffle algorithm which
		// generates a random permutation of a finite sequence i.e.
		// it randomly shuffles an array key of n elements as follows: 
		char[] charKey = key.toCharArray(); // convert our string key above into a char array
		int index; //position in array
		Random random = ThreadLocalRandom.current(); //Random java class to randomize
		for(int i= charKey.length - 1; i>0; i--) { //loop through the key array to end
			index = random.nextInt(i + 1); // pick random letter
			if(index != i) { //if the index letter is not equal to current letter then..
				charKey[index] ^= charKey[i]; 
				charKey[i] ^= charKey[index];
				charKey[index] ^= charKey[i];
			}//if end
		}//for end
		// convert back to new String and return
		return new String(charKey);
		
	}//genkey end

	
	// This method is used to change around the key under a few conditions
	public String shuffle(String parentKey) {
		
		Random random = ThreadLocalRandom.current();
		int number = random.nextInt(100); //Math.random()*100;
		
		/*
		 * Changes to key:
		 * Swap single letters (90%)
		 * Swap random rows (2%)
		 * Swap columns (2%)
		 * Flip all rows (2%)
		 * Flip all columns (2%) 
		 * Reverse the whole key (2%)
		 */
		
		// If the number is 1 then..
		if(number >= 0 && number < 2) {
			return swapRows(parentKey, random.nextInt(4), random.nextInt(4));
			// If number is 2 or 3 then..
		} else if ( number >= 2 && number < 4) {
			return swapCols(parentKey, random.nextInt(4), random.nextInt(4));
			// If number is 4 or 5 then..
		} else if ( number >= 4 && number < 6) {
			return flipRows(parentKey);
			// If number is 6 or 7 then..
		} else if ( number >= 6 && number < 8) {
			return flipCols(parentKey);
			// If number is 8 or 9 then..
		} else if ( number >= 8 && number < 10) {
			return new StringBuffer(parentKey).reverse().toString();
			//otherwise if any other number then..
		} else {
			int a = random.nextInt(parentKey.length()-1);
			int b = random.nextInt(parentKey.length()-1);
			b = (a == b) ? (b == parentKey.length()-1) ? b - 1 : b + 1 : random.nextInt(parentKey.length()-1);
			char[] result = parentKey.toCharArray();
			char tmp = result[a];
			result[a] = result[b];
			result[b] = tmp;
			
			return new String(result);
		}

	} //shuffle ends
	
	// Method used to swap the rows in the table containing the key
	private String swapRows(String key, int r1, int r2) {	
		// if row1 equals row2 then randomly swap both rows, otherwise permute
		return (r1 == r2) ? swapRows(key, new Random().nextInt(4), new Random().nextInt(4)) :  permutation(key, r1, r2, true);
	}
	
	//Method used to swap columns
	private String swapCols(String key, int c1, int c2) {
		// if column1 equals column2 then randomly swap the columns, otherwise permute
		return (c1 == c2) ? swapCols(key, new Random().nextInt(4), new Random().nextInt(4)) : permutation(key, c1, c2, false);
	}
	
	// Method used to flip the rows
	private String flipRows(String key) {
		// create rows array size 5
		String[] rows = new String[5];
		// Create StringBuilder in order to modify the row
		StringBuilder sb = new StringBuilder();
		
		// loop through 5 times
		for(int i = 0; i < 5; i++) {
			//create a substring of key by multiplying startIndex by 5 and endIndex *5+5
			// Assign the substring to current row position
			rows[i] = key.substring(i*5, i*5 + 5); 
			// convert that row to string and reverse order
			// assign String to current row
			rows[i] = new StringBuffer(rows[i]).reverse().toString();
			sb.append(rows[i]); //append modified row
		}
		// return the string
		return sb.toString();
		
	}//flipRows end
	
	// Method used to flip the columns
	private String flipCols(String key) {
		// convert key to char array and assign to cols
		char[] cols = key.toCharArray();
		// change the length
		int length = key.length() - (key.length()/5);
		// loop through key columns
		for(int i = 0; i < key.length() / 5; i++) {
			for(int j = 0; j < key.length() / 5; j++) { 
				// flip the columns
				char tmp = key.charAt(i*5 + j);
				cols[(i*5) + j] =  key.charAt(length + j);
				cols[length + j] =  tmp;
			}
			length -= 5;
		}
		//return new columns
		return new String(cols);
	}//flipCols end
	
	private String permutation(String key, int a, int b, boolean rowSwap) {
		// Convert key string into char array
		char[] newKey = key.toCharArray();
		if(rowSwap) { //if rowSwap is True then..
			// either the rows or columns are multiplied
			a *= 5;
			b *= 5;
		} 
		// loop through key 
		for(int i = 0; i < key.length() / 5 ; i++) {
			int index = (rowSwap) ? i : i*5;
			char tmp =  newKey[(index + a)];
			newKey[(index + a)] = newKey[(index + b)];
			newKey[(index + b)] = tmp;				
		}
		//Return new key string
		return new String(newKey);
	}//permutation end
} //Class end
