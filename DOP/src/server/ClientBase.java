package src.server;

import java.lang.NullPointerException;
import java.io.IOException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class ClientBase {
	
	private static ConcurrentHashMap<String, ClientStream> clients;

	ClientBase() {
		clients = new ConcurrentHashMap<String, ClientStream>();
	}

	public ClientStream getClientStream(String name) {
		ClientStream out = clients.get(name);
		if (out == null) {
			throw new NullPointerException("[x] No such Client in Base");
		}
		return out;
	}

	public boolean set(String name, ClientStream out) {
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
			throw new NullPointerException("[x] This Name is Already Exist");
		}
		ClientStream out = clients.remove(oldName);
		clients.put(newName, out);
	}

	public void disableAll() {
		for (ClientStream stream : clients.values()) {
			stream.close();
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