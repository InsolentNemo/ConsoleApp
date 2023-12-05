package dev.insolentnemo.consoleapp.command.essential;

import dev.insolentnemo.consoleapp.command.Book;
import dev.insolentnemo.consoleapp.plugin.Plugin;
import dev.insolentnemo.consoleapp.plugin.PluginManager;
import dev.insolentnemo.consoleapp.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PluginsCommand extends Book {

    public PluginsCommand(ConsoleApp consoleApp) {
        super(consoleApp, "plugins");
    }

    @Override
    protected List<String> onListRefresh() {
        final List<Plugin> plugins = PluginManager.getPlugins();
        final List<String> list = new ArrayList<>();

        for (Plugin plugin : plugins) {
            final Properties properties = plugin.getProperties();
            final String name = properties.getProperty("name");
            String line = name + " (";

            if (plugin.isEnabled()) line += ConsoleColor.GREEN + "ENABLED";
            else line += ConsoleColor.RED + "DISABLED";

            line += ConsoleColor.RESET + ")";
            list.add(line);
        }

        return list;
    }

}
