package dev.insolentnemo.consoleapp.utils;

import java.util.Scanner;

public class ConsoleApp {

    public static void run() {
        Logger.clearConsole();
        Logger.println(ConsoleColor.PURPLE_BRIGHT + getTitle() + " v" + getVersion());
        initialize();
        Logger.println("Type 'help' for a list of available commands.");
        final Scanner scanner = new Scanner(System.in);

        while (Thread.currentThread().isAlive()) {
            final String line = scanner.nextLine().trim();

            if (line.length() == 0) continue;

            CommandHandler.handle(line);
        }
    }

    private static void initialize() {
        CommandHandler.initialize();
        PluginManager.initialize();
        PluginManager.loadAll();
        PluginManager.enableAll();
    }

    public static String getVersion() {
        return ConsoleApp.class.getPackage().getImplementationVersion();
    }

    public static String getTitle() {
        return ConsoleApp.class.getPackage().getImplementationTitle();
    }

}
