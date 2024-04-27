package commands;

import storage.Storage;
import tasks.Deadline;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * The command to add a new deadline task into the task list.
 */
public class AddDeadlineCommand extends Command {

    private final String description;
    private final String by;

    public AddDeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) throws IOException {
        assert description != null : "description should not be null";
        assert by != null : "by should not be null";
        Task task = new Deadline(description, by);
        tasks.addTask(task);
        Ui.showTaskAdded(task, tasks.getSize());
        storage.saveTaskToFile(tasks.getAllTasks());
    }
}
