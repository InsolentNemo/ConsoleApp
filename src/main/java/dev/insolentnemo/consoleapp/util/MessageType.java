package dev.insolentnemo.consoleapp.util;

public enum MessageType {

    INFO(ConsoleColor.GREEN),
    WARNING(ConsoleColor.YELLOW),
    ERROR(ConsoleColor.RED);

    private final ConsoleColor consoleColor;

    MessageType(ConsoleColor consoleColor) {
        this.consoleColor = consoleColor;
    }

    public String getColorCode() {
        return consoleColor.toString();
    }

}
