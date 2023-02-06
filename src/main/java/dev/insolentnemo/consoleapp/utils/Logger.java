package dev.insolentnemo.consoleapp.utils;

import dev.insolentnemo.consoleapp.input.Command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat fileFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final File logsPath = new File(System.getProperty("user.dir") + "/logs");

    public static void println(String line)  {
        line = line.replace("\n", "\n   ");
        final Date time = new Date();
        final String prefix = "<" + format.format(time) + ">";
        System.out.println(ConsoleColor.RESET + prefix + " " + line + ConsoleColor.RESET);
        log(prefix + " " + line);
    }

    public static void println(MessageType type, String line)  {
        final String color = switch (type) {
            case ERROR -> ConsoleColor.RED;
            case INFO -> ConsoleColor.RESET;
        };

        line = color + type + ":" + ConsoleColor.RESET + " " + line;
        println(line);
    }

    public static void error(String error)  {
        println(MessageType.ERROR, error);
    }

    public static void argumentError(String argument)  {
        error("Argument '" + argument + "' is invalid.");
    }

    public static void invalidCommand(String command) {
        error("Invalid command '" + command + "'.");
    }

    public static void usageError(Command command)  {
        error("Usage: " + command.getUsage());
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String removeColors(String line) {
        final Field[] fields = ConsoleColor.class.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                final String colorCode = (String) field.get(ConsoleColor.class);
                line = line.replace(colorCode, "");
            } catch (IllegalAccessException exception) {
                throw new RuntimeException(exception);
            }
        }

        return line;
    }

    public static void log(String line) {
        final Date time = new Date();
        final String fileName = fileFormat.format(time) + ".log";
        final File file = new File(logsPath + "/" + fileName);

        try {
            if (!logsPath.exists()) logsPath.mkdirs();
            if (!file.exists()) file.createNewFile();

            final FileWriter fileWriter = new FileWriter(file, true);
            line = removeColors(line);
            fileWriter.write(line + "\n");
            fileWriter.close();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
