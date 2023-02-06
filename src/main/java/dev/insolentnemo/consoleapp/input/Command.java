package dev.insolentnemo.consoleapp.input;

import dev.insolentnemo.consoleapp.utils.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class Command {

    private String usage;
    private final Map<String, Command> subCommands = new HashMap<>();

    public Command(String usage) {
        this.usage = usage;
        registerSubCommands();
    }

    public void runCommand(String[] args) {
        if (subCommands.size() == 0) {
            onCommand(args);
            return;
        }

        if (args.length == 0) {
            Logger.usageError(this);
            return;
        }

        final String subCommandString = args[0];
        final Command subCommand = subCommands.get(subCommandString);

        if (subCommand == null) {
            Logger.invalidCommand(subCommandString);
            return;
        }

        final String[] subCommandArgs = Arrays.copyOfRange(args, 1, args.length);
        subCommand.runCommand(subCommandArgs);
    }

    public abstract void onCommand(String[] args);

    public abstract void registerSubCommands();

    public void addSubCommand(String label, Command subCommand) {
        if (subCommands.containsKey(label)) throw new RuntimeException("Subcommand named " + label + " already exists");

        subCommands.put(label, subCommand);
    }

    public String getUsage() {
        if (subCommands.keySet().isEmpty()) return usage;

        final String[] subCommandsArray = this.subCommands.keySet().toArray(new String[]{});
        final StringBuilder subCommandsStr = new StringBuilder();
        final String fistSubCommandStr = subCommandsArray[0];
        subCommandsStr.append(fistSubCommandStr);

        for (int i = 1; i < subCommandsArray.length; i++) {
            subCommandsStr.append("/");
            subCommandsStr.append(subCommandsArray[i]);
        }

        return usage + " <" + subCommandsStr + ">";
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

}