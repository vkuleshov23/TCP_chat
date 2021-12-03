package src.commands;

import src.interfaces.ICommand;
import src.server.MessageHandler;

import java.io.IOException;
import java.lang.NullPointerException;

public class AddToIgnoreList implements ICommand {

	MessageHandler msgHandler;
	String name;

	public AddToIgnoreList(MessageHandler handler, String newName) {
		msgHandler = handler;
		name = newName;
	}

	@Override
	public void execute() throws IOException {
		try {
			msgHandler.addToIgnore(name);
			System.out.println("\t" + msgHandler.getName() + " begin ignore " + name);
		} catch (NullPointerException e) {
			msgHandler.sendToMe(e.getMessage());
		}
	}
}