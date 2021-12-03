package src.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.HashSet;
import java.lang.NullPointerException;

public class BlackList {
	
	public static ConcurrentHashMap<ClientStream, HashSet<ClientStream>> blackList  = new ConcurrentHashMap<ClientStream, HashSet<ClientStream>>();

	public static boolean addNewClient(ClientStream client) {
		if(!isContain(client)) {
			blackList.put(client, new HashSet<ClientStream>());
			return true;
		} else {
			return false;
		}
	}

	public static void moveToBlackList(ClientStream me, ClientStream prisoner) throws NullPointerException {
		if(isContain(me)) {
			HashSet<ClientStream> myPrisoners = getSet(me);
			myPrisoners.add(prisoner);
		} else {
			throw new NullPointerException("No Such Client Exist!");
		}
	}

	public static void deleteFromBlackList(ClientStream me, ClientStream prisoner) throws NullPointerException  {
		if(isContain(me)) {
			HashSet<ClientStream> myPrisoners = getSet(me);
			myPrisoners.remove(prisoner);
		} else {
			throw new NullPointerException("No Such Client Exist!");
		}
	}

	public static boolean isBlockMe(ClientStream me, ClientStream recipient) {
		HashSet<ClientStream> recipientBlackList = getSet(recipient);
		return recipientBlackList.contains(me);
	}

	public static void deleteClient(ClientStream client) {
		blackList.remove(client);
	}

	public static boolean isContain(ClientStream client) {
		return blackList.containsKey(client);
	}

	public static HashSet<ClientStream> getSet(ClientStream me) {
		return blackList.get(me);
	}
}