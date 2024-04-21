import java.io.IOException;

public class MarkCommand extends Command {

    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        Task task = tasks.getTask(index);
        task.markAsDone();
        ui.showTaskMarked(task);
        storage.saveTaskToFile(tasks.getAllTasks());
    }
}
