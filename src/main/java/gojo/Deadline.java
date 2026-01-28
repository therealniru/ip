package gojo;

import java.time.LocalDateTime;

/**
 * Represents a task with a deadline in the gojo.Gojo application.
 * A deadline task has a description and a specific date and time by which it
 * must
 * be completed.
 */
public class Deadline extends Task {

    /** The deadline date and time for the task. */
    protected LocalDateTime by;

    /**
     * Constructs a new gojo.Deadline task.
     *
     * @param description The description of the task.
     * @param by          The deadline date string (e.g., "2/12/2019 1800",
     *                    "tomorrow").
     * @throws ChatbotExceptions If the date format is invalid.
     */
    public Deadline(String description, String by) throws ChatbotExceptions {
        super(description);
        this.by = DateParser.parseDateTime(by);
    }

    /**
     * Returns a string representation of the deadline task.
     * The format is "[D][Status] Description (by: MMM d yyyy HH:mm)".
     *
     * @return The string representation of the deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateParser.formatDateTime(by) + ")";
    }

    /**
     * Formats the deadline task data for file storage.
     * The format is "D | Status | Description | yyyy-MM-dd HHmm".
     *
     * @return A formatted string suitable for saving to a file.
     */
    @Override
    public String toFileFormat() {
        return "D" + super.toFileFormat() + " | " + DateParser.toFileString(by);
    }
}
