package network.client;

import graphics.windows.ChatReceiver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import settings.ServerSettings;


public class Client implements Runnable{
	
	String username = "NULL";
	ChatReceiver chatBox;
	
	Socket socket;
	DataInputStream inStream;
	DataOutputStream outStream;
	boolean connected = false;
	String receiveData;
	Thread thread;
	ClientStreamProcessor streamProcessor;
	
	
	public Client(String username) {
		this.username = username;
		chatBox = new ChatReceiver(this);
		streamProcessor = new ClientStreamProcessor(this);
		connectToServer("",0);
	}
	
	public String getChatText() {
		return chatBox.txtrChat.getText();
	}
	
	public void setChatText(String data) {
		chatBox.txtrChat.setText(data);
	}
	
	public void connectToServer(String ip, int port) {
		try {
			socket = new Socket("121.208.74.5", ServerSettings.PORT);
			
			inStream = new DataInputStream(socket.getInputStream());
			outStream = new DataOutputStream(socket.getOutputStream());
			
			writeOutStream(username);
			
			thread = new Thread(this, "Client");
			thread.start();
			
		} catch (UnknownHostException e) {
			System.out.println("UNKNOWN HOST, PLEASE CHECK YOUR IP AND PORT THAT YOU ARE CONNECTING TO");
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("UNABLE TO CONNECT!");
			e.printStackTrace();
		}
	}
	
	public void writeOutStream(String data) {
		try {
			System.out.println("Sending Data: " + data);
			outStream.writeUTF(data);
		} catch (IOException e) {
			System.out.println("FAILED TO WRITE OUT DATA: " + data);
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		connected = true;
		while (connected) {
			receiveData = "";
			try {
				receiveData = inStream.readUTF();
				streamProcessor.readInStream(receiveData);
			} catch (Exception e) {
				System.out.println("Client disconnection from server");
				terminate();
			}
		}
	}
	
	public void terminate() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		connected = false;
	}
}
