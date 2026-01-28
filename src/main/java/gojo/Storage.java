package gojo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles loading tasks from the file and saving tasks in the file.
 * <p>
 * This class abstracts the details of reading from and writing to the hard
 * disk.
 * It ensures that task data is persistent across application restarts.
 * </p>
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a new Storage instance.
     *
     * @param filePath The file path where tasks will be stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the data file.
     * <p>
     * If the file does not exist, it creates the directory structure if needed
     * and returns an empty list.
     * </p>
     *
     * @return The list of tasks loaded from the file.
     */
    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        try {
            // Check if file exists; if not, create parent directories and return empty list
            if (!file.exists()) {
                File directory = file.getParentFile();
                if (directory != null && !directory.exists()) {
                    directory.mkdirs();
                }
                return tasks; // Return empty list if file doesn't exist
            }

            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                try {
                    // Split the line by " | " to extract task details
                    // Format: Type | IsDone | Description [| Date/Time]
                    String[] parts = line.split(" \\| ");
                    String type = parts[0];
                    boolean isDone = parts[1].equals("1");
                    String description = parts[2];

                    Task task = null;
                    // Determine task type and create appropriate object
                    switch (type) {
                        case "T":
                            task = new Todo(description);
                            break;
                        case "D":
                            // Deadline format includes additional "by" date
                            String by = parts[3];
                            task = new Deadline(description, by);
                            break;
                        case "E":
                            // Event format includes additional "from" and "to" times
                            String from = parts[3];
                            String to = parts[4];
                            task = new Event(description, from, to);
                            break;
                    }

                    if (task != null) {
                        if (isDone) {
                            task.markAsDone();
                        }
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    System.out.println("Skipping corrupted line: " + line);
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Saves the list of tasks to the data file.
     * <p>
     * Overwrites the existing file with the current state of the task list.
     * </p>
     *
     * @param tasks The list of tasks to save.
     * @throws ChatbotExceptions If there are errors writing to the file.
     */
    public void save(List<Task> tasks) throws ChatbotExceptions {
        try {
            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks) {
                // Convert each task to its file storage format string
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            throw new ChatbotExceptions("Error saving data: " + e.getMessage());
        }
    }
}
