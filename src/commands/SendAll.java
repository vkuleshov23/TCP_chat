package src.commands;

import src.interfaces.ICommand;
import src.server.MessageHandler;

import java.io.IOException;

public class SendAll implements ICommand {

	MessageHandler msgHandler;
	String msg;

	public SendAll(MessageHandler handler, String data) {
		msgHandler = handler;
		msg = data;
	}

	@Override
	public void execute() throws IOException {
		msgHandler.sendToAll(msg);
		System.out.println("\t" + msgHandler.getName() +  " => All: " + msg);
	}
}