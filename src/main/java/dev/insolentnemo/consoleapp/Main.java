package dev.insolentnemo.consoleapp;

import dev.insolentnemo.consoleapp.utils.ConsoleApp;
import dev.insolentnemo.consoleapp.utils.ConsoleColor;
import dev.insolentnemo.consoleapp.utils.Logger;

public class Main {
    public static void main(String[] args) {
        new ConsoleApp() {

            @Override
            public void onStarted() {
                final String title = ConsoleApp.class.getPackage().getImplementationTitle();
                final String version = ConsoleApp.class.getPackage().getImplementationVersion();
                Logger.println(ConsoleColor.PURPLE_BRIGHT + title + " v" + version);
            }

            @Override
            public void onReload() {

            }

            @Override
            public void addCommands() {

            }

        }.start();

    }
}