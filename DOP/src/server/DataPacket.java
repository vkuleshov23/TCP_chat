package src.server;

public class DataPacket {
	private String command;
	private String msg;

	public DataPacket(String data) {
		command = parseCommand(data);
		msg = parseMSG(data);
	}

	private String parseCommand(String data) {
		if(data.charAt(0) == '@') {
			return data.substring(0, data.indexOf(' '));
		} else {
			return "";
		}
	}

	private String parseMSG(String data) {
		if(data.charAt(0) != '@') {
			return data;
		} else {
			return data.substring(data.indexOf(' ')+1);
		}
	}

	public String getCommand() {
		return command;
	}
	public String getMSG() {
		return msg;
	}
}