package commands;

import storage.Storage;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * The command to tag a task in the task list.
 */
public class TagCommand extends Command {
    private int index;
    private String tag;

    public TagCommand (int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) throws IOException {
        Task task = tasks.getTask(index);
        task.addTag(tag);
        ui.showTaskTagged(index,task,tag);
        storage.saveTaskToFile(tasks.getAllTasks());
    }
}
