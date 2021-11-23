package src.server;

import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import java.net.Socket;

import src.utils.NETparam;

public class MessageHandler {

	private Socket mySocket;
	private OutputStream myOutStream;
	private InputStream myInStream;
	private String myName;
	private ClientBase clients;

	public MessageHandler(Socket clientSocket, String name, ClientBase clientBase) throws IOException {
		myOutStream = clientSocket.getOutputStream();
		myInStream = clientSocket.getInputStream();
		myName = name;
		mySocket = clientSocket;
		clients = clientBase;
	}

	public void setName(String name) {
		myName = name;
	}

	public String getName() {
		return myName;
	}

	public void setMe() throws IOException{

		if(!clients.set(myName, myOutStream)) {
			throw new IOException("such name is Already Exist");
		}
	}

	public void deleteMe() {
		clients.delete(myName);
	}

	public String getDataFromClient() {
		byte[] receiveData = new byte[NETparam.maxPacketLength + NETparam.maxNameLength];
		try	{
			myInStream.read(receiveData);
			return (new String(receiveData, StandardCharsets.UTF_8)).trim();
		} catch (IOException e) {
			return "null";
		}
	}

	public void sendToAll(String data) throws IOException{
		if(!this.alone()) {
			for (String name : clients.getNames()) {
				if(name != myName) {
					sendToName(name, myName + ": " + data);
				}
			}
		}
	}

	public void sendToName(String name, String data) throws IOException {
		try {
			if(!this.alone()) {
				OutputStream clientStream = clients.getOutputStream(name);
				this.send(data, clientStream);
			}
		} catch (NullPointerException err) {
			this.sendToMe(err.getMessage());
		}	
	}

	public void sendToMe(String data) throws IOException {
		this.send(data, myOutStream);
	}

	private void send(String data, OutputStream out) throws IOException {
		byte[] sendData = new byte[NETparam.maxPacketLength + NETparam.maxNameLength];
		sendData = data.getBytes();
		out.write(sendData);
		out.flush();
	}

	private boolean alone() throws IOException {
		if(clients.isAlone()){
			sendToMe("You are alone");
			return true;
		}
		return false;
	}

	public boolean tryRename(String name) throws IOException {
		try {
			clients.rename(myName, name);
			myName = name;
			return true;
		} catch (NullPointerException err) {
			this.sendToMe(err.getMessage());
		}
		return false;
	}

	public void closeSocket() {
		try {
			mySocket.close();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	public boolean isQuit() {
		return mySocket.isClosed();
	}
}