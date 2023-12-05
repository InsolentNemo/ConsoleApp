package dev.insolentnemo.consoleapp.command.essential;

import dev.insolentnemo.consoleapp.command.Command;
import dev.insolentnemo.consoleapp.command.CommandSender;
import dev.insolentnemo.consoleapp.util.ConsoleApp;
import dev.insolentnemo.consoleapp.util.Logger;

public class ClearCommand extends Command {

    public ClearCommand(ConsoleApp consoleApp) {
        super(consoleApp, "clear");
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        if (args.length > 0) {
            final String usage = getUsage();
            sender.sendUsageError(usage);
            return;
        }

        Logger.clearConsole();
    }

}
