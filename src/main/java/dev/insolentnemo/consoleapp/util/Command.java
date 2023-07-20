package dev.insolentnemo.consoleapp.util;

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

    public void run(String[] args) {
        if (subCommands.size() == 0) {
            onCommand(args);
            return;
        }

        if (args.length == 0) {
            Logger.usageError(this);
            return;
        }

        final String subCommandStr = args[0];
        final Command subCommand = subCommands.get(subCommandStr);

        if (subCommand == null) {
            Logger.invalidCommand(subCommandStr);
            return;
        }

        final String[] subCommandArgs = Arrays.copyOfRange(args, 1, args.length);
        subCommand.run(subCommandArgs);
    }

    protected void onCommand(String[] args) { }

    protected void registerSubCommands() { }

    protected ConsoleApp getConsoleApp() {
        return consoleApp;
    }

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

}
