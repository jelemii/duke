package tasks;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Takes in a list of task to load into a task list.
     *
     * @param tasks The list of tasks to be added into the task list.
     */
    public void loadTasks(ArrayList<Task> tasks) {
        if (tasks != null) {
            this.tasks.addAll(tasks);
        }
    }

    /**
     * Adds a task into the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Find a task using its index in the task list.
     *
     * @param index The index of the task in the task list.
     * @return The task at the index of the task list.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Get the number of tasks in the list.
     *
     * @return The number of tasks in the task list.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Delete a task based on its index in the task list.
     *
     * @param index The index number of the task to be deleted.
     */
    public void deleteTask(int index) {
        tasks.remove(index);
    }

    /**
     * Gets all tasks containing in the list.
     *
     * @return A new array list which consist of all the tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Checks for duplicates in the current task list.
     *
     * @param arguments Contains the description of the task to be compared to the tasks in the task list.
     * @return True if the task already exists in the list. If not, return false
     */
    public boolean isDuplicate(String arguments) {
        for (Task task : tasks) {
            if (task.description.equals(arguments)) {
                return true;
            }
        }
        return false;
    }
}
