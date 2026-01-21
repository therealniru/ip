/**
 * Represents a todo task in the Gojo application.
 * A todo task is a simple task with a description and no specific deadline or
 * time constraint.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo task with the specified description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the todo task.
     * The format is "[T][Status] Description".
     *
     * @return The string representation of the todo task.
     */
    @Override
    public String toString() {

        return "[T]" + super.toString();
    }

    /**
     * Formats the todo task data for file storage.
     * The format is "T | Status | Description".
     *
     * @return A formatted string suitable for saving to a file.
     */
    @Override
    public String toFileFormat() {

        return "T" + super.toFileFormat();
    }
}
