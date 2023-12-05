package dev.insolentnemo.consoleapp.command;

import dev.insolentnemo.consoleapp.util.ConsoleApp;
import dev.insolentnemo.consoleapp.util.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Command {

    private final ConsoleApp consoleApp;
    private final String usage;
    private final Map<String, Command> subCommands = new HashMap<>();

    public Command(ConsoleApp consoleApp, String usage) {
        this.consoleApp = consoleApp;
        this.usage = usage;
        registerSubCommands();
    }

    public void run(CommandSender sender, String[] args) {
        if (subCommands.size() == 0) {
            onCommand(sender, args);
            return;
        }

        if (args.length == 0) {
            final String usage = getUsage();
            sender.sendUsageError(usage);
            return;
        }

        final String subCommandStr = args[0];
        final Command subCommand = subCommands.get(subCommandStr);

        if (subCommand == null) {
            sender.sendInvalidCommandError(subCommandStr);
            return;
        }

        final String[] subCommandArgs = Arrays.copyOfRange(args, 1, args.length);
        subCommand.run(sender, subCommandArgs);
    }

    protected void onCommand(CommandSender sender, String[] args) { }

    protected void registerSubCommands() { }

    protected void addSubCommand(String label, Command subCommand) {
        if (subCommands.containsKey(label)) return;
        subCommands.put(label, subCommand);
    }

    public String getUsage() {
        if (subCommands.keySet().isEmpty()) return usage;

        final String[] subCommandsArr = this.subCommands.keySet().toArray(new String[]{});
        final StringBuilder subCommandsStr = new StringBuilder();
        final String fistSubCommandStr = subCommandsArr[0];
        subCommandsStr.append(fistSubCommandStr);

        for (int i = 1; i < subCommandsArr.length; i++) {
            subCommandsStr.append("/");
            subCommandsStr.append(subCommandsArr[i]);
        }

        return usage + " <" + subCommandsStr + ">";
    }

    protected ConsoleApp getConsoleApp() {
        return consoleApp;
    }

    public Map<String, Command> getSubCommands() {
        return subCommands;
    }
}
