package com.haw_hamburg.de.objectMapping.hibernate.helper;

import com.haw_hamburg.de.objectMapping.hibernate.app.FrameworkTest;

public class Main {
	public static void main(String[] args) {
		// MongoDB
		FrameworkTest frameworkTest = new FrameworkTest(500, 3);
		try {
			System.out.println("WRITE TEST");
			frameworkTest.performWriteTest().printMeasureResultWrite();
			System.out.println("READ TEST");
			frameworkTest.performReadTest().printMeasureResultRead();
		} catch (Exception e) {
			System.out.println("Test Failed");
			e.printStackTrace();
		}
	}
}
