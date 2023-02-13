package dev.insolentnemo.consoleapp.input.commands;

import dev.insolentnemo.consoleapp.input.Command;
import dev.insolentnemo.consoleapp.utils.ConsoleApp;
import dev.insolentnemo.consoleapp.utils.Logger;

public class ClearCommand extends Command {

    public ClearCommand(ConsoleApp consoleApp) {
        super(consoleApp, "clear");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length > 0) {
            Logger.usageError(this);
            return;
        }

        Logger.clearConsole();
    }

    @Override
    public void registerSubCommands() {

    }

}
