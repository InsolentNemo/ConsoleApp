package dev.insolentnemo.consoleapp.util;

import java.io.File;
import java.util.*;

public class Plugin {

    private final ConsoleApp consoleApp;
    private boolean enabled = false;
    private File file;
    private Properties properties;

    private final Map<String, Command> commands = new HashMap<>();

    public Plugin(ConsoleApp consoleApp) {
        this.consoleApp = consoleApp;
    }

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
