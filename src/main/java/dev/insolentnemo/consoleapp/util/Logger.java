package dev.insolentnemo.consoleapp.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat FILE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private static final File LOGS_PATH = new File(System.getProperty("user.dir") + "/logs");

    private static void println(MessageType type, String line)  {
        final String color = switch (type) {
            case INFO -> ConsoleColor.GREEN.toString();
            case WARNING -> ConsoleColor.YELLOW.toString();
            case ERROR -> ConsoleColor.RED.toString();
        };

        line = color + type + ":" + ConsoleColor.RESET + " " + line;
        println(line);
    }

    public static void println(String line)  {
        line = line.replace("\n", "\n   ");
        final Date time = new Date();
        final String prefix = "<" + FORMAT.format(time) + ">";
        System.out.println(ConsoleColor.RESET + prefix + " " + line + ConsoleColor.RESET);
        log(prefix + " " + line);
    }

    public static void println(String[] lines)  {
        String linesStr = lines[0];
        for (int i = 1; i < lines.length; i++) linesStr += "\n" + lines[i];
        println(linesStr);
    }

    public static void info(String info) { println(MessageType.INFO, info);}

    public static void warning(String warning) { println(MessageType.WARNING, warning);}

    public static void error(String error)  {
        println(MessageType.ERROR, error);
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String removeColors(String line) {
        for (ConsoleColor consoleColor : ConsoleColor.values()) {
            final String colorCode = consoleColor.toString();
            line = line.replace(colorCode, "");
        }

        return line;
    }

    public static void log(String line) {
        final Date time = new Date();
        final String fileName = FILE_FORMAT.format(time) + ".log";
        final File file = new File(LOGS_PATH + "/" + fileName);

        try {
            if (!LOGS_PATH.exists()) LOGS_PATH.mkdirs();
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
