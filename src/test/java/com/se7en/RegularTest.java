package com.se7en;

import java.util.Scanner;
import org.junit.Test;;

public class RegularTest {

	@Test
	public void test(){
		//matches("[\u4E00-\u9FA5]+")
		Scanner scan = new Scanner(System.in);
		while(true){
			String str = scan.nextLine();
			System.out.println(str.matches("[\u4E00-\u9FA5]+"));
		}
	}
	
}
