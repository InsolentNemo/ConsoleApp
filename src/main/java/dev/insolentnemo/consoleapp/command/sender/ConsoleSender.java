package dev.insolentnemo.consoleapp.command.sender;

import dev.insolentnemo.consoleapp.command.CommandSender;
import dev.insolentnemo.consoleapp.util.Logger;

public class ConsoleSender extends CommandSender {

    @Override
    public void sendMessage(String text) {
        Logger.println(text);
    }

    @Override
    public void sendError(String error) {
        Logger.error(error);
    }

}
