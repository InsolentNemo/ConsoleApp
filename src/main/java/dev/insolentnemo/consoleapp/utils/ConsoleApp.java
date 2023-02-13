package dev.insolentnemo.consoleapp.utils;

import java.util.Scanner;

public abstract class ConsoleApp {

    private void initialize() {
        CommandHandler.initialize(this);
        PluginManager.initialize();
    }

    public void start() {
        initialize();
        onStarted();
        final Scanner scanner = new Scanner(System.in);

        while (Thread.currentThread().isAlive()) {
            final String line = scanner.nextLine().trim();

            if (line.length() == 0) continue;

            CommandHandler.handle(line);
        }
    }

    public abstract void onStarted();

    public abstract void addCommands();

}
