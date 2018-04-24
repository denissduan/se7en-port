package com.se7en;
import org.junit.*;

public class FinalTest {

	public StringBuilder t1(){
		final StringBuilder sb = new StringBuilder();
		return sb;
	}
	
	@Test
	public void t2(){
		StringBuilder sb1 = t1();
		sb1 = new StringBuilder();
	}

	public static void t3(String str){
		str = "abc";
	}

	public static void main(String[] args) {
		String str = "def";
		t3(str);
		System.out.println(str);
	}

}
