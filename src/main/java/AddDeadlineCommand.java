public class AddDeadlineCommand {
    public static void addDeadline(String input) {
        int byIndex = input.indexOf("/by");
        String desc = input.substring(9, byIndex).trim();
        String by = input.substring(byIndex + 3).trim();
        Task task = new Deadline(desc, by);
        TaskList.addTask(task);
        Ui.showTaskAdded(task, TaskList.getSize());
    }
}
