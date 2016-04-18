package uk.ac.cam.ss2099.fjava.tick4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

import uk.ac.cam.cl.fjava.messages.ChangeNickMessage;
import uk.ac.cam.cl.fjava.messages.ChatMessage;
import uk.ac.cam.cl.fjava.messages.Message;
import uk.ac.cam.cl.fjava.messages.RelayMessage;
import uk.ac.cam.cl.fjava.messages.StatusMessage;

public class ClientHandler {
	private Socket socket;
	private MultiQueue<Message> multiQueue;
	private String nickname;
	private MessageQueue<Message> clientMessages;

	public ClientHandler(Socket s, MultiQueue<Message> q) {
		socket = s;
		multiQueue = q;
		clientMessages = new SafeMessageQueue<Message>();
		multiQueue.register(clientMessages);
		nickname = "Anonymous" + (new Random().nextInt(90000) + 10000);
		StatusMessage status_msg = new StatusMessage(nickname + " connected from " + socket.getInetAddress().getHostName());
		multiQueue.put(status_msg);
		Thread thread_in = new Thread() {
			@Override
			public void run(){
				try{
					ObjectInputStream stream = new ObjectInputStream(socket.getInputStream());
					while(true){
						Object MyObject = stream.readObject();
						if (MyObject instanceof ChangeNickMessage){
							String tmp = nickname;
							nickname = ((ChangeNickMessage) MyObject).name;
							StatusMessage status = new StatusMessage(tmp + " is now known as " + nickname + ".");
							multiQueue.put(status);
						}
						if (MyObject instanceof ChatMessage){
							RelayMessage relay = new RelayMessage(nickname,((ChatMessage) MyObject));
							multiQueue.put(relay);
						}
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					multiQueue.deregister(clientMessages);
					StatusMessage quit = new StatusMessage(nickname + " has disconnected.");
					multiQueue.put(quit);
					return;
				}
			}
		};
		thread_in.setDaemon(true); //Marked as a daemon thread(The Java Virtual Machine exits when the only threads running are all daemon threads) 
		thread_in.start();
		
		Thread thread_out = new Thread() {
			@Override
			public void run(){
				try{
					ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());
					while(true){
						Message msg = clientMessages.take();
						stream.writeObject(msg);
						stream.flush();
					}

				} catch (IOException e) {
					multiQueue.deregister(clientMessages);
					StatusMessage quit = new StatusMessage(nickname + " has disconnected.");
					multiQueue.put(quit);
					return;
				}
			}
		};
		thread_out.setDaemon(true);
		thread_out.start();
	}
}