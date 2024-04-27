package tasks;

/**
 * Represents a Task in a task list. Super class to different task type such as Todo,Deadline and Event.
 */
public class Task {

    protected String description;
    protected boolean isDone;
    protected String tag;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.tag = null;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getDescription() {
        return description;
    }

    public void markAsDone() {
        assert !isDone : "Task should not already been marked as done.";
        this.isDone = true;
    }

    public void unmarkAsDone() {
        assert isDone : "Task should have been marked as done to unmark.";
        this.isDone = false;
    }

    public void addTag(String tag) {
        assert tag != null : "Tag to be added should not be empty.";
        this.tag = tag;
    }

    public void unTag() {
        this.tag = null;
    }

    public boolean hasTag() {
        return this.tag != null;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        //add a space before a tag in tagString instead of in return statement
        //to avoid having extra space if there is no tag
        String tagString = (tag == null) ? "" : (" " + tag);
        return "[" + getStatusIcon() + "] " + description + tagString;
    }
}
