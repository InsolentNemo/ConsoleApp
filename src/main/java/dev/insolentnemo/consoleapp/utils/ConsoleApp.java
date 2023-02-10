package dev.insolentnemo.consoleapp.utils;

import java.util.Scanner;

public abstract class ConsoleApp {

    private void initialize() {
        CommandHandler.initialize();
        PluginManager.initialize();
    }

    public void start() {
        initialize();
        Logger.clearConsole();
        onStarted();
        PluginManager.loadAll();
        PluginManager.enableAll();
        Logger.println("Type 'help' for a list of available commands.");
        final Scanner scanner = new Scanner(System.in);

        while (Thread.currentThread().isAlive()) {
            final String line = scanner.nextLine().trim();

            if (line.length() == 0) continue;

            CommandHandler.handle(line);
        }
    }

    public abstract void onStarted();
}
