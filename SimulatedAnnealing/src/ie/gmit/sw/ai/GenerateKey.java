package ie.gmit.sw.ai;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GenerateKey {
	private String key = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
	
	public String genkey() {
		int index;
		char[] keyword = key.toCharArray();
		Random random = new Random();
		for (int i = keyword.length - 1; i > 0; i--) {
			index = random.nextInt(i+1);
			if (index != i) {
				keyword[index] ^= keyword[i];
				keyword[i] ^= keyword[index];
				keyword[index] ^= keyword[i];
			}
		}
		return new String(keyword);
	}
	
	public String shuffle(String parentKey) {
		Random random = ThreadLocalRandom.current();
		int x = random.nextInt(100); //Math.random()*100;
		
		if(x >= 0 && x < 2) {
			return swapRows(parentKey, random.nextInt(4), random.nextInt(4));
		} else if ( x >= 2 && x < 4) {
			return swapCols(parentKey, random.nextInt(4), random.nextInt(4));
		} else if ( x >= 4 && x < 6) {
			return flipRows(parentKey);
		} else if ( x >= 6 && x < 8) {
			return flipCols(parentKey);
		} else if ( x >= 8 && x < 10) {
			return new StringBuffer(parentKey).reverse().toString();
		} else {
			int a = random.nextInt(parentKey.length()-1);
			int b = random.nextInt(parentKey.length()-1);
			b = (a == b) ? (b == parentKey.length()-1) ? b - 1 : b + 1 : random.nextInt(parentKey.length()-1);
			char[] res = parentKey.toCharArray();
			char tmp = res[a];
			res[a] = res[b];
			res[b] = tmp;
			
			return new String(res);
		}

	} //shuffle ends
	
	private String flipRows(String key) {
		String[] rows = new String[5];
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < 5; i++) {
			rows[i] = key.substring(i*5, i*5 + 5);
			rows[i] = new StringBuffer(rows[i]).reverse().toString();
			sb.append(rows[i]);
		}
		return sb.toString();
		
	}//flipRows end
	
	private String flipCols(String key) {
		char[] cols = key.toCharArray();
		int length = key.length() - (key.length()/5);
		
		for(int i = 0; i < key.length() / 5; i++) {
			for(int j = 0; j < key.length() / 5; j++) {
				char tmp = key.charAt(i*5 + j);
				cols[(i*5) + j] =  key.charAt(length + j);
				cols[length + j] =  tmp;
			}
			length -= 5;
		}
		return new String(cols);
}
	private String swapRows(String key, int r1, int r2) {	
		return (r1 == r2) ? swapRows(key, new Random().nextInt(4), new Random().nextInt(4)) :  permute(key, r1, r2, true);
	}
	
	//Method to swap columns
	private String swapCols(String key, int c1, int c2) {
		return (c1 == c2) ? swapCols(key, new Random().nextInt(4), new Random().nextInt(4)) : permute(key, c1, c2, false);
}
	private String permute(String key, int a, int b, boolean rw) {
		char[] newKey = key.toCharArray();
		if(rw) {
			a *= 5;
			b *= 5;
		} 
		for(int i = 0; i < key.length() / 5 ; i++) {
			int index = (rw) ? i : i*5;
			char tmp =  newKey[(index + a)];
			newKey[(index + a)] = newKey[(index + b)];
			newKey[(index + b)] = tmp;				
		}
		return new String(newKey);
}
} //Class end
