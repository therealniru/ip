import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline in the Gojo application.
 * A deadline task has a description and a specific date by which it must
 * be completed.
 */
public class Deadline extends Task {

    /** The deadline date for the task. */
    protected LocalDate by;

    /**
     * Constructs a new Deadline task.
     *
     * @param description The description of the task.
     * @param by          The deadline date in yyyy-MM-dd format.
     * @throws ChatbotExceptions If the date format is invalid.
     */
    public Deadline(String description, String by) throws ChatbotExceptions {
        super(description);
        try {
            this.by = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            throw new ChatbotExceptions("OOPS!!! Invalid date format. Please use yyyy-MM-dd (e.g., 2019-12-02).");
        }
    }

    /**
     * Returns a string representation of the deadline task.
     * The format is "[D][Status] Description (by: MMM d yyyy)".
     *
     * @return The string representation of the deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    /**
     * Formats the deadline task data for file storage.
     * The format is "D | Status | Description | yyyy-MM-dd".
     *
     * @return A formatted string suitable for saving to a file.
     */
    @Override
    public String toFileFormat() {
        return "D" + super.toFileFormat() + " | " + by;
    }
}
