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
        this.isDone = true;
    }

    public void unmarkAsDone() {
        this.isDone = false;
    }

    public void addTag(String tag) {
        this.tag = tag;
    }

    public void unTag(String tag) {
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
