package dev.insolentnemo.consoleapp.command;

import dev.insolentnemo.consoleapp.util.Command;
import dev.insolentnemo.consoleapp.util.ConsoleApp;
import dev.insolentnemo.consoleapp.util.Logger;

public class StopCommand extends Command {
    public StopCommand(ConsoleApp consoleApp) {
        super(consoleApp, "stop");
    }

    @Override
    protected void onCommand(String[] args) {
        if (args.length > 0) {
            Logger.usageError(this);
            return;
        }

        final ConsoleApp consoleApp = getConsoleApp();
        consoleApp.stop();
    }

}
