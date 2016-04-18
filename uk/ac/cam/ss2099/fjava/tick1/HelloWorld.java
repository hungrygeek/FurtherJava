package uk.ac.cam.ss2099.fjava.tick1;

public class HelloWorld {
	public static void main(String[] args) {
		try{
			System.out.println("Hello, "+ args[0]);
		} catch(Exception e) {
			System.out.println("Hello, world");
		}
	}
}
