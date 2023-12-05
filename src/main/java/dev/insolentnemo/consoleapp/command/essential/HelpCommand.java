package dev.insolentnemo.consoleapp.command.essential;

import dev.insolentnemo.consoleapp.command.Book;
import dev.insolentnemo.consoleapp.command.Command;
import dev.insolentnemo.consoleapp.command.CommandHandler;
import dev.insolentnemo.consoleapp.util.ConsoleApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HelpCommand extends Book {

    public HelpCommand(ConsoleApp consoleApp) {
        super(consoleApp, "help <page>");
    }

    @Override
    protected List<String> onListRefresh() {
        final ConsoleApp consoleApp = getConsoleApp();
        final CommandHandler commandHandler = consoleApp.getCommandHandler();
        final Map<String, Command> commands = commandHandler.getCommands();
        final List<String> list = new ArrayList<>();

        for (Command command : commands.values()) {
            final String usage = command.getUsage();
            list.add(usage);
        }

        return list;
    }

}