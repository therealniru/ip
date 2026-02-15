package gojo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private static final String MSG_DISMISSED = "Dismissed.";
    private static final String MSG_LIST_HEADER = "My Six Eyes see everything. Here are your tasks:";
    private static final String MSG_UNMARK_ERROR = "Please specify a task number to unmark. Don't make me repeat myself.";
    private static final String MSG_MARK_ERROR = "Please specify a task number to mark. Focus.";
    private static final String MSG_TODO_EMPTY = "OOPS!!! A todo cannot be empty. Are you failing to control your cursed energy?";
    private static final String MSG_DEADLINE_EMPTY = "OOPS!!! Intricate details matter. The description cannot be empty.";
    private static final String MSG_DEADLINE_TIME_EMPTY = "OOPS!!! The deadline cannot be empty. Time is relative, but not optional.";
    private static final String MSG_EVENT_EMPTY = "OOPS!!! The description of an event cannot be empty.";
    private static final String MSG_EVENT_TIME_EMPTY = "OOPS!!! The event time is missing. A sorcerer must be punctual.";
    private static final String MSG_DELETE_ERROR = "Please specify a task number to delete. Exorcise it properly.";
    private static final String MSG_SCHEDULE_ERROR = "Please specify a date to view the schedule.";
    private static final String MSG_FIND_ERROR = "Please specify a keyword to search for. Use your eyes.";
    private static final String MSG_MAX_TASKS = "Cannot add more than 100 items. Your domain is too crowded.";

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
            if (input.trim().isEmpty()) {
                return "";
            }

            Command command = Parser.parseCommand(input);
            String arguments = Parser.getArguments(input);

            switch (command) {
                case BYE:
                    return handleBye();
                case LIST:
                    return handleList();
                case UNMARK:
                    return handleUnmark(arguments);
                case MARK:
                    return handleMark(arguments);
                case TODO:
                    return handleTodo(arguments);
                case DEADLINE:
                    return handleDeadline(arguments);
                case EVENT:
                    return handleEvent(arguments);
                case DELETE:
                    return handleDelete(arguments);
                case SCHEDULE:
                    return handleSchedule(arguments);
                case FIND:
                    return handleFind(arguments);
                default:
                    throw new IllegalStateException("I do not understand that command. Please be precise.");
            }
        } catch (ChatbotExceptions ce) {
            return ce.getMessage();
        } catch (Exception e) {
            return "Something went wrong: " + e.getMessage();
        }
    }

    private String handleBye() {
        return "Bye! Don't let the curses bite. Stay Limitless \u267E\uFE0F";
    }

    private String handleList() {
        StringBuilder response = new StringBuilder();
        response.append(MSG_LIST_HEADER);
        if (tasks.size() > 0) {
            response.append("\n");
        }
        IntStream.range(0, tasks.size())
                .forEach(i -> {
                    try {
                        response.append(i + 1).append(". ").append(tasks.get(i));
                        if (i < tasks.size() - 1) {
                            response.append("\n");
                        }
                    } catch (ChatbotExceptions e) {
                        e.printStackTrace();
                    }
                });
        return response.toString();
    }

    private String handleUnmark(String arguments) throws ChatbotExceptions {
        if (arguments.isEmpty()) {
            throw new ChatbotExceptions(MSG_UNMARK_ERROR);
        }
        int taskNumber = Parser.parseIndex(arguments);
        Task task = tasks.get(taskNumber);
        task.markAsNotDone();
        storage.save(tasks.getAllTasks());
        return "Reversing Cursed Technique. Task marked as not done:\n" + task.toString();
    }

    private String handleMark(String arguments) throws ChatbotExceptions {
        assert arguments != null : "Arguments cannot be null";
        if (arguments.isEmpty()) {
            throw new ChatbotExceptions(MSG_MARK_ERROR);
        }
        int markIndex = Parser.parseIndex(arguments);
        Task markTask = tasks.get(markIndex);
        markTask.markAsDone();
        storage.save(tasks.getAllTasks());
        return "Hollow... Purple! Task obliterated (completed):\n" + markTask.toString();
    }

    private String handleTodo(String arguments) throws ChatbotExceptions {
        assert arguments != null : "Arguments cannot be null";
        if (tasks.size() >= 100) {
            return MSG_MAX_TASKS;
        }
        if (arguments.isEmpty()) {
            throw new ChatbotExceptions(MSG_TODO_EMPTY);
        }
        Task newTodo = new Todo(arguments.trim());
        tasks.add(newTodo);
        storage.save(tasks.getAllTasks());
        return formatAddResponse(newTodo);
    }

    private String handleDeadline(String arguments) throws ChatbotExceptions {
        assert arguments != null : "Arguments cannot be null";
        if (tasks.size() >= 100) {
            return MSG_MAX_TASKS;
        }
        if (arguments.isEmpty()) {
            throw new ChatbotExceptions(MSG_DEADLINE_EMPTY);
        }
        String[] parts = arguments.split(" /by ");
        if (parts.length < 2) {
            throw new ChatbotExceptions(MSG_DEADLINE_TIME_EMPTY);
        }
        String description = parts[0].trim();
        if (description.isEmpty()) {
            throw new ChatbotExceptions(MSG_DEADLINE_EMPTY);
        }
        String by = parts[1].trim();
        Task newDeadline = new Deadline(description, by);
        tasks.add(newDeadline);
        storage.save(tasks.getAllTasks());
        return formatAddResponse(newDeadline);
    }

    private String handleEvent(String arguments) throws ChatbotExceptions {
        assert arguments != null : "Arguments cannot be null";
        if (tasks.size() >= 100) {
            return MSG_MAX_TASKS;
        }
        if (arguments.isEmpty()) {
            throw new ChatbotExceptions(MSG_EVENT_EMPTY);
        }
        String[] eventParts = arguments.split(" /from ");
        if (eventParts.length < 2) {
            throw new ChatbotExceptions(MSG_EVENT_EMPTY);
        }
        String eventDesc = eventParts[0].trim();
        if (eventDesc.isEmpty()) {
            throw new ChatbotExceptions(MSG_EVENT_EMPTY);
        }
        String[] timeParts = eventParts[1].split(" /to ");
        if (timeParts.length < 2) {
            throw new ChatbotExceptions(MSG_EVENT_TIME_EMPTY);
        }
        String from = timeParts[0].trim();
        String to = timeParts[1].trim();
        Task newEvent = new Event(eventDesc, from, to);
        tasks.add(newEvent);
        storage.save(tasks.getAllTasks());
        return formatAddResponse(newEvent);
    }

    private String handleDelete(String arguments) throws ChatbotExceptions {
        assert arguments != null : "Arguments cannot be null";
        if (arguments.isEmpty()) {
            throw new ChatbotExceptions(MSG_DELETE_ERROR);
        }
        int deleteIndex = Parser.parseIndex(arguments);
        Task removedTask = tasks.delete(deleteIndex);
        storage.save(tasks.getAllTasks());
        return "Noted. I've removed this task:\n" + "  " + removedTask + "\n" + "Now you have "
                + tasks.size() + " tasks in the list.";
    }

    private String handleSchedule(String arguments) throws ChatbotExceptions {
        assert arguments != null : "Arguments cannot be null";
        if (arguments.isEmpty()) {
            throw new ChatbotExceptions(MSG_SCHEDULE_ERROR);
        }
        LocalDateTime scheduleDate = DateParser.parseDateTime(arguments);
        LocalDate queryDate = scheduleDate.toLocalDate();

        StringBuilder response = new StringBuilder();
        response.append("Tasks for ").append(queryDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")))
                .append(":\n");

        String scheduledTasks = tasks.getAllTasks().stream()
                .filter(t -> isTaskScheduledOnDate(t, queryDate))
                .map(this::formatScheduledTask)
                .collect(Collectors.joining());

        if (!scheduledTasks.isEmpty()) {
            response.append(scheduledTasks);
        } else {
            response.append("  No tasks scheduled for this date.");
        }
        return response.toString();
    }

    private boolean isTaskScheduledOnDate(Task t, LocalDate queryDate) {
        if (t instanceof Deadline) {
            return ((Deadline) t).by.toLocalDate().equals(queryDate);
        } else if (t instanceof Event) {
            Event e = (Event) t;
            try {
                LocalDateTime from = DateParser.parseDateTime(e.from);
                LocalDateTime to = DateParser.parseDateTime(e.to);
                return !queryDate.isBefore(from.toLocalDate()) && !queryDate.isAfter(to.toLocalDate());
            } catch (ChatbotExceptions ex) {
                return false;
            }
        }
        return false;
    }

    private String formatScheduledTask(Task t) {
        if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return "  [D] " + d.description + " (due: " + DateParser.formatDateTime(d.by) + ")\n";
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return "  [E] " + e.description + " (from: " + e.from + " to: " + e.to + ")\n";
        }
        return "";
    }

    private String handleFind(String arguments) throws ChatbotExceptions {
        assert arguments != null : "Arguments cannot be null";
        if (arguments.isEmpty()) {
            throw new ChatbotExceptions(MSG_FIND_ERROR);
        }
        String keyword = arguments.trim();
        List<Task> matchingTasks = tasks.findTasks(keyword);

        if (matchingTasks.isEmpty()) {
            throw new ChatbotExceptions("No tasks matching '" + keyword + "' found.");
        }

        StringBuilder response = new StringBuilder();
        response.append("Here are the matching tasks in your list:\n");
        IntStream.range(0, matchingTasks.size())
                .forEach(i -> {
                    response.append(i + 1).append(".").append(matchingTasks.get(i));
                    if (i < matchingTasks.size() - 1) {
                        response.append("\n");
                    }
                });
        return response.toString();
    }

    private String formatAddResponse(Task task) {
        return "I've added this to the Infinite Void. Don't get lost:\n"
                + "  " + task + "\n"
                + "Now you have " + tasks.size()
                + " tasks in the list.";
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
