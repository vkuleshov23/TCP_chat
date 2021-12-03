package src.exe;

import src.server.Server;
import src.utils.NETparam;

import java.io.IOException;

public class ServerExe {
	public static void main(String[] args) {
		try {
			System.out.println("MAIN");
			Server serv = new Server(NETparam.serverPort);
			serv.run();
			System.out.println("END MAIN");
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
}