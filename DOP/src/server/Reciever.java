package src.server;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
// import java.util.HashSet;

import src.utils.NETparam;

public class Reciever {

	private InputStream inStream;
	// private HashSet<String> blackList;

	public Reciever(InputStream inStream) {
		this.inStream = inStream;
		// this.ignoreList = new HashSet<String>();
	}

	public String getData() {
		byte[] receiveData = new byte[NETparam.maxPacketLength + NETparam.maxNameLength];
		try	{
			inStream.read(receiveData);
		} catch (IOException e) {
			return "null";
		}
		return (new String(receiveData, StandardCharsets.UTF_8)).trim();
	}
}