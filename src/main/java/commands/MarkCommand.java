package commands;

import commands.Command;
import duke.DukeException;
import storage.Storage;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;

import java.io.IOException;
/**
 * The command to mark a task in the task list.
 */
public class MarkCommand extends Command {

    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        assert tasks != null : "Task list should not be empty. DukeException should have been thrown in Parser.";
        Task task = tasks.getTask(index);
        task.markAsDone();
        ui.showTaskMarked(task);
        storage.saveTaskToFile(tasks.getAllTasks());
    }
}
