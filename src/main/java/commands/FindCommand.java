package commands;

import storage.Storage;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;

import java.util.ArrayList;

/**
 * The command to find tasks that matches a keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword=keyword;
    }

    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) {
        assert keyword != null : "Keyword should not be empty. DukeException should have been thrown in Parser.";
        ArrayList<Task> listOfMatchedTasks = tasks.matchingTasksFound(keyword);
        ui.showMatchingTasks(listOfMatchedTasks);
    }
}
