package src.server;

import src.utils.*;
import src.commands.*;
import src.interfaces.*;

import java.io.IOException;

import java.net.Socket;

public class ClientHandler implements Runnable {
	
	ClientBase clients;
	MessageHandler msgHandler;
	String myName;

	ClientHandler(ClientBase clientBase, Socket socket) {
		clients = clientBase;
		this.setRandomName();
		this.setMsgHandler(socket, clientBase);
	}

	private void setMsgHandler(Socket socket, ClientBase clientBase) {
		try {
			msgHandler = new MessageHandler(socket, myName, clientBase);
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			this.handling();
		} finally {
			if(!msgHandler.isQuit()) {
				msgHandler.closeSocket();
			}
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

	private void handling() {		
		try {
			msgHandler.setMe();
			msgHandler.sendToMe("Your name is: " + myName + '\n');

			System.out.println(myName + " join to client base");
			
			recieverLoop();
		} catch(IOException err) {
			err.getMessage();
			err.printStackTrace();
		} finally {
			msgHandler.deleteMe();

			System.out.println(myName + " left the server");
		}
	}

	private void recieverLoop() throws IOException {
		while(!msgHandler.isQuit()) {
			String data = msgHandler.getDataFromClient();
			DataPacket packet = new DataPacket(data);
			ICommand command = defineCommand(packet);
			command.execute();
		}
	}

	private ICommand defineCommand(DataPacket packet) {
		ICommand command;
		switch(packet.getCommand()) {
			case "@quit":
				command = new Quit(msgHandler);
				break;
			case "@sendUser":
				command = new SendUser(msgHandler, packet.getMSG());
				break;
			case "@name":
				command = new ChangeName(msgHandler, packet.getMSG());
				break;
			default:
				command = new SendAll(msgHandler, packet.getMSG());
				break;
		}
		return command;
	}
}