/*
 * Author: Ian Burke - G00307742
 */
package ie.gmit.sw.ai;

import java.awt.Point;
import java.util.Scanner;

/*
 * Adapted from: https://github.com/mckennapsean/code-examples/blob/master/Java/Playfair.java
 * 
 * This class is responsible for encrypting a message by setting a keyword.
 */

public class Playfair {
	
	// Playfair table
	private String[][] playfairTable = new String[5][5];
	private int length = 0;
	
	public static void main(String[] args) {
		Playfair p = new Playfair();
		p.encrypt();
	}
	
	Scanner scan = new Scanner(System.in);

	public void encrypt() {
		
		//================== KEYWORD ========================================
		System.out.print("Enter the keyword: ");
		String keyword = scan.nextLine();
		keyword = keyword.toUpperCase().replaceAll("[^A-Za-z0-9]", "");
		keyword = keyword.replace("J", "I");
		
		
		String key = keyword + "ABCDEFGHIKLMNOPQRSTUVWXYZ";
		
		// Fill string array with empty string first
	    for(int i = 0; i < 5; i++)
	        for(int j = 0; j < 5; j++)
	        	playfairTable[i][j] = "";
		
		for (int i = 0; i < key.length(); i++) {
			boolean repeat = false;
			boolean used = false;
			for (int x = 0; x<5; x++) {
				for (int z = 0; z<5; z++) {
					// Checking each letter in array to make sure theres no repeats 
					if(playfairTable[x][z].equals("" + key.charAt(i))) {
						repeat = true;
					}else if(playfairTable[x][z].equals("") && !repeat && !used) {
						playfairTable[x][z] = "" + key.charAt(i);
						used = true;
					} //else if end
				} //for end
			} //for end
		} //for end
		
		// Printing out playfair table with keyword 
	    for(int i = 0; i < 5; i++){
	        for(int j = 0; j < 5; j++){
	          System.out.print(playfairTable[i][j]+" ");
	        }
	        System.out.println(); //break line after each row
	    }
	    System.out.println();
	    
	    //============== PLAINTEXT ============================================
	    
	    // Split Message into digraphs
	    System.out.print("Enter message to be encrypted: ");
	    String plainText = scan.nextLine();
	    plainText = plainText.toUpperCase().replaceAll("[^A-Za-z0-9]", "");
	    plainText = plainText.replace("J", "I");
	    
	    
	    length = (int) plainText.length() / 2 + plainText.length() % 2;
	    
	    // insert x between double-letter digraphs & redefines "length"
	    for(int i = 0; i < (length - 1); i++){
	      if(plainText.charAt(2 * i) == plainText.charAt(2 * i + 1)){
	        plainText = new StringBuffer(plainText).insert(2 * i + 1, 'X').toString();
	        length = (int) plainText.length() / 2 + plainText.length() % 2;
	      } //If end
	} //for end
	    
	 // adds an x to the last digraph, if necessary
	    String[] digraph = new String[length];
	    for(int j = 0; j < length ; j++){
	      if(j == (length - 1) && plainText.length() / 2 == (length - 1))
	        plainText = plainText + "X";
	      digraph[j] = plainText.charAt(2 * j) +""+ plainText.charAt(2 * j + 1);
	} // for end
	    
	
	    // encodes the digraphs and returns the output
	    String out = "";
	    String[] encDigraphs = new String[length];
	    encDigraphs = encodeDigraph(digraph);
	    for(int k = 0; k < length; k++)
	    	out = out + encDigraphs[k];
	    System.out.println("Encrypted text: " + out);
	    
	    
	    
	} // encrypt end
	
	// encodes the digraph input with the cipher's specifications
	  private String[] encodeDigraph(String di[]){
	    String[] enc = new String[length];
	   
	    for(int i = 0; i < length; i++){
	      char a = di[i].charAt(0);
	      char b = di[i].charAt(1);
	      int r1 = (int) getPoint(a).getX();
	      int r2 = (int) getPoint(b).getX();
	      int c1 = (int) getPoint(a).getY();
	      int c2 = (int) getPoint(b).getY();
	      
	      // case 1: letters in digraph are of same row, shift columns to right
	      if(r1 == r2){
	        c1 = (c1 + 1) % 5;
	        c2 = (c2 + 1) % 5;
	        
	      // case 2: letters in digraph are of same column, shift rows down
	      }else if(c1 == c2){
	        r1 = (r1 + 1) % 5;
	        r2 = (r2 + 1) % 5;
	      
	      // case 3: letters in digraph form rectangle, swap first column # with second column #
	      }else{
	        int temp = c1;
	        c1 = c2;
	        c2 = temp;
	      }
	      
	      //performs the table look-up and puts those values into the encoded array
	      enc[i] = playfairTable[r1][c1] + "" + playfairTable[r2][c2];
	    }
	    return enc;
	}
	  
	  // returns a point containing the row and column of the letter
	  private Point getPoint(char c){
	    Point pt = new Point(0,0);
	    for(int i = 0; i < 5; i++)
	      for(int j = 0; j < 5; j++)
	        if(c == playfairTable[i][j].charAt(0))
	          pt = new Point(i,j);
	    return pt;
	}
	  
	 
	  
} // class end
