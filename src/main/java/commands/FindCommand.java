package commands;

import storage.Storage;
import tasks.TaskList;
import ui.Ui;

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
        ui.showMatchingTasks(tasks.matchingTasksFound(keyword));
    }
}
