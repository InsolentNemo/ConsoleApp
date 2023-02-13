package dev.insolentnemo.consoleapp.input.commands;

import dev.insolentnemo.consoleapp.input.Command;
import dev.insolentnemo.consoleapp.utils.ConsoleApp;
import dev.insolentnemo.consoleapp.utils.Logger;

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

        getConsoleApp().reload();
    }

    @Override
    public void registerSubCommands() {

    }

}
