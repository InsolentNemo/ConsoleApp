package dev.insolentnemo.consoleapp.util;

import java.util.Scanner;

public class ConsoleApp {

    public void start() {
        CommandHandler.initialize(this);
        Config.initialize();
        onStart();
        PluginManager.initialize(this);
        final Scanner scanner = new Scanner(System.in);

        while (Thread.currentThread().isAlive()) {
            final String line = scanner.nextLine().trim();

            if (line.length() == 0) continue;

            CommandHandler.handle(line);
        }
    }

    public void reload() {
        Logger.println("Reloading ...");
        onReload();
        CommandHandler.reload();
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

    protected void onStart() { }

    protected void onReload() { }

    protected void onStop() { }

}
