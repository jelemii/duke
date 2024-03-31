import java.util.ArrayList;

public class TaskList {
    public static ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        TaskList.tasks = tasks;
    }

    public static void addTask(Task task) {
        tasks.add(task);
    }

    public static Task getTask(int index) {
        return tasks.get(index);
    }

    public static int getSize() {
        return tasks.size();
    }

    public static void deleteTask(int index) {
        tasks.remove(index);
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public void markTask(Task task) {
        task.markAsDone();
    }
    public void unmarkTask(Task task) {
        task.unmarkAsDone();
    }
}
