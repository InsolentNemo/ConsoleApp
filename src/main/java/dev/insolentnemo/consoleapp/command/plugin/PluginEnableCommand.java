package dev.insolentnemo.consoleapp.command.plugin;

import dev.insolentnemo.consoleapp.util.*;

import java.util.Properties;

public class PluginEnableCommand extends Command {

    public PluginEnableCommand(ConsoleApp consoleApp) {
        super(consoleApp, "plugin enable <plugin-name>");
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

        PluginManager.enable(plugin);
        final Properties properties = plugin.getProperties();
        final String pluginName = properties.getProperty("name");
        Config.removeFromList("disabled-plugins", name);
        Logger.println("Plugin '" + pluginName + "' has been enabled.");
    }

}
