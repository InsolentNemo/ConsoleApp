package dev.insolentnemo.consoleapp.command;

public abstract class CommandSender {

    public abstract void sendMessage(String line);

    public abstract void sendError(String error);

    public void sendInvalidCommandError(String command) {
        sendError("Command '" + command + "' does not exist.");
    }

    public void sendUsageError(String usage) {
        sendError("Usage: " + usage);
    }

    public void sendArgumentError(String arg) {
        sendError("Invalid argument '" + arg + "'.");
    }

    public void sendMessage(String[] lines)  {
        String linesStr = lines[0];
        for (int i = 1; i < lines.length; i++) linesStr += "\n" + lines[i];
        sendMessage(linesStr);
    }

}
