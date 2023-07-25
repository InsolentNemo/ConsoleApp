package dev.insolentnemo.consoleapp.command;

import dev.insolentnemo.consoleapp.command.plugin.PluginDisableCommand;
import dev.insolentnemo.consoleapp.command.plugin.PluginEnableCommand;
import dev.insolentnemo.consoleapp.command.plugin.PluginInfoCommand;
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
        addSubCommand("enable", new PluginEnableCommand(consoleApp));
        addSubCommand("disable", new PluginDisableCommand(consoleApp));
        addSubCommand("reload", new PluginReloadCommand(consoleApp));
        addSubCommand("info", new PluginInfoCommand(consoleApp));
    }

}
