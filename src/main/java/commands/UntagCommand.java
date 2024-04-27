package commands;

import storage.Storage;
import tasks.Task;
import tasks.TaskList;
import ui.Ui;

import java.io.IOException;
/**
 * The command to untag a task in the task list.
 */
public class UntagCommand extends Command {
    private int index;
    private String tag;

    public UntagCommand (int index, String tag) {
        this.index = index;
        this.tag = tag;
    }

    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) throws IOException {
        Task task = tasks.getTask(index);
        task.unTag(tag);
        ui.showTaskUntagged(index,task,tag);
        storage.saveTaskToFile(tasks.getAllTasks());
    }
}
