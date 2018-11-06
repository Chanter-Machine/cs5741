package utils;

import java.util.Random;

public class NumGeneration {
	
	public static int generateNumber(int start, int end) {
		Random r = new Random();
		return r.nextInt(end) + start;
	}

}
