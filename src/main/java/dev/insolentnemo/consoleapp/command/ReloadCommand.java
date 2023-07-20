package dev.insolentnemo.consoleapp.command;

import dev.insolentnemo.consoleapp.util.Command;
import dev.insolentnemo.consoleapp.util.ConsoleApp;
import dev.insolentnemo.consoleapp.util.Logger;

public class ReloadCommand extends Command {

    public ReloadCommand(ConsoleApp consoleApp) {
        super(consoleApp, "reload");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length > 0) {
            Logger.usageError(this);
            return;
        }

        final ConsoleApp consoleApp = getConsoleApp();
        consoleApp.reload();
    }

}
