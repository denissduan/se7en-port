package com.se7en;

import org.junit.Test;

public class StringTest {

	@Test
	public void test(){
		String str = "a.b";
		System.out.println(str.replaceAll("\\.", "\\\\\\\\."));
	}
	
}
