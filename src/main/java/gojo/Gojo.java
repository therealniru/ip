
package gojo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The entry point for the gojo.Gojo chatbot application.
 * gojo.Gojo is a CLI-based task manager that helps users track todos,
 * deadlines, and events.
 * It supports commands to list, mark, unmark, delete, and add various types of
 * tasks.
 *
 * <p>
 * This class orchestrates the interaction between the User Interface (Ui),
 * the Storage system, and the TaskList manager.
 * </p>
 */
public class Gojo {
    // Path to the file where tasks are persisted.
    private static final String FILE_PATH = "data/gojo.txt";

    // The list of tasks currently managed by the application.
    private TaskList tasks;

    // The user interface handler for input and output.
    private UI ui;

    // The storage handler for loading and saving tasks tasks to/from the hard disk.
    private Storage storage;

    /**
     * Constructs a new Gojo application instance.
     * Initializes the UI, Storage, and attempts to load existing tasks.
     * If loading fails, it starts with an empty task list.
     */
    public Gojo() {
        ui = new UI();
        storage = new Storage(FILE_PATH);
        // Attempt to load tasks from the defined file path
        tasks = new TaskList(storage.load());
    }

    /**
     * Processes the user input and returns a response string.
     *
     * @param input The user's input command.
     * @return The response from the chatbot.
     */
    public String getResponse(String input) {
        try {
            // Ignore empty inputs
            if (input.trim().isEmpty()) {
                return "";
            }

            // Parse the command and arguments separately
            Command command = Parser.parseCommand(input);
            assert command != null : "Command should not be null";
            String arguments = Parser.getArguments(input);

            StringBuilder response = new StringBuilder();

            // Handle the command based on its type
            switch (command) {
            case BYE:
                return "Dismissed.";

            case LIST:
                response.append("Here are the tasks in your list:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    response.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
                }
                break;

            case UNMARK:
                if (arguments.isEmpty()) {
                    throw new ChatbotExceptions("Please specify a task number to unmark.");
                }
                int taskNumber = Parser.parseIndex(arguments);
                Task task = tasks.get(taskNumber);
                task.markAsNotDone();
                response.append("OK, I've marked this task as not done yet:\n");
                response.append(task.toString());
                storage.save(tasks.getAllTasks());
                break;

            case MARK:
                if (arguments.isEmpty()) {
                    throw new ChatbotExceptions("Please specify a task number to mark.");
                }
                int markIndex = Parser.parseIndex(arguments);
                Task markTask = tasks.get(markIndex);
                markTask.markAsDone();
                response.append("Impressive. You have completed the task:\n");
                response.append(markTask.toString());
                storage.save(tasks.getAllTasks());
                break;

            case TODO:
                if (tasks.size() >= 100) {
                    return "Cannot add more than 100 items";
                }
                if (arguments.isEmpty()) {
                    throw new ChatbotExceptions("OOPS!!! The description of a todo cannot be empty.");
                }
                int initialSize = tasks.size(); // Capture size before
                Task newTodo = new Todo(arguments.trim());
                tasks.add(newTodo);
                assert tasks.size() == initialSize + 1 : "Task list size should increase by 1";
                response.append("Confirmed. I have added this to your schedule:\n");
                response.append("  ").append(newTodo).append("\n");
                response.append("Now you have ").append(tasks.size()).append(" tasks in the list.");
                storage.save(tasks.getAllTasks());
                break;

            case DEADLINE:
                if (tasks.size() >= 100) {
                    return "Cannot add more than 100 items";
                }
                if (arguments.isEmpty()) {
                    throw new ChatbotExceptions("OOPS!!! The description of a deadline cannot be empty.");
                }
                String[] parts = arguments.split(" /by ");
                if (parts.length < 2) {
                    throw new ChatbotExceptions("OOPS!!! The deadline cannot be empty.");
                }
                String description = parts[0].trim();
                if (description.length() == 0) {
                    throw new ChatbotExceptions("OOPS!!! The description of a deadline cannot be empty.");
                }
                String by = parts[1].trim();
                Task newDeadline = new Deadline(description, by);
                tasks.add(newDeadline);
                response.append("Confirmed. I have added this to your schedule:\n");
                response.append("  ").append(newDeadline).append("\n");
                response.append("Now you have ").append(tasks.size()).append(" tasks in the list.");
                storage.save(tasks.getAllTasks());
                break;

            case EVENT:
                if (tasks.size() >= 100) {
                    return "Cannot add more than 100 items";
                }
                if (arguments.isEmpty()) {
                    throw new ChatbotExceptions("OOPS!!! The description of a event cannot be empty.");
                }
                String[] eventParts = arguments.split(" /from ");
                if (eventParts.length < 2) {
                    throw new ChatbotExceptions("OOPS!!! The event cannot be empty.");
                }
                String eventDesc = eventParts[0].trim();
                if (eventDesc.length() == 0) {
                    throw new ChatbotExceptions("OOPS!!! The description of a event cannot be empty.");
                }
                String[] timeParts = eventParts[1].split(" /to ");
                if (timeParts.length < 2) {
                    throw new ChatbotExceptions("OOPS!!! The event time is missing.");
                }
                String from = timeParts[0].trim();
                String to = timeParts[1].trim();
                Task newEvent = new Event(eventDesc, from, to);
                tasks.add(newEvent);
                response.append("Confirmed. I have added this to your schedule:\n");
                response.append("  ").append(newEvent).append("\n");
                response.append("Now you have ").append(tasks.size()).append(" tasks in the list.");
                storage.save(tasks.getAllTasks());
                break;

            case DELETE:
                if (arguments.isEmpty()) {
                    throw new ChatbotExceptions("Please specify a task number to delete.");
                }
                int deleteIndex = Parser.parseIndex(arguments);
                Task removedTask = tasks.delete(deleteIndex);
                response.append("I have removed that task. It no longer exists:\n");
                response.append("  ").append(removedTask).append("\n");
                response.append("Now you have ").append(tasks.size()).append(" tasks in the list.");
                storage.save(tasks.getAllTasks());
                break;

            case SCHEDULE:
                if (arguments.isEmpty()) {
                    throw new ChatbotExceptions("Please specify a date to view the schedule.");
                }
                LocalDateTime scheduleDate = DateParser.parseDateTime(arguments);
                LocalDate queryDate = scheduleDate.toLocalDate();

                response.append("Tasks for ").append(queryDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")))
                        .append(":\n");

                boolean found = false;
                for (Task t : tasks.getAllTasks()) {
                    if (t instanceof Deadline) {
                        Deadline d = (Deadline) t;
                        if (d.by.toLocalDate().equals(queryDate)) {
                            response.append("  [D] ").append(d.description).append(" (due: ")
                                    .append(DateParser.formatDateTime(d.by)).append(")\n");
                            found = true;
                        }
                    } else if (t instanceof Event) {
                        Event e = (Event) t;
                        LocalDate startDate = e.from.toLocalDate();
                        LocalDate endDate = e.to.toLocalDate();
                        if (!queryDate.isBefore(startDate) && !queryDate.isAfter(endDate)) {
                            response.append("  [E] ").append(e.description).append(" (from: ")
                                    .append(DateParser.formatDateTime(e.from)).append(" to: ")
                                    .append(DateParser.formatDateTime(e.to)).append(")\n");
                            found = true;
                        }
                    }
                }
                if (!found) {
                    response.append("  No tasks scheduled for this date.");
                }
                break;

            case FIND:
                if (arguments.isEmpty()) {
                    throw new ChatbotExceptions("Please specify a keyword to search for.");
                }
                String keyword = arguments.trim();
                List<Task> matchingTasks = tasks.findTasks(keyword);

                if (matchingTasks.isEmpty()) {
                    throw new ChatbotExceptions("No tasks matching '" + keyword + "' found.");
                }

                response.append("Here are the matching tasks in your list:\n");
                for (int i = 0; i < matchingTasks.size(); i++) {
                    response.append((i + 1)).append(".").append(matchingTasks.get(i)).append("\n");
                }
                break;

            default:
                assert false : "Command " + command + " not recognized";
                throw new IllegalStateException("I do not understand that command. Please be precise.");
            }
            return response.toString();
        } catch (ChatbotExceptions ce) {
            return ce.getMessage();
        } catch (Exception e) {
            return "Something went wrong: " + e.getMessage();
        }
    }

    /**
     * The main method that initializes the chatbot and handles the user input loop.
     * This method runs until the user issues the 'BYE' command.
     */
    public void run() {
        ui.showWelcome();

        String input;
        // Main application loop
        while (true) {
            try {
                // Read the next command line from the user
                input = ui.readCommand();

                // Ignore empty inputs
                if (input.trim().isEmpty()) {
                    continue;
                }

                // Use getResponse to get the logic output
                String response = getResponse(input);
                ui.showMessage(response);

                // Check if we should exit separately because getResponse returns a string, not
                // control flow
                Command command = Parser.parseCommand(input);
                if (command == Command.BYE) {
                    ui.showLine();
                    return;
                }

            } catch (Exception e) {
                // catch-all for other unexpected exceptions
                ui.showError("Something went wrong: " + e.getMessage());
            } finally {
                // Ensure the separator line is always drawn
                ui.showLine();
            }
        }
    }

    /**
     * Main entry point of the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Gojo().run();
    }
}
