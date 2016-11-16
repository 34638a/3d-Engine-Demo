package network.server;

import network.StreamInformation;

public class ServerStreamProcessor {
	
	Server server;
	
	public ServerStreamProcessor(Server server) {
		this.server = server;
	}
	
	public void readInStream(String username, String data) {
		
		String[] dataSets = data.split(StreamInformation.deliminator);
		//System.out.println("Command " + dataSets[0]);
		
		if (dataSets[0].equalsIgnoreCase(StreamInformation.chat)) {
			System.out.println("Recieved Chat instructions - Server");
			readChat(username, dataSets);
		}
	}
	
	public void readChat(String username, String[] dataSets) {
		
		String data = "";
		for (int i = 1; i < dataSets.length; i++) {
			data += dataSets[i];
		}
		
		server.writeToAll(StreamInformation.chat + StreamInformation.deliminator + username + StreamInformation.deliminator + data);
	}
}
