package src.server;

import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	
	private ServerSocket socket;
	private ExecutorService executor;
	private ClientBase activeClients;

	public Server(int port) throws IOException {
		this.socket = new ServerSocket(port);
		this.activeClients = new ClientBase();
		this.executor = Executors.newCachedThreadPool();
	}

	public void run() throws IOException {
		try {

			System.out.println("\tSERVER BEGIN");
			
			this.getClientLoop();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		} finally {

			System.out.println("\tSERVER END");
			
			activeClients.disableAll();
			executor.shutdown();
		}
	}

	private void getClientLoop() throws IOException {
		while(true) {
			Socket clientSocket = socket.accept();
			ClientHandler newClient = new ClientHandler(activeClients, clientSocket);
			executor.submit(newClient);
		}
	}
}