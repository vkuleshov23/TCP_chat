package src.commands;

import src.interfaces.ICommand;
import src.server.MessageHandler;

import java.io.IOException;

public class ChangeName implements ICommand {

	MessageHandler msgHandler;
	String name;

	public ChangeName(MessageHandler handler, String newName) {
		msgHandler = handler;
		name = newName;
	}

	@Override
	public void execute() throws IOException {
		String oldName = msgHandler.getName();
		if(msgHandler.tryRename(name)) {
			msgHandler.sendToMe("Your new Name is '" + name + "'");
			System.out.println("\t" + oldName + " changed name to " + name);
		}
	}
}