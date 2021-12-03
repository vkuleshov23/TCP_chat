package src.server;

import java.io.IOException;
import java.net.Socket;

public class ClientStream {
	private Socket socket;
	private Sender sender;
	private Reciever reciever;

	public ClientStream(Socket clientSocket) throws IOException {
		socket = clientSocket;
		sender = new Sender(clientSocket.getOutputStream());
		reciever = new Reciever(clientSocket.getInputStream());
		System.out.println(socket + " is available");
	}

	public String getData() {
		return reciever.getData();
	}

	public void sendToMe(String data) throws IOException {
		sender.sendToMe(data);
	}

	public void send(String data, ClientStream stream) throws IOException {
			sender.send(data, stream.sender);
	}

	public void addToIgnoreList(ClientStream stream) {
		sender.addToIL(stream.sender);
	}

	public void deleteFromIgnoreList(ClientStream stream) {
		sender.delFromIL(stream.sender);
	}

	public void close() {
		try{
			socket.close();
			System.out.println(socket + " closed");
		} catch(IOException e) {
			System.out.println(e.getMessage());
			System.out.println("OutputStream Closing Error");
		}
	}

	public boolean isClosed() {
		return socket.isClosed();
	}

	@Override
	public boolean equals(Object o) {
		if(this == o){
			return true;
		} else if(getClass() != o.getClass()) {
			return false;
		} else {
			ClientStream stream = (ClientStream)o;
			return this.sender == stream.sender;
		}
	}

	@Override
	public int hashCode() {
		return this.sender.hashCode();
	}
}