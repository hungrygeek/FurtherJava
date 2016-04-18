package uk.ac.cam.ss2099.fjava.tick2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;


class TestMessageReadWrite {

	static boolean writeMessage(String message, String filename) {
		TestMessage MyMessage = new TestMessage();
		MyMessage.setMessage(message);
		try{
			FileOutputStream fos = new FileOutputStream(filename);		
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(MyMessage);
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	static String readMessage(String location) {
		TestMessage MyMessage = new TestMessage();
		ObjectInputStream in;
		try {
			if (location.startsWith("http://")) {
				in = new ObjectInputStream(new URL(location).openConnection().getInputStream());
			} else {
				in = new ObjectInputStream(new FileInputStream(location));
			} 
			MyMessage = (TestMessage)in.readObject();
			return MyMessage.getMessage();
		} catch (ClassNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public static void main(String args[]) {
		System.out.println(readMessage(args[0]));
	}
}