package dev.insolentnemo.consoleapp.input.commands;

import dev.insolentnemo.consoleapp.input.Book;
import dev.insolentnemo.consoleapp.input.Command;
import dev.insolentnemo.consoleapp.utils.CommandHandler;
import dev.insolentnemo.consoleapp.utils.ConsoleApp;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends Book {

    public HelpCommand(ConsoleApp consoleApp) {
        super(consoleApp, "help <page>");
    }

    @Override
    public List<String> onListRefresh() {
        final List<Command> commands = CommandHandler.getCommands();
        final List<String> usageList = new ArrayList<>();

        for (Command command : commands) {
            final String usage = command.getUsage();
            usageList.add(usage);
        }

        return usageList;
    }
}
