package src.server;

import java.io.IOException;
import java.net.Socket;

public class MessageHandler {

	private String myName;
	private ClientBase clients;
	private ClientStream stream;

	public MessageHandler(Socket clientSocket, String name, ClientBase clientBase) throws IOException {
		myName = name;
		clients = clientBase;
		stream = new ClientStream(clientSocket);
		BlackList.addNewClient(stream);
	}

	public void setName(String name) {
		myName = name;
	}

	public String getName() {
		return myName;
	}

	public void setMeToBase() throws IOException{
		if(!clients.set(myName, stream)) {
			throw new IOException("[x] Such name is Already Exist");
		}
	}	

	public void deleteMeFromAll() {
		clients.delete(myName);
		BlackList.deleteClient(stream);
	}

	public String getDataFromClient() {
		return stream.getData();
	}

	public void sendToAll(String data) throws IOException {
		if(!this.alone()) {
			for (String name : clients.getNames()) {
				if(name != myName) {
					this.sendToName(name, myName + ": " + data);
				}
			}
		}
	}

	public void sendToMe(String data) throws IOException {
		stream.sendToMe(data);
	}

	public void sendToName(String name, String data) throws IOException {
		try {
			if(!this.alone()) {
				ClientStream recipient = clients.getClientStream(name);
				if(!BlackList.isBlockMe(stream, recipient)){
					stream.send(data, recipient);
				}
			}
		} catch (NullPointerException err) {
			stream.sendToMe(err.getMessage());
		}	
	}

	private boolean alone() throws IOException {
		if(clients.isAlone()){
			stream.sendToMe("[!] You are alone");
			return true;
		}
		return false;
	}

	public void addToIgnore(String name) throws NullPointerException {
		stream.addToIgnoreList(clients.getClientStream(name));
	}

	public void stopIgnore(String name) throws NullPointerException {
		stream.deleteFromIgnoreList(clients.getClientStream(name));
	}
	public void addToBlackList(String name) {
		BlackList.moveToBlackList(stream, clients.getClientStream(name));
	}

	public void deleteFromBlackList(String name) {
		BlackList.deleteFromBlackList(stream, clients.getClientStream(name));
	}

	public boolean tryRename(String name) throws IOException {
		try {
			clients.rename(myName, name);
			myName = name;
			return true;
		} catch (NullPointerException err) {
			stream.sendToMe(err.getMessage());
		}
		return false;
	}

	public void closeSocket() {
		stream.close();
	}

	public boolean isQuit() {
		return stream.isClosed();
	}
}