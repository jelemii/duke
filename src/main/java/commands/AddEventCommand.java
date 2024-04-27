package commands;

import commands.Command;
import storage.Storage;
import tasks.Event;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * The command to add a new event task into the task list.
 */
public class AddEventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;

    public AddEventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) throws IOException {
        assert description != null : "Description should not be null.";
        assert from != null : "From should not be null.";
        assert to != null : "To should not be null.";
        Task task = new Event(description, from, to);
        tasks.addTask(task);
        Ui.showTaskAdded(task, tasks.getSize());
        storage.saveTaskToFile(tasks.getAllTasks());
    }
}
