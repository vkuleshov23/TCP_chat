package src.server;

import java.lang.NullPointerException;
import java.io.IOException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

import java.io.OutputStream;

public class ClientBase {
	
	private static ConcurrentHashMap<String, OutputStream> clients;

	ClientBase() {
	clients = new ConcurrentHashMap<String, OutputStream>();
		// this.set(name, out);
	}

	public OutputStream getOutputStream(String name) {
		OutputStream out = clients.get(name);
		if (out == null) {
			throw new NullPointerException("No such Client in Base");
		}
		return out;
	}

	public boolean set(String name, OutputStream out) {
		if(this.isContain(name)) {
			return false;
		} else {
			clients.put(name, out);
			return true;
		}
	}

	public Collection<String> getNames() {
		return clients.keySet();
	}

	public void delete(String name) {
		clients.remove(name);
	}

	public void rename(String oldName, String newName) {
		if (this.isContain(newName)) {
			throw new NullPointerException("This Name is Already Exist");
		}
		OutputStream out = clients.remove(oldName);
		clients.put(newName, out);
	}

	public void disableAll() {
		for (OutputStream stream : clients.values()) {
			close(stream);
		}
	}

	private void close(OutputStream stream) {
		try{
			stream.close();
			System.out.println(stream + " closed");
		} catch(IOException e) {

		}
	}

	public boolean isAlone() {
		if(clients.size() <= 1) {
			return true;
		}
		return false;
	}

	public boolean isContain(String name) {
		return clients.containsKey(name);
	}
}