package network.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import network.StreamInformation;
import settings.ServerSettings;


public class Server implements Runnable{
	
	ConnectedClient[] clients;
	boolean activeServer = false;
	ServerSocket serverSocket;
	Thread thread;
	public ServerStreamProcessor streamProcessor;
	
	public Server() {
		
		openServer();
		streamProcessor = new ServerStreamProcessor(this);
		thread = new Thread(this, "Server");
		thread.start();
	}
	
	public void openServer() {
		try {
			serverSocket = new ServerSocket(ServerSettings.PORT);
			clients = new ConnectedClient[ServerSettings.CLIENTS];
			System.out.println("SERVER OPEN");
		} catch (IOException e) {
			System.out.println("FAILED TO BIND TO PORT, CHECK THAT ANOTHER SERVER IS NOT OPEN");
			e.printStackTrace();
		}
	}
	
	public void acceptConnection(Socket socket, int slot) {
		try {
			DataInputStream inStream = new DataInputStream(socket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
			String user = inStream.readUTF();
			System.out.println("Client \"" + user + "\" connected");
			
			ConnectedClient client = new ConnectedClient(user, slot, socket, inStream, outStream, this);
			clients[slot] = client;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int connections = 0;
		for (int i = 0; i < ServerSettings.CLIENTS; i++) {
			if (clients[i] != null) {
				connections++;
			}
		}
		System.out.println("" + connections);
	}
	public void removeClient(int position) {
		clients[position] = null;
	}
	public void dropClient(int position) {
		clients[position].terminate();
	}
	
	public void writeToClient(int position, String data) {
		clients[position].writeOutStream(data);
	}
	public void writeToAll(String data) {
		for (int i = 0; i < ServerSettings.CLIENTS; i++) {
			if (clients[i] != null) {
				clients[i].writeOutStream(data);
			}
		}
	}
	
	
	@Override
	public void run() {
		activeServer = true;
		while (activeServer) {
			try {
				boolean lock = true;
				
				Socket socket = serverSocket.accept();
				for (int i = 0; i < ServerSettings.CLIENTS; i++) {
					if (clients[i] == null) {
						acceptConnection(socket, i);
						lock = false;
						break;
					}
				}
				if (lock) {
					System.out.println("Failed to accept a new Client connection: No empty slot or Invalid connection ID");
					socket.close();
				}
			} catch (Exception e) {
				System.out.println("FALSE EVENT, SERVER CRASHED!");
				activeServer = false;
			}
		}
	}
	
	public void terminate() {
		activeServer = false;
		for (ConnectedClient client : clients) {
			client.terminate();
		}
	}
}

class ConnectedClient implements Runnable{
	
	private String username;
	private int slot;
	private boolean connected = false;
	private String receiveData = "";
	
	private Socket socket;
	private DataInputStream inStream;
	private DataOutputStream outStream;
	private Server server;
	
	private Thread thread;
	
	public ConnectedClient(String username, int slot, Socket socket, DataInputStream inStream, DataOutputStream outStream, Server server) throws IOException {
		this.username = username;
		this.slot = slot;
		
		this.socket = socket;
		this.inStream = inStream;
		this.outStream = outStream;
		
		this.server = server;
		
		thread = new Thread(this, "Server Client");
		thread.start();
	}
	
	public void writeOutStream(String data) {
		try {
			outStream.writeUTF(data);
		} catch (IOException e) {
			System.out.println("FAILED TO WRITE OUT DATA: " + data);
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		connected = true;
		System.out.println("Running");
		writeOutStream(StreamInformation.chat + StreamInformation.deliminator + username + StreamInformation.deliminator + "Joined the Server");
		
		while (connected) {
			receiveData = "";
			try {
				receiveData = inStream.readUTF();
				System.out.println("I HAVE RECEIVED DATA");
				server.streamProcessor.readInStream(username, receiveData);
			} catch (Exception e) {
				e.printStackTrace();
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
		server.removeClient(slot);
	}
}
