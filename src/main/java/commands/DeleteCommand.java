package commands;

import commands.Command;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;

import java.io.IOException;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) throws IOException {
        Ui.showTaskDeleted(tasks.getTask(index), tasks.getSize());
        tasks.deleteTask(index);
        storage.saveTaskToFile(tasks.getAllTasks());
    }
}