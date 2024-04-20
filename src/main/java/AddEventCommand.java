public class AddEventCommand {
    public static void addEvent(String input) {
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");
        String desc = input.substring(6, fromIndex).trim();
        String from = input.substring(fromIndex + 5, toIndex).trim();
        String to = input.substring(toIndex + 3).trim();
        Task task = new Event(desc, from, to);
        TaskList.addTask(task);
        Ui.showTaskAdded(task, TaskList.getSize());
    }
}
