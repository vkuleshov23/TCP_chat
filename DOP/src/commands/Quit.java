package src.commands;

import src.interfaces.ICommand;
import src.server.MessageHandler;

import java.io.IOException;

public class Quit implements ICommand {
	MessageHandler msgHandler;

	public Quit(MessageHandler handler) {
		msgHandler = handler;
	}

	@Override
	public void execute() throws IOException {
		msgHandler.closeSocket();
	}
}