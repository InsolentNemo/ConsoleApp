package dev.insolentnemo.consoleapp.command.essential.plugin;

import dev.insolentnemo.consoleapp.command.Command;
import dev.insolentnemo.consoleapp.command.CommandSender;
import dev.insolentnemo.consoleapp.plugin.Plugin;
import dev.insolentnemo.consoleapp.plugin.PluginManager;
import dev.insolentnemo.consoleapp.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PluginInfoCommand extends Command {

    public PluginInfoCommand(ConsoleApp consoleApp) {
        super(consoleApp, "plugin info <plugin-name>");
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

        final List<String> lines = new ArrayList<>();
        lines.add("Plugin info:");
        final Properties properties = plugin.getProperties();
        final String pluginName = properties.getProperty("name");
        lines.add("Name: " + pluginName);

        final String version = properties.getProperty("version");
        if (version != null) lines.add("Version: " + version);

        final String author = properties.getProperty("author");
        if (author != null) lines.add("Author: " + author);

        final String[] linesArr = lines.toArray(new String[]{});
        sender.sendMessage(linesArr);
    }

}
