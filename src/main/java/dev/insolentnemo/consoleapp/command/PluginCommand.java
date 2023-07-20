package dev.insolentnemo.consoleapp.command;

import dev.insolentnemo.consoleapp.command.plugin.PluginDisableCommand;
import dev.insolentnemo.consoleapp.command.plugin.PluginReloadCommand;
import dev.insolentnemo.consoleapp.util.Command;
import dev.insolentnemo.consoleapp.util.ConsoleApp;

public class PluginCommand extends Command {

    public PluginCommand(ConsoleApp consoleApp) {
        super(consoleApp, "plugin");
    }

    @Override
    public void registerSubCommands() {
        final ConsoleApp consoleApp = getConsoleApp();
        addSubCommand("disable", new PluginDisableCommand(consoleApp));
        addSubCommand("reload", new PluginReloadCommand(consoleApp));
    }

}
