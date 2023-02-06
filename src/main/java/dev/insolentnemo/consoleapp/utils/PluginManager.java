package dev.insolentnemo.consoleapp.utils;

import dev.insolentnemo.consoleapp.input.Plugin;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class PluginManager {

    private static final File pluginsPath = new File(System.getProperty("user.dir") + "/plugins");
    private static final Map<String, Plugin> plugins = new HashMap<>();
    private static final Map<Plugin, Properties> properties = new HashMap<>();

    public static void initialize() {
        if (!pluginsPath.exists()) pluginsPath.mkdirs();
    }

    public static void load(File pluginFile) {
        final Properties pluginProperties = getProperties(pluginFile);
        final String name = pluginProperties.getProperty("name");
        final String main = pluginProperties.getProperty("main");

        if (plugins.containsKey(name)) {
            Logger.error("Plugin named '" + name + "' is already loaded.");
            return;
        }

        final URLClassLoader classLoader;
        final Plugin plugin;

        try {
            classLoader = URLClassLoader.newInstance(new URL[]{ pluginFile.toURL() });
            plugin = (Plugin) classLoader.loadClass(main).newInstance();
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            throw new RuntimeException(exception);
        }

        plugins.put(name, plugin);
        properties.put(plugin, pluginProperties);
    }

    public static void reload() {
        unloadAll();
        loadAll();
        enableAll();
    }

    public static void loadAll() {
        final File[] files = pluginsPath.listFiles();
        final List<File> jars = new ArrayList<>();

        for (File file : files) {
            final String name = file.getName();

            if (name.endsWith(".jar")) jars.add(file);
        }

        for (File jar : jars) load(jar);
    }

    public static void unload(Plugin plugin) {
        if (plugin.isEnabled()) disable(plugin);

        final Properties pluginProperties = properties.get(plugin);
        final String name = pluginProperties.getProperty("name");
        plugins.put(name, null);
        properties.put(plugin, null);
    }

    public static void unloadAll() {
        for (Plugin plugin : plugins.values()) unload(plugin);
    }

    public static void enable(Plugin plugin) {
        final Properties pluginProperties = properties.get(plugin);
        final String name = pluginProperties.getProperty("name");

        if (plugin.isEnabled()) {
            Logger.error("Plugin '" + name + "' is already enabled.");
            return;
        }

        plugin.enable();
        Logger.println("Plugin '" + name + "' has been enabled.");
    }

    public static void enableAll() {
        for (Plugin plugin : plugins.values()) enable(plugin);
    }

    public static void disable(Plugin plugin) {
        final Properties pluginProperties = properties.get(plugin);
        final String name = pluginProperties.getProperty("name");

        if (!plugin.isEnabled()) {
            Logger.error("Plugin '" + name + "' is already disabled.");
            return;
        }

        plugin.disable();
        Logger.println("Plugin '" + name + "' has been disabled.");
    }

    public static void disableAll() {
        for (Plugin plugin : plugins.values()) disable(plugin);
    }

    private static Properties getProperties(File pluginFile) {
        final Properties properties = new Properties();
        final String osName = System.getProperty("os.name");

        try {
            final URL url;
            if (osName.contains("Windows")) url = new URL("jar:file:/" + pluginFile + "!/plugin.properties");
            else if (osName.contains("Linux")) {
                final String pluginName = pluginFile.getName();
                url = new URL("jar:file:./plugins/" + pluginName + "!/plugin.properties");
            }
            else throw new RuntimeException(osName + " is not supported.");

            final JarURLConnection connection = (JarURLConnection) url.openConnection();
            final InputStream inputStream = connection.getInputStream();
            properties.load(inputStream);
            return properties;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
