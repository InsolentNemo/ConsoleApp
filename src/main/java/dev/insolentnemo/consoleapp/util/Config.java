package dev.insolentnemo.consoleapp.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {

    private static final File FILE = new File(System.getProperty("user.dir") + "/config.json");
    private static JSONObject JSON = new JSONObject();

    public static void initialize() {
        if (!FILE.exists()) create();
        else load();
    }

    public static void create() {
        try {
            FILE.createNewFile();
        } catch (IOException exception) {
            final String fileName = FILE.getName();
            Logger.error("Cannot create config file '" + fileName + "'.");
        }
    }

    public static void load() {
        final String content;

        try {
            final Path path = FILE.toPath();
            content = Files.readString(path);
        } catch (IOException exception) {
            Logger.println("Failed loading config '" + FILE.getName() + "'.");
            return;
        }

        JSON = (JSONObject) JSONValue.parse(content);
    }

    public static void save() {
        final FileWriter fileWriter;
        try {
            if (!FILE.exists()) create();

            fileWriter = new FileWriter(FILE, false);
            fileWriter.write(JSON.toJSONString());
            fileWriter.close();
        } catch (IOException exception) {
            final String fileName = FILE.getName();
            Logger.error("Cannot save config file '" + fileName + "'.");
        }
    }

    public static void reload() {
        save();
        load();
    }

    public static Object get(String key) {
        return JSON.get(key);
    }

    public static void set(String key, Object object) {
        JSON.put(key, object);
        save();
    }

    public static void addToList(String key, Object object) {
        final JSONArray list = (JSONArray) JSON.get(key);
        list.add(object);
    }

    public static void removeFromList(String key, Object object) {
        final JSONArray list = (JSONArray) JSON.get(key);
        list.remove(object);
    }

}
