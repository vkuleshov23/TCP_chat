package src.server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;

import src.utils.NETparam;

public class Sender {
	
	private OutputStream outStream;
	private HashSet<OutputStream> ignoreList;

	public Sender(OutputStream outStream) {
		this.outStream = outStream;
		this.ignoreList = new HashSet<OutputStream>();
	}

	public OutputStream getStream() {
		return outStream;
	}

	public void sendToMe(String data) throws IOException {
		this.trueSend(data, outStream);
	}

	public void addToIL(Sender sender) {
		ignoreList.add(sender.getStream());
	}

	public void delFromIL(Sender sender) {
		ignoreList.remove(sender.getStream());
	}

	public void trueSend(String data, OutputStream out) throws IOException {
		byte[] sendData = new byte[NETparam.maxPacketLength + NETparam.maxNameLength];
		sendData = data.getBytes();
		out.write(sendData);
		out.flush();
	}

	public void send(String data, Sender out) throws IOException {
		if(!ignoreList.contains(out.getStream())){
			trueSend(data, out.getStream());
		}
	}

	@Override
	public boolean equals(Object o) {
		if(this == o){
			return true;
		} else if(getClass() != o.getClass()) {
			return false;
		} else {
			Sender sender = (Sender)o;
			return this.getStream() == sender.getStream();
		}
	}

	@Override
	public int hashCode() {
		return outStream.hashCode();
	}
}
