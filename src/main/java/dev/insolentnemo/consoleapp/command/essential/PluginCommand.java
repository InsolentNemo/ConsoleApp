package dev.insolentnemo.consoleapp.command.essential;

import dev.insolentnemo.consoleapp.command.essential.plugin.PluginDisableCommand;
import dev.insolentnemo.consoleapp.command.essential.plugin.PluginEnableCommand;
import dev.insolentnemo.consoleapp.command.essential.plugin.PluginInfoCommand;
import dev.insolentnemo.consoleapp.command.essential.plugin.PluginReloadCommand;
import dev.insolentnemo.consoleapp.command.Command;
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
