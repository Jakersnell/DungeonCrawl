package com.skilldistillery.lordo.app;

import java.util.Arrays;
import java.util.List;

public class Test {
	App app = new App();
	UI cli = new UI(app);
	
	public static void main(String[] args) {
		Test test = new Test();
		test.test_userSelectFrom();

	}
	
	public void test_userSelectFrom() {
		List<String> list = Arrays.asList("one", "two", "three");
		String select = cli.userSelectFrom(list);
		System.out.println(select);
	}

}
