package dev.insolentnemo.consoleapp.plugin;

import dev.insolentnemo.consoleapp.command.Command;
import dev.insolentnemo.consoleapp.command.CommandHandler;
import dev.insolentnemo.consoleapp.plugin.Plugin;
import dev.insolentnemo.consoleapp.util.Config;
import dev.insolentnemo.consoleapp.util.ConsoleApp;
import dev.insolentnemo.consoleapp.util.Logger;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {

    private static ConsoleApp CONSOLE_APP;
    private static final File PLUGINS_PATH = new File(System.getProperty("user.dir") + "/plugins");
    private static final Map<String, Plugin> PLUGINS = new HashMap<>();
    private static final Map<String, URLClassLoader> CLASS_LOADERS = new HashMap<>();

    public static void initialize(ConsoleApp consoleApp) {
        CONSOLE_APP = consoleApp;

        if (!PLUGINS_PATH.exists()) PLUGINS_PATH.mkdirs();

        loadAll();
        autoDisableCommands();
    }

    private static void autoDisableCommands() {
        final JSONArray disabledPlugins = (JSONArray) Config.get("disabled-plugins");

        for (Plugin plugin : PLUGINS.values()) {
            final Properties properties = plugin.getProperties();
            final String name = properties.getProperty("name").toLowerCase();

            if (disabledPlugins != null) {
                if (disabledPlugins.contains(name)) continue;
            }

            enable(plugin);
        }
    }

    private static Plugin createPlugin(File file, Properties properties) {
        final String main = properties.getProperty("main");
        final URLClassLoader classLoader;
        Plugin plugin = null;

        try {
            classLoader = URLClassLoader.newInstance(new URL[]{ file.toURI().toURL() });
            final Class<?> pluginClass = classLoader.loadClass(main);
            plugin = (Plugin) pluginClass.newInstance();
            plugin.setConsoleApp(CONSOLE_APP);
            plugin.setFile(file);
            plugin.setProperties(properties);
            classLoader.close();
        } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
            final String message = exception.getMessage();
            Logger.error(message);
            return null;
        }

        return plugin;
    }

    public static void load(File file) {
        final Properties properties = getProperties(file);

        if (properties == null)  {
            Logger.error("Failed loading plugin '" + file + "'. No properties file found.");
            return;
        }

        final String main = properties.getProperty("main");

        if (main == null) {
            Logger.error("Failed loading plugin '" + file + "'. No property named 'main' found.");
            return;
        }

        final String name = properties.getProperty("name").toLowerCase();

        if (PLUGINS.containsKey(name)) {
            Logger.error("Plugin named '" + name + "' were already loaded.");
            return;
        }

        final Plugin plugin = createPlugin(file, properties);

        if (plugin == null) {
            Logger.error("Failed loading plugin '" + file + "'.");
            return;
        }

        PLUGINS.put(name, plugin);
    }

    public static void loadAll() {
        final File[] files = PLUGINS_PATH.listFiles();
        final List<File> jars = new ArrayList<>();

        for (File file : files) {
            final String name = file.getName();

            if (name.endsWith(".jar")) jars.add(file);
        }

        for (File jar : jars) load(jar);
    }

    public static void reload(Plugin plugin) {
        final File file = plugin.getFile();
        final Properties properties = getProperties(file);
        final String name = properties.getProperty("name").toLowerCase();
        final boolean enabled = plugin.isEnabled();
        unload(plugin);
        load(file);
        final Plugin newPlugin = PLUGINS.get(name);

        if (enabled) newPlugin.enable();
    }

    public static void reloadAll() {
        unloadAll();
        loadAll();
        autoDisableCommands();
    }

    public static void unload(Plugin plugin) {
        if (plugin.isEnabled()) disable(plugin);

        final Properties properties = plugin.getProperties();
        final String name = properties.getProperty("name").toLowerCase();
        PLUGINS.remove(name);
    }

    public static void unloadAll() {
        for (Plugin plugin : PLUGINS.values()) unload(plugin);
    }

    public static void enable(Plugin plugin) {
        if (plugin.isEnabled()) return;

        final Properties properties = plugin.getProperties();
        final String main = properties.getProperty("main");
        final File file = plugin.getFile();
        final URLClassLoader classLoader;
        Plugin newPlugin = null;

        try {
            classLoader = URLClassLoader.newInstance(new URL[]{ file.toURI().toURL() });
            final Class<?> pluginClass = classLoader.loadClass(main);
            newPlugin = (Plugin) pluginClass.newInstance();
        } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
            final String message = exception.getMessage();
            Logger.error(message);
            return;
        }

        newPlugin.setConsoleApp(CONSOLE_APP);
        newPlugin.setFile(file);
        newPlugin.setProperties(properties);
        newPlugin.enable();
        final CommandHandler commandHandler = CONSOLE_APP.getCommandHandler();
        final Map<String, Command> commands = plugin.getCommands();

        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            final String label = entry.getKey();
            final Command command = entry.getValue();
            commandHandler.add(label, command);
        }

        final String name = properties.getProperty("name").toLowerCase();
        PLUGINS.put(name, newPlugin);
        CLASS_LOADERS.put(name, classLoader);
    }

    public static void enableAll() {
        for (Plugin plugin : PLUGINS.values()) enable(plugin);
    }

    public static void disable(Plugin plugin) {
        if (!plugin.isEnabled()) return;

        plugin.disable();
        final Properties properties = plugin.getProperties();
        final String name = properties.getProperty("name").toLowerCase();
        URLClassLoader classLoader = CLASS_LOADERS.get(name);

        try {
            classLoader.close();
        } catch (IOException exception) {
            final String message = exception.getMessage();
            Logger.error(message);
        }

        final CommandHandler commandHandler = CONSOLE_APP.getCommandHandler();
        final Map<String, Command> commands = plugin.getCommands();

        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            final String label = entry.getKey();
            commandHandler.remove(label);
        }

        CLASS_LOADERS.remove(name);
    }

    public static void disableAll() {
        for (Plugin plugin : PLUGINS.values()) disable(plugin);
    }

    public static Plugin getPlugin(String name) {
        name = name.toLowerCase();
        return PLUGINS.get(name);
    }

    public static List<Plugin> getPlugins() {
        return PLUGINS.values().stream().toList();
    }

    private static Properties getProperties(File file) {
        final Properties properties = new Properties();

        try {
            final JarFile jarFile = new JarFile(file);
            final JarEntry propertiesFile = jarFile.getJarEntry("plugin.properties");
            final InputStream inputStream = jarFile.getInputStream(propertiesFile);
            properties.load(inputStream);
            jarFile.close();
            return properties;
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

}
