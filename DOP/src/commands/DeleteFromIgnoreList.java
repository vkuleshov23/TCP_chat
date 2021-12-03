package src.commands;

import src.interfaces.ICommand;
import src.server.MessageHandler;

import java.io.IOException;
import java.lang.NullPointerException;

public class DeleteFromIgnoreList implements ICommand {

	MessageHandler msgHandler;
	String name;

	public DeleteFromIgnoreList(MessageHandler handler, String newName) {
		msgHandler = handler;
		name = newName;
	}

	@Override
	public void execute() throws IOException {
		try {
			msgHandler.stopIgnore(name);
			System.out.println("\t" + msgHandler.getName() + " stop ignoring " + name);
		} catch (NullPointerException e) {
			msgHandler.sendToMe(e.getMessage());
		}
	}
}