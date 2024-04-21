import java.io.IOException;

public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        Task task = tasks.getTask(index);
        task.unmarkAsDone();
        ui.showTaskUnmarked(task);
        storage.saveTaskToFile(tasks.getAllTasks());
    }
}