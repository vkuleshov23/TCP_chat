package src.server;

import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.ServerSocket;
import java.net.Socket;

// import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	
	private ServerSocket socket;
	private ExecutorService executor;

	private ClientBase activeClients;

	// public Server(String port) throws IOException {
	// 	this(Integer.parseInt(port));
	// }

	public Server(int port) throws IOException {
		socket = new ServerSocket(port);
		activeClients = new ClientBase();
		executor = Executors.newCachedThreadPool();
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