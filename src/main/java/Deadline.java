/**
 * Represents a task with a deadline in the Gojo application.
 * A deadline task has a description and a specific date/time by which it must
 * be completed.
 */
public class Deadline extends Task {

    /** The deadline date/time for the task. */
    protected String by;

    /**
     * Constructs a new Deadline task.
     *
     * @param description The description of the task.
     * @param by          The deadline date/time.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string representation of the deadline task.
     * The format is "[D][Status] Description (by: Deadline)".
     *
     * @return The string representation of the deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * Formats the deadline task data for file storage.
     * The format is "D | Status | Description | Deadline".
     *
     * @return A formatted string suitable for saving to a file.
     */
    @Override
    public String toFileFormat() {
        return "D" + super.toFileFormat() + " | " + by;
    }
}
