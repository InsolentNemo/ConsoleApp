package dev.insolentnemo.consoleapp.util;

import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PluginManager {

    private static final File PLUGINS_PATH = new File(System.getProperty("user.dir") + "/plugins");
    private static final Map<String, Plugin> PLUGINS = new HashMap<>();

    public static void initialize() {
        if (!PLUGINS_PATH.exists()) PLUGINS_PATH.mkdirs();

        loadAll();
        final JSONArray disabledPlugins = (JSONArray) Config.get("disabled-plugins");

        for (Plugin plugin : PLUGINS.values()) {
            final Properties properties = plugin.getProperties();
            final String name = properties.getProperty("name").toLowerCase();

            if (disabledPlugins != null) {
                if (disabledPlugins.contains(name)) continue;
            }

            plugin.enable();
        }
    }

    public static void load(File pluginFile) {
        final Properties properties = getProperties(pluginFile);
        final String name = properties.getProperty("name").toLowerCase();
        final String main = properties.getProperty("main");

        if (PLUGINS.containsKey(name)) {
            Logger.error("Plugin named '" + name + "' were already loaded.");
            return;
        }

        final URLClassLoader classLoader;
        final Plugin plugin;

        try {
            classLoader = URLClassLoader.newInstance(new URL[]{ pluginFile.toURL() });
            plugin = (Plugin) classLoader.loadClass(main).newInstance();
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            Logger.error("Failed loading plugin '" + pluginFile + "'.");
            exception.printStackTrace();
            return;
        }

        plugin.setProperties(properties);
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
        plugin.disable();
        unload(plugin);
        load(file);
    }

    public static void reloadAll() {
        unloadAll();
        initialize();
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
        plugin.enable();
    }

    public static void enableAll() {
        for (Plugin plugin : PLUGINS.values()) enable(plugin);
    }

    public static void disable(Plugin plugin) {
        if (!plugin.isEnabled()) return;
        plugin.disable();
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
