package src.commands;

import src.interfaces.ICommand;
import src.server.MessageHandler;

import java.io.IOException;
import java.lang.NullPointerException;

public class DeleteFromBlackList implements ICommand {

	MessageHandler msgHandler;
	String name;

	public DeleteFromBlackList(MessageHandler handler, String newName) {
		msgHandler = handler;
		name = newName;
	}

	@Override
	public void execute() throws IOException {
		try {
			msgHandler.stopIgnore(name);
			msgHandler.deleteFromBlackList(name);
			System.out.println("\t" + msgHandler.getName() + " stop ignoring " + name);
		} catch (NullPointerException e) {
			msgHandler.sendToMe(e.getMessage());
		}
	}
}