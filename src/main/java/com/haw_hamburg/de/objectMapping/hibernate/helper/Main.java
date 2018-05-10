package com.haw_hamburg.de.objectMapping.hibernate.helper;

import com.haw_hamburg.de.objectMapping.hibernate.app.FrameworkTest;

public class Main {
	public static void main(String[] args) {
		// MongoDB
		FrameworkTest mongodb = new FrameworkTest(500, 3);
		try {
			mongodb.performWriteTest().printMeasureResult();
		} catch (Exception e) {
			System.out.println("Test Failed");
			e.printStackTrace();
		}
	}
}
