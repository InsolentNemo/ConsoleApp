package dev.insolentnemo.consoleapp.command.essential;

import dev.insolentnemo.consoleapp.command.Command;
import dev.insolentnemo.consoleapp.command.CommandSender;
import dev.insolentnemo.consoleapp.util.ConsoleApp;

public class StopCommand extends Command {
    public StopCommand(ConsoleApp consoleApp) {
        super(consoleApp, "stop");
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        if (args.length > 0) {
            final String usage = getUsage();
            sender.sendUsageError(usage);
            return;
        }

        final ConsoleApp consoleApp = getConsoleApp();
        consoleApp.stop();
    }

}
