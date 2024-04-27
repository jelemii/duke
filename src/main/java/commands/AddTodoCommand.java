package commands;

import commands.Command;
import storage.Storage;
import tasks.Task;
import tasks.TaskList;
import tasks.Todo;
import ui.Ui;

import java.io.IOException;

/**
 * The command to add a new to-do task into the task list.
 */
public class AddTodoCommand extends Command {
    private final String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) throws IOException {
        assert description != null : "Description should not be null.";
        Task task = new Todo(description);
        tasks.addTask(task);
        Ui.showTaskAdded(task, tasks.getSize());
        storage.saveTaskToFile(tasks.getAllTasks());
    }
}
