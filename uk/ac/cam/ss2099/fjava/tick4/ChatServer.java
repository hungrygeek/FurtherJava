package uk.ac.cam.ss2099.fjava.tick4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import uk.ac.cam.cl.fjava.messages.Message;

public class ChatServer {
	public static void main(String[] args) throws IOException {
		int port = 0;
		try{
			port = Integer.parseInt(args[0]);
		} catch (ArrayIndexOutOfBoundsException e1){
			System.out.println("Usage: java ChatServer <port>");
			return;
		}catch (NumberFormatException e2){
			System.out.println("Usage: java ChatServer <port>");
			return;
		}
		ServerSocket server_s = null;
		try {
			server_s = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Cannot use port number <" + port + ">");
			return;
		}

		MultiQueue<Message> mulQ = new MultiQueue<Message>();
		Socket s = null;
		while (true) {
			s = server_s.accept();
			new ClientHandler(s, mulQ);
		}
	}
}

