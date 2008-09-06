package edu.bu.cs673;

/*
 * Simple test class 
 */
public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("This is a test");
		
		System.out.println("Version 2");
		
		Car johnsCar = new Car();
		johnsCar.setMake("Ford");
		
		System.out.println("John's Car is a " 
				+ johnsCar.getMake());
	}

}
