public class Ui {

    public static void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println(taskCount + ". " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public static void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        System.out.println("Now you have " + (taskCount - 1) + " tasks in the list.");
    }

    public void greetUser() {
        System.out.println("Hello! I'm jelemiiBot\n"
                + "What can I do for you?\n");
    }

    public void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void fileNotFoundError() {
        System.out.println("""

                Existing file not found. A new file will be created.
                Creating new file...
                """);
    }

    public void showList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.println((i + 1) + ". " + tasks.getTask(i).toString());
        }
    }

    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }

    public void showGoodbyeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}
