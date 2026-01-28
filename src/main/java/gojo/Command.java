package gojo;

/**
 * Represents the valid commands that can be issued to the gojo.Gojo chatbot.
 * These commands control the various operations such as adding tasks,
 * listing tasks, marking tasks as done/undone, deleting tasks, and exiting.
 */
public enum Command {
    /** Terminates the application. */
    BYE,
    /** Lists all currently tracked tasks. */
    LIST,
    /** Marks a specific task as done. */
    MARK,
    /** Marks a specific task as not done. */
    UNMARK,
    /** Adds a new Todo task. */
    TODO,
    /** Adds a new Deadline task. */
    DEADLINE,
    /** Adds a new Event task. */
    EVENT,
    /** Deletes a specific task. */
    DELETE
}
