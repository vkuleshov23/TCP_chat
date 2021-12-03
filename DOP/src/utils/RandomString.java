package src.utils;

import java.util.Random;

public class RandomString {
	
	private static int a = 97;
	private static int z = 122;

	static public String random() {
		Random rand = new Random();
		String str = "";
		for(int i = 0; i < 5; i++) {
			str += (char)(a + (int)(rand.nextFloat() * (z - a + 1)));
		}
		return str;
	}
}