package gojo;

/**
 * Represents an event task in the Gojo application.
 * An event task has a description and occurs within a specific time range
 * (from a start time to an end time).
 */
public class Event extends Task {

    /** The start time of the event. */
    protected String from;

    /** The end time of the event. */
    protected String to;

    /**
     * Constructs a new Event task.
     *
     * @param description The description of the event.
     * @param from        The start time of the event.
     * @param to          The end time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the event task.
     * The format is "[E][Status] Description (from: start to: end)".
     *
     * @return The string representation of the event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Formats the event task data for file storage.
     * The format is "E | Status | Description | From | To".
     *
     * @return A formatted string suitable for saving to a file.
     */
    @Override
    public String toFileFormat() {
        return "E" + super.toFileFormat() + " | " + from + " | " + to;
    }
}
