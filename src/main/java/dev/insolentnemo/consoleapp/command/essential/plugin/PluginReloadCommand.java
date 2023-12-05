package dev.insolentnemo.consoleapp.command.essential.plugin;

import dev.insolentnemo.consoleapp.command.Command;
import dev.insolentnemo.consoleapp.command.CommandSender;
import dev.insolentnemo.consoleapp.plugin.Plugin;
import dev.insolentnemo.consoleapp.plugin.PluginManager;
import dev.insolentnemo.consoleapp.util.*;

import java.util.Properties;

public class PluginReloadCommand extends Command {

    public PluginReloadCommand(ConsoleApp consoleApp) {
        super(consoleApp, "plugin reload <plugin-name>");
    }

    @Override
    protected void onCommand(CommandSender sender, String[] args) {
        if (args.length != 1) {
            final String usage = getUsage();
            sender.sendUsageError(usage);
            return;
        }

        final String name = args[0].toLowerCase();
        final Plugin plugin = PluginManager.getPlugin(name);

        if (plugin == null) {
            sender.sendError("There is no plugin named '" + name + "'.");
            return;
        }

        PluginManager.reload(plugin);
        final Properties properties = plugin.getProperties();
        final String pluginName = properties.getProperty("name");
        sender.sendMessage("Plugin '" + pluginName + "' has been reloaded.");
    }

}
