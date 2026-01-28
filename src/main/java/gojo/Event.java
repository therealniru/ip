package gojo;

import java.time.LocalDateTime;

/**
 * Represents an event task in the Gojo application.
 * An event task has a description and occurs within a specific time range
 * (from a start time to an end time).
 */
public class Event extends Task {

    /** The start time of the event. */
    protected LocalDateTime from;

    /** The end time of the event. */
    protected LocalDateTime to;

    /**
     * Constructs a new Event task.
     *
     * @param description The description of the event.
     * @param from        The start time of the event (e.g., "2/12/2019 1400",
     *                    "tomorrow").
     * @param to          The end time of the event.
     * @throws ChatbotExceptions If the date format is invalid.
     */
    public Event(String description, String from, String to) throws ChatbotExceptions {
        super(description);
        this.from = DateParser.parseDateTime(from);
        this.to = DateParser.parseDateTime(to);
    }

    /**
     * Returns a string representation of the event task.
     * The format is "[E][Status] Description (from: start to: end)".
     *
     * @return The string representation of the event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateParser.formatDateTime(from) + " to: "
                + DateParser.formatDateTime(to) + ")";
    }

    /**
     * Formats the event task data for file storage.
     * The format is "E | Status | Description | From | To".
     *
     * @return A formatted string suitable for saving to a file.
     */
    @Override
    public String toFileFormat() {
        return "E" + super.toFileFormat() + " | " + DateParser.toFileString(from) + " | " + DateParser.toFileString(to);
    }
}
