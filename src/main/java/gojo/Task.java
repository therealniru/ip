package gojo;

/**
 * Represents a generic task in the Gojo application.
 * A task has a description and a completion status.
 * It serves as the base class for more specific task types like Todo, Deadline,
 * and Event.
 */
public class Task {
    /** The description of the task. */
    protected String description;

    /** The completion status of the task (true if done, false otherwise). */
    protected boolean isDone;

    /**
     * Constructs a new Task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon for the task.
     * "X" indicates completion, while " " (space) indicates incomplete.
     *
     * @return A string representing the status icon.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns a string representation of the task.
     * The format is "[Status] Description".
     *
     * @return The string representation of the task.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "]" + " " + description;
    }

    /**
     * Formats the task data for file storage.
     * The format is " | Status(0/1) | Description".
     *
     * @return A formatted string suitable for saving to a file.
     */
    public String toFileFormat() {
        return " | " + (isDone ? "1" : "0") + " | " + description;
    }
}