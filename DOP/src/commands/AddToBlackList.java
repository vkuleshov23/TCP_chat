package src.commands;

import src.interfaces.ICommand;
import src.server.MessageHandler;

import java.io.IOException;
import java.lang.NullPointerException;

public class AddToBlackList implements ICommand {

	MessageHandler msgHandler;
	String name;

	public AddToBlackList(MessageHandler handler, String newName) {
		msgHandler = handler;
		name = newName;
	}

	@Override
	public void execute() throws IOException {
		try {
			msgHandler.addToIgnore(name);
			msgHandler.addToBlackList(name);
			System.out.println("\t" + msgHandler.getName() + " throw  " + name + " into BlackList...");
		} catch (NullPointerException e) {
			msgHandler.sendToMe(e.getMessage());
		}
	}
}