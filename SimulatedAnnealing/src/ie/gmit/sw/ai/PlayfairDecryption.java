package ie.gmit.sw.ai;

import java.awt.Point;

/*
 * Adapted from: https://github.com/mckennapsean/code-examples/blob/master/Java/Playfair.java
 */

public class PlayfairDecryption {
	private char[][] playfairTable;
	private String cipherText;
	private StringBuilder plainText;
	

	public PlayfairDecryption() {
		super();
		this.playfairTable = new char[5][5];
		this.cipherText  = "";
		this.plainText = new StringBuilder();
		
	}

	public String decrypt(String key) throws Exception {
		
		char[][] playfairTable = new char[5][5];
		String keyword = key;
		int index = 0;
		
		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++) {
				playfairTable[i][j] = keyword.charAt(index);
				index++;
			}
		}
		this.playfairTable = playfairTable;
		
		StringBuilder sb = new StringBuilder();
		
		for(index = 0; index < this.cipherText.length() / 2; index++ ) {
			char a = this.cipherText.charAt(2 * index);
			char b = this.cipherText.charAt(2 * index + 1);
		    int r1 = (int) getPosition(a, playfairTable).getX();
	        int r2 = (int) getPosition(b, playfairTable).getX();
	        int c1 = (int) getPosition(a, playfairTable).getY();
	        int c2 = (int) getPosition(b, playfairTable).getY();
	        
	      if(r1 == r2){
	        c1 = (c1 + 4) % 5;
	        c2 = (c2 + 4) % 5;
	      }else if(c1 == c2){
	        r1 = (r1 + 4) % 5;
	        r2 = (r2 + 4) % 5;
	      }else{
	        int temp = c1;
	        c1 = c2;
	        c2 = temp;
	        
		}
	      sb.append(playfairTable[r1][c1] +""+ playfairTable[r2][c2]);
			
	}
		return sb.toString();
}
	
	private Point getPosition(char c, char[][] playfairTable) {
	    Point pt = new Point(0,0);
	    for(int i = 0; i < 5; i++)
	      for(int j = 0; j < 5; j++)
	        if(c == playfairTable[i][j])
	          pt = new Point(i,j);
	return pt;
		
	}

	public String getCipherText() {
		return cipherText;
	}

	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}

	public String getPlainText() {
		return plainText.toString();
	}

	public void setPlainText(StringBuilder plainText) {
		this.plainText.append(plainText);
	}
	
	
	
	

}
