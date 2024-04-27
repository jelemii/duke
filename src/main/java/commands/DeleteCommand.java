package commands;

import commands.Command;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;

import java.io.IOException;
/**
 * The command to delete a task in the task list.
 */
public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) throws IOException {
        assert tasks != null : "Task list should not be empty. DukeException should have been thrown in Parser.";
        Ui.showTaskDeleted(tasks.getTask(index), tasks.getSize());
        tasks.deleteTask(index);
        storage.saveTaskToFile(tasks.getAllTasks());
    }
}