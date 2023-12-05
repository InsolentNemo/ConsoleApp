package dev.insolentnemo.consoleapp.util;

import dev.insolentnemo.consoleapp.command.CommandHandler;
import dev.insolentnemo.consoleapp.command.CommandSender;
import dev.insolentnemo.consoleapp.command.essential.*;
import dev.insolentnemo.consoleapp.command.sender.ConsoleSender;
import dev.insolentnemo.consoleapp.plugin.PluginManager;

import java.util.Scanner;

public class ConsoleApp {

    private final CommandHandler commandHandler = new CommandHandler();

    public void start() {
        initializeCommands();
        Config.initialize();
        onStart();
        PluginManager.initialize(this);
        final ConsoleSender sender = new ConsoleSender();
        final Scanner scanner = new Scanner(System.in);

        while (Thread.currentThread().isAlive()) {
            final String line = scanner.nextLine().trim();

            if (line.length() == 0) continue;

            commandHandler.handle(sender, line);
        }
    }

    public void reload() {
        Logger.println("Reloading ...");
        onReload();
        PluginManager.reloadAll();
        Config.reload();
        Logger.println("Reload completed.");
    }

    public void stop() {
        onStop();
        PluginManager.unloadAll();
        Config.save();
        System.exit(0);
    }

    protected void initializeCommands() {
        commandHandler.add("help", new HelpCommand(this));
        commandHandler.add("clear", new ClearCommand(this));
        commandHandler.add("reload", new ReloadCommand(this));
        commandHandler.add("plugin", new PluginCommand(this));
        commandHandler.add("plugins", new PluginsCommand(this));
        commandHandler.add("stop", new StopCommand(this));
    }

    protected void onStart() { }

    protected void onReload() { }

    protected void onStop() { }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

}
