package dev.insolentnemo.consoleapp.util;

import java.util.List;

public abstract class Book extends Command {

    private int linesPerPage = 10;
    private List<String> list;

    public Book(ConsoleApp consoleApp, String usage) {
        super(consoleApp, usage);
    }

    @Override
    public void onCommand(String[] args) {
        list = onListRefresh();
        final int maxPages = (int) Math.ceil((double) list.size() / linesPerPage);
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

        if (list.size() == 0) {
            Logger.error("Page '" + page + "' is invalid. There are no available pages.");
            return;
        }

        final String pageStr = getPage(page);

        if (pageStr == null) {
            Logger.error("Page '" + page + "' is invalid. Available pages: 1-" + maxPages);
            return;
        }

        Logger.println(pageStr);
    }

    protected abstract List<String> onListRefresh();

    public int getLinesPerPage() {
        return linesPerPage;
    }

    public void setLinesPerPage(int linesPerPage) {
        this.linesPerPage = linesPerPage;
    }

    private String getPage(int page) {
        final int maxPages = (int) Math.ceil((double) list.size() / linesPerPage);

        if (page > maxPages || page < 1) return null;

        final StringBuilder pageStr = new StringBuilder("Page " + page + "/" + maxPages + ":");

        for (int i = 0; i < linesPerPage; i++) {
            final int index = (linesPerPage * (page - 1)) + i;

            if (index > list.size() - 1) break;

            final String line = list.get(index);
            pageStr.append("\n");
            pageStr.append(line);
        }

        return pageStr.toString();
    }

}
