package dev.insolentnemo.consoleapp.plugin;

import dev.insolentnemo.consoleapp.command.Command;
import dev.insolentnemo.consoleapp.util.ConsoleApp;
import dev.insolentnemo.consoleapp.util.Logger;

import java.io.File;
import java.util.*;

public class Plugin {

    private ConsoleApp consoleApp;
    private final Map<String, Command> commands = new HashMap<>();
    private boolean enabled = false;
    private File file;
    private Properties properties;

    public void enable() {
        enabled = true;
        onEnable();
    }

    public void disable() {
        enabled = false;
        onDisable();
    }

    protected void addCommand(String label, Command command) {
        if (commands.containsKey(label)) {
            Logger.error("Command '" + label + "' does already exist.");
            return;
        }

        commands.put(label, command);
    }

    protected void removeCommand(String label) {
        if (commands.containsKey(label)) {
            Logger.error("Command '" + label + "' does already exist.");
            return;
        }

        commands.remove(label);
    }

    protected void onEnable() { }

    protected void onDisable() { }

    public ConsoleApp getConsoleApp() {
        return consoleApp;
    }

    public void setConsoleApp(ConsoleApp consoleApp) {
        this.consoleApp = consoleApp;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

}
