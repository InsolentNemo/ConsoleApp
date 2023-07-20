package dev.insolentnemo.consoleapp.command.plugin;

import dev.insolentnemo.consoleapp.util.*;

import java.util.Properties;

public class PluginReloadCommand extends Command {

    public PluginReloadCommand(ConsoleApp consoleApp) {
        super(consoleApp, "plugin reload <plugin-name>");
    }

    @Override
    protected void onCommand(String[] args) {
        if (args.length != 1) {
            Logger.usageError(this);
            return;
        }

        final String name = args[0].toLowerCase();
        final Plugin plugin = PluginManager.getPlugin(name);

        if (plugin == null) {
            Logger.error("There is no plugin named '" + name + "'.");
            return;
        }

        PluginManager.reload(plugin);
        final Properties properties = plugin.getProperties();
        final String pluginName = properties.getProperty("name");
        Logger.println("Plugin '" + pluginName + "' has been reloaded.");
    }

}
