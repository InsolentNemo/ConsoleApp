package dev.insolentnemo.consoleapp.command;

import dev.insolentnemo.consoleapp.util.Book;
import dev.insolentnemo.consoleapp.util.Command;
import dev.insolentnemo.consoleapp.util.CommandHandler;
import dev.insolentnemo.consoleapp.util.ConsoleApp;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends Book {

    public HelpCommand(ConsoleApp consoleApp) {
        super(consoleApp, "help <page>");
    }

    @Override
    protected List<String> onListRefresh() {
        final List<Command> commands = CommandHandler.getCommands();
        final List<String> list = new ArrayList<>();

        for (Command command : commands) {
            final String usage = command.getUsage();
            list.add(usage);
        }

        return list;
    }

}