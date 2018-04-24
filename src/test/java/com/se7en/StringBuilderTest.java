package com.se7en;

import com.se7en.common.util.Console;

public class StringBuilderTest {

//	@Test
	public void test1(){
		StringBuilder sb = new StringBuilder("a");
		StringBuilder sb1 = new StringBuilder("b");
		StringBuilder sb2 = new StringBuilder("c");
		
		sb.append(sb1);
		Console.msgln(sb);
		
		sb1.append("b1b2");
		Console.msgln(sb);
	}
	
}
