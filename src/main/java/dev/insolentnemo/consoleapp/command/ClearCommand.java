package dev.insolentnemo.consoleapp.command;

import dev.insolentnemo.consoleapp.util.Command;
import dev.insolentnemo.consoleapp.util.ConsoleApp;
import dev.insolentnemo.consoleapp.util.Logger;

public class ClearCommand extends Command {

    public ClearCommand(ConsoleApp consoleApp) {
        super(consoleApp, "clear");
    }

    @Override
    protected void onCommand(String[] args) {
        if (args.length > 0) {
            Logger.usageError(this);
            return;
        }

        Logger.clearConsole();
    }

}
