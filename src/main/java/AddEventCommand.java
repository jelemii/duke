public class AddEventCommand {
    public static void addEvent(String input) {
        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");
        String desc = input.substring(6, fromIndex).trim();
        String from = input.substring(fromIndex + 5, toIndex).trim();
        String to = input.substring(toIndex + 3).trim();
        Task task = new Event(desc, from, to);
        TaskList.addTask(task);
        // System.out.println("Got it. I've added this task:");
        // System.out.println((taskList.size()) + ". " + taskList.get(taskList.size() - 1).toString());
        //  System.out.println("Now you have " + taskList.size() + " tasks in the list.");
        Ui.showTaskAdded(task, TaskList.getSize());
    }
}
