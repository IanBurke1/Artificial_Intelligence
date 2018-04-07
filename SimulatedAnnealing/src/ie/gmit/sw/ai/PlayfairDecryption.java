/*
 * Author: Ian Burke - G00307742
 */
package ie.gmit.sw.ai;

import java.awt.Point;

/*
 * Some code is adapted from: https://github.com/mckennapsean/code-examples/blob/master/Java/Playfair.java
 * 
 * This class is responsible for deciphering the cipher text using a generated key and the playfair table.
 */

public class PlayfairDecryption {
	// Variables
	private String cipherText;
	private StringBuilder plainText;
	
	//========== CONSTRUCTOR ===================================================
	public PlayfairDecryption() {
		super();
		this.cipherText  = "";
		this.plainText = new StringBuilder();
		
	}//constructor end

	// decrypt method decrypts cipher text using passed in key
	public String decrypt(String key) throws Exception {
		// Create a 5x5 table
		char[][] playfairTable = new char[5][5];
		String keyword = key; //assigning passed in generated key
		int index = 0; 
		
		//Loop through table/array and fill in the key
		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++) {
				playfairTable[i][j] = keyword.charAt(index);
				index++;
			}
		}
		// create a StringBuilder in order to modify diagraphs
		StringBuilder sb = new StringBuilder();
		// loop through cipherText and split into pairs of letters i.e. diagraphs
		for(index = 0; index < this.cipherText.length() / 2; index++ ) {
			char a = this.cipherText.charAt(2 * index);
			char b = this.cipherText.charAt(2 * index + 1);
		    int r1 = (int) getPosition(a, playfairTable).getX();
	        int r2 = (int) getPosition(b, playfairTable).getX();
	        int c1 = (int) getPosition(a, playfairTable).getY();
	        int c2 = (int) getPosition(b, playfairTable).getY();
	      //if diagraph letters in same row then..
	      if(r1 == r2){
	        c1 = (c1 + 4) % 5;
	        c2 = (c2 + 4) % 5;
	        //if diagraph letters in same column then..
	      }else if(c1 == c2){
	        r1 = (r1 + 4) % 5;
	        r2 = (r2 + 4) % 5;
	        //otherwise..
	      }else{
	        int temp = c1;
	        c1 = c2;
	        c2 = temp;
	        
	      }//else end
	      sb.append(playfairTable[r1][c1] +""+ playfairTable[r2][c2]);
			
		}//for end
		return sb.toString();
	}//decrypt end
	
	// Point is a built in java class that returns a point in (x,y) coordinate location
	// places character/letter in that position of table
	private Point getPosition(char c, char[][] playfairTable) {
	    Point pt = new Point(0,0);
	    for(int i = 0; i < 5; i++)
	      for(int j = 0; j < 5; j++)
	        if(c == playfairTable[i][j])
	          pt = new Point(i,j);
	return pt;
		
	} //getPosition end

	//============== GETTERS AND SETTERS ==================================================
	public String getCipherText() {
		return cipherText;
	}
	
	// Set cipherText to passed in cipher text from user in SA
	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}

	public String getPlainText() {
		return plainText.toString();
	}
	// set plaintext to sb String returned from decrypt()
	public void setPlainText(StringBuilder plainText) {
		this.plainText.append(plainText);
	}
	
} //Class end
