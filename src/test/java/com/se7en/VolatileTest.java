package com.se7en;

public class VolatileTest {

	private volatile static int index = 0;
	
	private static int index1 = 0;
	
	public static void main(String[] args) {
		index++;
		
		index1++;
	}
	
}
