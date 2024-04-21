import java.io.IOException;

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
        Task task = new Event(description, from, to);
        tasks.addTask(task);
        Ui.showTaskAdded(task, tasks.getSize());
        storage.saveTaskToFile(tasks.getAllTasks());
    }
}
