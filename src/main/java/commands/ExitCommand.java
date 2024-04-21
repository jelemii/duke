package commands;

import commands.Command;
import storage.Storage;
import tasks.TaskList;
import ui.Ui;

public class ExitCommand extends Command {

    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbyeMessage();
        isExit();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
