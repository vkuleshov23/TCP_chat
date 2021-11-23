package src.server;

import serc.utils.*;

import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class ClientHandler2 implements Runnable {
	
	ConcurrentHashMap<String, OutputStream> clients;

	Socket mySocket;
	OutputStream myOutStream;
	InputStream myInStream;
	String myName;

	boolean quit;

	ClientHandler2(ConcurrentHashMap<String, OutputStream> clientBase, Socket socket) {
		clients = clientBase;
		quit = false;
		this.setSoketAndStreams(socket);
	}

	private void setSoketAndStreams(Socket socket) {
		try {
			mySocket = socket;
			myOutStream = socket.getOutputStream();
			myInStream = socket.getInputStream();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
			socket.close();
			quit = true;
		}
	}

	@Override
	public void run() {
		if(quit) { return; }

		try {
			this.nameDefinition();
			this.handling();
		} finally {
			mySocket.close();
		}
	}

	private void nameDefinition() {
		myName = this.setRandomName();
		send("Accept your name: " + myName, myOutStream);
		if(!getDataFromClient().equalsIgnoredCase("yes")) {
			myName = tryDefineName();
		}			
	}

	private void handling() {
		clients.set(myName, myOutStream);
		try {	
			recieverLoop();
		} catch(IOException err) {
			err.getMessage();
			err.printStackTrace();
		} finally {
			clients.delete(myName);
		}
	}

	private void recieverLoop() {
		while(!mySocket.isClosed() || !this.quit) {
			String data = this.getDataFromClient();

		}
	}

	private String getDataFromClient() {
		byte[] receiveData = new byte[maxPacketLength + maxNameLength];
		try	{
			myInStream.read(receiveData);
			return (new String(receiveData, StandardCharsets.UTF_8)).trim();
		} catch (IOException e) {
			return "null";
		}
	}

	private void sendToAll(String data) {
		for (String name : clients.getNames()) {
			sendToName(name, data);
		}
	}

	private void sendToName(String name, String data) {
		try {
			OutputStream clientStream = clientBase.getOutputStream(name);
			send(String data, clientStream);
		} catch (NullPointerException err) {
			send(err.getMessage(), myOutStream);
		}	
	}

	private void send(String data, OutputStream stream) {
		byte[] sendData = new byte[maxPacketLength + maxNameLength];
		sendData = data.getBytes();
		out.write(sendData);
		out.flush();
	}

	private boolean tryChangeNameAndGetAnswer(String name) {
		String answer = "Name was changed";
		try {
			clients.rename(name);
		} catch (NullPointerException e) {
			answer = err.getMessage()
		} finally {
			send(answer, myOutStream);
		}
	}

	private void setRandomName() {
		while (true){
			String name = RandomString.random();
			if(!clients.isContain(name)) {
				this.myName = name;
				break;
			}
		}
	}

	private boolean msgIsName(String data) {
		return data.regionMatches(0, nameCommand, 0, nameCommand.length());
	}

	private String tryDefineName() {
		while(!socket.isClosed()) {
			send("Create name", myOutStream);
			String data = getDataFromClient();
			if(msgIsName(data)){
				String name = parseName(data);
				if(!clients.isContain()) {
					return name;
				}
			}
		}
		//Must be Rewrited;
		return "--";
	}

	private String parseName(String name) {
		String data = name.trim();
		if(data.length() != nameCommand.length()){
			data = data.substring(nameCommand.length()+1, data.length());
			data.trim();
			if(data.length() > maxNameLength){
				data = data.substring(0, maxNameLength);
				data.trim();
			}
			return data;
		}
		return myName;
	}
}