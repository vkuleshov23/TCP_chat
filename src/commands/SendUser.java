package src.commands;

import src.interfaces.ICommand;
import src.server.MessageHandler;

import java.io.IOException;
import java.lang.IndexOutOfBoundsException;

public class SendUser implements ICommand {

	MessageHandler msgHandler;
	String data;

	public SendUser(MessageHandler handler, String clData) {
		msgHandler = handler;
		data = clData;
	}

	@Override
	public void execute() throws IOException {
		try {
			String name = data.substring(0, data.indexOf(' '));
			String msg = data.substring(data.indexOf(' ')+1);

			String data = msgHandler.getName() + " whispers to you: " + msg; 
		
			msgHandler.sendToName(name, data);
		
			System.out.println("\t" + msgHandler.getName() +  " send => " + name + ": " + msg);
		} catch (IndexOutOfBoundsException e) {
			msgHandler.sendToMe("Incorrect command");
		}
	}
}