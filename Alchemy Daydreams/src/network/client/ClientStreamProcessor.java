package network.client;

import network.StreamInformation;

public class ClientStreamProcessor {

	Client client;
	
	public ClientStreamProcessor(Client client) {
		this.client = client;
	}
	
	public void readInStream(String data) {
		
		String[] dataSets = data.split(StreamInformation.deliminator);
		
		if (dataSets[0].equalsIgnoreCase(StreamInformation.chat)) {
			System.out.println("Recieved Chat instructions - Client");
			readChat(dataSets);
		}
		if (dataSets[0].equalsIgnoreCase(StreamInformation.object)) {
			System.out.println("Recieved Chat instructions - Client");
			readObject(dataSets);
		}
	}
	
	public void readChat(String[] dataSets) {
		
		String data = "";
		for (int i = 2; i < dataSets.length; i++) {
			data += dataSets[i];
		}
		client.setChatText(client.getChatText() + "[" + dataSets[1] + "]: " + data + "\n");
	}
	
	public void readObject(String[] dataSets) {
		
		if (dataSets[1].equalsIgnoreCase(StreamInformation.transform)) {
			
		} else if (dataSets[1].equalsIgnoreCase(StreamInformation.generate)) {
			
		}
	}
}
