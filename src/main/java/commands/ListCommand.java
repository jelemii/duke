package commands;

import commands.Command;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;
/**
 * The command to display all tasks in the task list.
 */
public class ListCommand extends Command {

    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "Task list should not be empty. DukeException should have been thrown in Parser.";
        ui.showList(tasks);
    }
}
