package commands;

import commands.Command;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;

public class ListCommand extends Command {

    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }
}
