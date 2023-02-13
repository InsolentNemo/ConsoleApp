package dev.insolentnemo.consoleapp.utils;

import dev.insolentnemo.consoleapp.input.Command;
import dev.insolentnemo.consoleapp.input.commands.ClearCommand;
import dev.insolentnemo.consoleapp.input.commands.HelpCommand;
import dev.insolentnemo.consoleapp.input.commands.ReloadCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandHandler {

    private static final HashMap<String, Command> commands = new HashMap<>();
    private static ConsoleApp consoleApp;

    public static void initialize(ConsoleApp consoleApp) {
        CommandHandler.consoleApp = consoleApp;
        initializeCommands();
    }

    private static void initializeCommands() {
        add("help", new HelpCommand());
        add("clear", new ClearCommand());
        add("reload", new ReloadCommand());
    }

    public static void add(String label, Command command) {
        if (commands.containsKey(label)) {
            Logger.error("Command '" + label + "' does already exist.");
            return;
        }

        commands.put(label, command);
    }

    public static void reload() {
        commands.clear();
        initializeCommands();
        consoleApp.addCommands();
    }

    public static void handle(String line) {
        final String[] lineArray = line.split(" ");
        final String commandStr = lineArray[0].toLowerCase();
        final Command command = commands.get(commandStr);

        if (command == null) {
            Logger.invalidCommand(commandStr);
            return;
        }

        final String[] args = Arrays.copyOfRange(lineArray, 1, lineArray.length);
        command.run(args);
    }

    public static List<Command> getCommands() {
        return new ArrayList<>(commands.values());
    }
}
