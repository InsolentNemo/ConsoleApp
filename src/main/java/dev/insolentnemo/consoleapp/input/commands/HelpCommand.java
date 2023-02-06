package dev.insolentnemo.consoleapp.input.commands;

import dev.insolentnemo.consoleapp.input.Command;
import dev.insolentnemo.consoleapp.utils.CommandHandler;
import dev.insolentnemo.consoleapp.utils.Logger;

import java.util.List;

public class HelpCommand extends Command {

    private final int maxLinesPerPage = 10;

    public HelpCommand() {
        super("help <page>");
    }

    @Override
    public void onCommand(String[] args) {
        final List<Command> commands = CommandHandler.getCommands();
        final int maxPages = (int) Math.ceil((double) commands.size() / maxLinesPerPage);
        final int page;

        if (args.length == 0) page = 1;
        else {
            try {
                page = Integer.parseInt(args[0]);
            } catch (NumberFormatException exception) {
                Logger.argumentError(args[0]);
                return;
            }
        }

        final String pageString = getPage(page);

        if (pageString == null) {
            Logger.error("Page '" + page + "' is invalid. Available pages: 1-" + maxPages);
            return;
        }

        Logger.println(pageString);
    }

    @Override
    public void registerSubCommands() {

    }

    private String getPage(int page) {
        final List<Command> commands = CommandHandler.getCommands();
        final int maxPages = (int) Math.ceil((double) commands.size() / maxLinesPerPage);

        if (page > maxPages || page < 1) return null;

        final StringBuilder pageMessage = new StringBuilder("Commands page " + page + "/" + maxPages + ":");

        for (int i = 0; i < maxLinesPerPage; i++) {
            final int index = (maxLinesPerPage * (page - 1)) + i;

            if (index > commands.size() - 1) break;

            final Command command = commands.get(index);
            pageMessage.append("\n");
            pageMessage.append(command.getUsage());
        }

        return pageMessage.toString();
    }

}
