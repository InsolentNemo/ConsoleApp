package dev.insolentnemo.consoleapp.util;

import dev.insolentnemo.consoleapp.command.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler {

    private static ConsoleApp CONSOLE_APP;
    private static final Map<String, Command> COMMANDS = new HashMap<>();

    public static void initialize(ConsoleApp consoleApp) {
        CONSOLE_APP = consoleApp;
        initializeCommands();
    }

    private static void initializeCommands() {
        COMMANDS.put("help", new HelpCommand(CONSOLE_APP));
        COMMANDS.put("clear", new ClearCommand(CONSOLE_APP));
        COMMANDS.put("reload", new ReloadCommand(CONSOLE_APP));
        COMMANDS.put("plugin", new PluginCommand(CONSOLE_APP));
        COMMANDS.put("plugins", new PluginsCommand(CONSOLE_APP));
        COMMANDS.put("stop", new StopCommand(CONSOLE_APP));
    }

    public static void add(String label, Command command) {
        if (COMMANDS.containsKey(label)) {
            Logger.error("Command '" + label + "' does already exist.");
            return;
        }

        COMMANDS.put(label, command);
    }

    public static void remove(String label) {
        if (!COMMANDS.containsKey(label)) {
            Logger.error("Command '" + label + "' does not exist.");
            return;
        }

        COMMANDS.remove(label);
    }

    public static void reload() {
        COMMANDS.clear();
        initializeCommands();
    }

    public static void handle(String line) {
        final String[] lineArr = line.split(" ");
        final String commandStr = lineArr[0].toLowerCase();
        final Command command = COMMANDS.get(commandStr);

        if (command == null) {
            Logger.invalidCommand(commandStr);
            return;
        }

        final String[] args = Arrays.copyOfRange(lineArr, 1, lineArr.length);
        command.run(args);
    }

    public static List<Command> getCommands() {
        return new ArrayList<>(COMMANDS.values());
    }

}
