package uk.ac.cam.ss2099.fjava.tick2;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import uk.ac.cam.cl.fjava.messages.ChangeNickMessage;
import uk.ac.cam.cl.fjava.messages.ChatMessage;
import uk.ac.cam.cl.fjava.messages.DynamicObjectInputStream;
import uk.ac.cam.cl.fjava.messages.Execute;
import uk.ac.cam.cl.fjava.messages.NewMessageType;
import uk.ac.cam.cl.fjava.messages.RelayMessage;
import uk.ac.cam.cl.fjava.messages.StatusMessage;

@FurtherJavaPreamble (
		author = "Shi Shu", 
		crsid = "ss2099", 
		date = "11th November 2013", 
		summary = "FurtherJava.tick2", 
		ticker = FurtherJavaPreamble.Ticker.A)

public class ChatClient{
	static DynamicObjectInputStream stream = null;
	public static String time(){
		String current_time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		return current_time;
	}

	public static void main(String[] args) throws IOException {
		String server = null;
		int port = 0;

		try{
			server = args[0];
			port = Integer.parseInt(args[1]);
		}catch(ArrayIndexOutOfBoundsException e1){
			System.err.println("This application requires two arguments: <machine> <port>");
			return;
		}catch (NumberFormatException e2){
			System.err.println("This application requires two arguments: <machine> <port>");
			return;
		}

		final Socket s;
		try{
			s = new Socket(server, port);
			stream = new DynamicObjectInputStream((s.getInputStream()));
		}catch(ConnectException e){
			System.err.println("Cannot connect to "+ server +" on port "+ port);
			return;
		}
			System.out.println(time() + " [Client] Connected to " + server + " on port " + port +"." );
		Thread output = new Thread() {
			@Override
			public void run() {
				try {
					while(true){
						Object MyObject = stream.readObject();
						if (MyObject instanceof RelayMessage){
							System.out.println(time() + " [" + ((RelayMessage)MyObject).getFrom()+"] "+((RelayMessage)MyObject).getMessage());
						}
						else if (MyObject instanceof StatusMessage){
							System.out.println(time() + " " + "[Server] "+((StatusMessage)MyObject).getMessage());
						}
						else if (MyObject instanceof NewMessageType){
							stream.addClass(((NewMessageType) MyObject).getName(),((NewMessageType) MyObject).getClassData());
							System.out.println(time() + " [Client] New class " + ((NewMessageType) MyObject).getName() + " loaded.");
						}
						else{
							Class<?> someClass = MyObject.getClass();
							String name = someClass.getSimpleName();
							Field [] fields = someClass.getDeclaredFields();
							String string_out = null;
							for (int i = 0; i < fields.length; i++){
								fields[i].setAccessible(true);
								String field_name = fields[i].getName();
								Object field_object = fields[i].get(MyObject);
								if (i == 0){
									string_out = field_name + "(" + field_object + ")";
								} else {
									string_out = string_out + ", " + field_name + "(" + field_object +")";
								}
							}
							System.out.println(time() + " [Client] " + name + ": " + string_out);
							Method [] methods = someClass.getMethods();
							for (int i =0; i < methods.length; i++) {
								methods[i].setAccessible(true);
								Execute annotations = methods[i].getAnnotation(Execute.class);
								if (annotations != null && methods[i].getParameterTypes().length == 0){
									methods[i].invoke(MyObject, (Object[]) null);
								}
							}
						}
					}
				}catch (IOException e) {
					System.err.println("IOException");
					return;
				} catch (ClassNotFoundException e) {
					System.err.println("ClassNotFoundException");
					e.printStackTrace();
				}catch (IllegalAccessException e) {
					e.printStackTrace();
				}catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		};

		output.setDaemon(true); //Marked as a daemon thread(The Java Virtual Machine exits when the only threads running are all daemon threads) 
		output.start();

		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			while(true) {
				String from_user = r.readLine();
				if(from_user.startsWith("\\")){
					if(from_user.startsWith("\\nick")){
						String Nick = from_user.substring(6);
						ChangeNickMessage name = new ChangeNickMessage(Nick);
						out.writeObject(name);
					} else if(from_user.startsWith("\\quit")){
						System.out.println("[Client] Connection terminated.");
						return;
					} else{
						String [] command = from_user.split(" ");
						String unknown = command[0].substring(1);
						System.out.println(time() + " [Client] Unknown command " + "\"" + unknown + "\"");
					}
				} else {
					ChatMessage message = new ChatMessage(from_user);
					out.writeObject(message);
				}
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
