public class DeleteCommand {
    public static void deleteTask(int index) {
        Ui.showTaskDeleted(TaskList.getTask(index), TaskList.getSize());
        TaskList.deleteTask(index);
    }
}