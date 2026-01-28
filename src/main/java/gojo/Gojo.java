
package gojo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

                // Parse the command and arguments separately
                Command command = Parser.parseCommand(input);
                String arguments = Parser.getArguments(input);

                // Handle the command based on its type
                switch (command) {
                    case BYE:
                        // Exit the application
                        ui.showMessage("Bye, until next time - Stay Limitless ♾️");
                        ui.showLine();
                        return;

                    case LIST:
                        // Display all tasks in the list
                        ui.showMessage("Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            // Display 1-based index and task details
                            ui.showMessage((i + 1) + ". " + tasks.get(i));
                        }
                        break;

                    case UNMARK:
                        // Marks a task as incomplete
                        if (arguments.isEmpty()) {
                            throw new ChatbotExceptions("Please specify a task number to unmark.");
                        }
                        int taskNumber = Parser.parseIndex(arguments);
                        Task task = tasks.get(taskNumber);
                        task.markAsNotDone();
                        ui.showMessage("OK, I've marked this task as not done yet:");
                        ui.showMessage(task.toString());
                        // Save changes to storage immediately
                        storage.save(tasks.getAllTasks());
                        break;

                    case MARK:
                        // Marks a task as complete
                        if (arguments.isEmpty()) {
                            throw new ChatbotExceptions("Please specify a task number to mark.");
                        }

                        // Parse index and retrieve task
                        // Note: TaskList.get throws ChatbotExceptions if OOB.
                        int markIndex = Parser.parseIndex(arguments);
                        Task markTask = tasks.get(markIndex);
                        markTask.markAsDone();
                        ui.showMessage("Nice! I've marked this task as done:");
                        ui.showMessage(markTask.toString());
                        storage.save(tasks.getAllTasks());
                        break;

                    case TODO:
                        // Adds a new ToDo task
                        if (tasks.size() >= 100) {
                            ui.showMessage("Cannot add more than 100 items");
                        } else {
                            if (arguments.isEmpty()) {
                                throw new ChatbotExceptions("OOPS!!! The description of a todo cannot be empty.");
                            }
                            Task newTask = new Todo(arguments.trim());
                            tasks.add(newTask);
                            ui.showMessage("Got it. I've added this task:");
                            ui.showMessage("  " + newTask);
                            ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
                            storage.save(tasks.getAllTasks());
                        }
                        break;

                    case DEADLINE:
                        // Adds a new Deadline task
                        if (tasks.size() >= 100) {
                            ui.showMessage("Cannot add more than 100 items");
                        } else {
                            if (arguments.isEmpty()) {
                                throw new ChatbotExceptions("OOPS!!! The description of a deadline cannot be empty.");
                            }
                            // Split argument to separate description and date
                            String[] parts = arguments.split(" /by ");
                            if (parts.length < 2) {
                                throw new ChatbotExceptions("OOPS!!! The deadline cannot be empty.");
                            }
                            String description = parts[0].trim();
                            if (description.length() == 0) {
                                throw new ChatbotExceptions("OOPS!!! The description of a deadline cannot be empty.");
                            }
                            String by = parts[1].trim();
                            Task newTask = new Deadline(description, by);
                            tasks.add(newTask);
                            ui.showMessage("Got it. I've added this task:");
                            ui.showMessage("  " + newTask);
                            ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
                            storage.save(tasks.getAllTasks());
                        }
                        break;

                    case EVENT:
                        // Adds a new Event task
                        if (tasks.size() >= 100) {
                            ui.showMessage("Cannot add more than 100 items");
                        } else {
                            if (arguments.isEmpty()) {
                                throw new ChatbotExceptions("OOPS!!! The description of a event cannot be empty.");
                            }
                            // Split argument to check for /from delimiter
                            String[] parts = arguments.split(" /from ");
                            if (parts.length < 2) {
                                throw new ChatbotExceptions("OOPS!!! The event cannot be empty.");
                            }
                            String description = parts[0].trim();
                            if (description.length() == 0) {
                                throw new ChatbotExceptions("OOPS!!! The description of a event cannot be empty.");
                            }
                            // Split second part to check for /to delimiter
                            String[] timeParts = parts[1].split(" /to ");
                            if (timeParts.length < 2) {
                                throw new ChatbotExceptions("OOPS!!! The event time is missing.");
                            }
                            String from = timeParts[0].trim();
                            String to = timeParts[1].trim();
                            Task newTask = new Event(description, from, to);
                            tasks.add(newTask);
                            ui.showMessage("Got it. I've added this task:");
                            ui.showMessage("  " + newTask);
                            ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
                            storage.save(tasks.getAllTasks());
                        }
                        break;

                    case DELETE:
                        // Deletes a task from the list
                        if (arguments.isEmpty()) {
                            throw new ChatbotExceptions("Please specify a task number to delete.");
                        }
                        int deleteIndex = Parser.parseIndex(arguments);
                        Task removedTask = tasks.delete(deleteIndex);
                        ui.showMessage("Noted. I've removed this task:");
                        ui.showMessage("  " + removedTask);
                        ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
                        storage.save(tasks.getAllTasks());
                        break;

                    case SCHEDULE:
                        // Lists tasks scheduled for a specific date
                        if (arguments.isEmpty()) {
                            throw new ChatbotExceptions("Please specify a date to view the schedule.");
                        }

                        // Parse the date argument
                        LocalDateTime scheduleDate = DateParser.parseDateTime(arguments);
                        LocalDate queryDate = scheduleDate.toLocalDate();

                        ui.showMessage(
                                "Tasks for " + queryDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ":");

                        boolean found = false;
                        for (Task t : tasks.getAllTasks()) {
                            if (t instanceof Deadline) {
                                Deadline d = (Deadline) t;
                                if (d.by.toLocalDate().equals(queryDate)) {
                                    ui.showMessage("  [D] " + d.description + " (due: "
                                            + DateParser.formatDateTime(d.by) + ")");
                                    found = true;
                                }
                            } else if (t instanceof Event) {
                                Event e = (Event) t;
                                LocalDate startDate = e.from.toLocalDate();
                                LocalDate endDate = e.to.toLocalDate();
                                // Check if queryDate is within [startDate, endDate]
                                if (!queryDate.isBefore(startDate) && !queryDate.isAfter(endDate)) {
                                    ui.showMessage(
                                            "  [E] " + e.description + " (from: " + DateParser.formatDateTime(e.from)
                                                    + " to: " + DateParser.formatDateTime(e.to) + ")");
                                    found = true;
                                }
                            }
                        }

                        if (!found) {
                            ui.showMessage("  No tasks scheduled for this date.");
                        }
                        break;
                }
            } catch (ChatbotExceptions ce) {
                // specific chatbot exceptions are handled here
                ui.showError(ce.getMessage());
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
