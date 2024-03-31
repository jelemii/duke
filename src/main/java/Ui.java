public class Ui {

    public void greetUser() {
        System.out.println("Hello! I'm jelemiiBot\n"
                + "What can I do for you?\n");
    }

    public void showErrorMessage(String errorMessage){
        System.out.println(errorMessage);
    }

    public void fileNotFoundError() {
        System.out.println("Existing file not found. A new file will be created.\n"
                + "Creating new file...\n");
    }

    public static void showTaskAdded(Task task, int taskCount){
        System.out.println("Got it. I've added this task:");
        System.out.println(taskCount + ". " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }
    public static void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        System.out.println("Now you have " + (taskCount-1) + " tasks in the list.");
    }
    public static void showList(){
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < TaskList.tasks.size(); i++) {
            System.out.println((i+1) + ". " + TaskList.tasks.get(i).toString());
        }
    }
    public static void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }
    public static void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }
    public void showGoodbyeMessage(){
        System.out.println("Bye. Hope to see you again soon!");
    }
}
