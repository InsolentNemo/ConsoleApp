package dev.insolentnemo.consoleapp;

import dev.insolentnemo.consoleapp.util.ConsoleApp;
import dev.insolentnemo.consoleapp.util.ConsoleColor;
import dev.insolentnemo.consoleapp.util.Logger;

public class Main {

    public static void main(String[] args) {
        new ConsoleApp() {

            @Override
            protected void onStart() {
                final String title = ConsoleApp.class.getPackage().getImplementationTitle();
                final String version = ConsoleApp.class.getPackage().getImplementationVersion();
                Logger.println(ConsoleColor.PURPLE_BRIGHT + title + " v" + version);
            }

            @Override
            protected void onStop() {
                Logger.println("Shutting down ...");
            }

        }.start();
    }

}