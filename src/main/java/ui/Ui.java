package ui;

import storage.Storage;
import tasks.*;

import java.util.ArrayList;

/**
 * The Ui class manages interaction with the user
 * such as displaying messages to the user.
 */
public class Ui {
    /**
     * Tells the user that the task was added.
     *
     * @param task      The new task that was added.
     * @param taskCount The total number of tasks after adding the new task.
     */
    public static void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println(taskCount + ". " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Tells the user that the task is deleted.
     *
     * @param task      The task that was deleted.
     * @param taskCount The total number of tasks after deletion.
     */
    public static void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        System.out.println("Now you have " + (taskCount - 1) + " tasks in the list.");
    }

    /**
     * Greets the user.
     */
    public void greetUser() {
        System.out.println("\nHello! I'm jelemiiBot\n"
                + "What can I do for you?\n");
    }

    /**
     * Display an error message to the user.
     *
     * @param errorMessage The error message to be displayed.
     */
    public void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    /**
     * Tells the user that there is no existing file.
     */
    public void fileNotFoundError() {
        System.out.println("""

                Existing file not found. A new file will be created.
                Creating new file...
                """);
    }

    /**
     * Display all the current tasks in the list.
     *
     * @param tasks the tasks in the task list to be displayed.
     */
    public void showList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.println((i + 1) + ". " + tasks.getTask(i).toString());
        }
    }

    /**
     * Tells the user that the task is marked as done.
     *
     * @param task The task that is marked done.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    /**
     * Tells the user that the task is unmarked as done.
     *
     * @param task The task that is unmarked as done.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }

    /**
     * Display a goodbye message to the user.
     */
    public void showGoodbyeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Checks for upcoming deadline or event tasks and display them to remind the user.
     * @param taskList The list of current tasks.
     */
    public void showUpcomingTasks(TaskList taskList) {
        ArrayList<Deadline> upcomingDeadlinesDue = taskList.upcomingDeadlinesDue();
        ArrayList<Event> upcomingEvents = taskList.upcomingEvents();

        if(!(upcomingDeadlinesDue.isEmpty() && upcomingEvents.isEmpty())) {
            System.out.println("Reminder: You have upcoming Tasks.");
            if(!upcomingDeadlinesDue.isEmpty()) {
                System.out.println("\nUpcoming Deadlines: ");
                for(Deadline deadline : upcomingDeadlinesDue) {
                    System.out.println(deadline);
                }
            }
            if(!upcomingEvents.isEmpty()) {
                System.out.println("\nUpcoming Events: ");
                for(Event event : upcomingEvents) {
                    System.out.println(event);
                }
            }
        }

    }
}
