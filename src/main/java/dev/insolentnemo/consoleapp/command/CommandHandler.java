package dev.insolentnemo.consoleapp.command;

import dev.insolentnemo.consoleapp.util.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private final Map<String, Command> commands = new HashMap<>();

    public CommandHandler() {
        initializeCommands();
    }

    public void add(String label, Command command) {
        if (commands.containsKey(label)) {
            Logger.error("Command '" + label + "' does already exist.");
            return;
        }

        commands.put(label, command);
    }

    public void remove(String label) {
        if (!commands.containsKey(label)) {
            Logger.error("Command '" + label + "' does not exist.");
            return;
        }

        commands.remove(label);
    }

    public void clear() {
        commands.clear();
    }

    public void handle(CommandSender sender, String line) {
        final String[] lineArr = line.split(" ");
        final String commandStr = lineArr[0].toLowerCase();
        final Command command = commands.get(commandStr);

        if (command == null) {
            sender.sendInvalidCommandError(commandStr);
            return;
        }

        final String[] args = Arrays.copyOfRange(lineArr, 1, lineArr.length);
        command.run(sender, args);
    }

    protected void initializeCommands() { }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
