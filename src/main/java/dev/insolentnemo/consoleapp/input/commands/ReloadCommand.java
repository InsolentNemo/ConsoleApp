package dev.insolentnemo.consoleapp.input.commands;

import dev.insolentnemo.consoleapp.input.Command;
import dev.insolentnemo.consoleapp.utils.CommandHandler;
import dev.insolentnemo.consoleapp.utils.Logger;
import dev.insolentnemo.consoleapp.utils.PluginManager;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super("reload");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length > 0) {
            Logger.usageError(this);
            return;
        }

        reload();
    }

    @Override
    public void registerSubCommands() {

    }

    private void reload() {
        Logger.println("Reloading ...");
        CommandHandler.reload();
        PluginManager.reload();
        Logger.println("Reload completed.");
    }
}
