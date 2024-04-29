package tasks;

import parser.DateParser;

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
        assert task != null : "Task to be added to the list should not be null.";
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
     * Creates a list of upcoming deadline tasks that are due soon.
     * @return The list of upcoming deadline tasks.
     */
    public ArrayList<Deadline> upcomingDeadlinesDue() {
        ArrayList<Deadline> upcomingDeadlines = new ArrayList<>();
        for(Task task: tasks) {
            if (task.toString().charAt(1) == 'D' && DateParser.isUpcoming(((Deadline) task).getBy())) {
                upcomingDeadlines.add((Deadline) task);
            }
        }
        return upcomingDeadlines;
    }

    /**
     * Creates a list of upcoming event tasks that are starting soon.
     * @return The list of upcoming event tasks.
     */
    public ArrayList<Event> upcomingEvents() {
        ArrayList<Event> upcomingEvents = new ArrayList<>();
        for(Task task: tasks) {
            if (task.toString().charAt(1) == 'E' && DateParser.isUpcoming(((Event)task).getTo())) {
                upcomingEvents.add((Event) task);
            }
        }
        return upcomingEvents;
    }

    /**
     * Creates a list of task which matches the keyword.
     * @param keyword The keyword to find in the list.
     * @return A list of the tasks which matches the keyword.
     */
    public ArrayList<Task> matchingTasksFound(String keyword) {
        ArrayList<Task> matchingTasksFound = new ArrayList<>();
        for(Task task : tasks) {
            if(task.description.toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasksFound.add(task);
            } else if (task.hasTag() && task.getTag().contains(keyword.toLowerCase())) {
                matchingTasksFound.add(task);
            }
        }
        return matchingTasksFound;
    }
}
