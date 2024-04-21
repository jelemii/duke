package tasks;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void loadTasks(ArrayList<Task> tasks) {
        if (tasks != null) {
            this.tasks.addAll(tasks);
        }
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public int getSize() {
        return tasks.size();
    }

    public void deleteTask(int index) {
        tasks.remove(index);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public boolean isDuplicate(String arguments) {
        for (Task task : tasks) {
            if (task.description.equals(arguments)) {
                return true;
            }
        }
        return false;
    }
}
