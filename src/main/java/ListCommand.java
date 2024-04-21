public class ListCommand extends Command {

    @Override
    public void executeCommand(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }
}
