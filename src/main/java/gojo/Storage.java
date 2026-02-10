package gojo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.UncheckedIOException;

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
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return Files.lines(Paths.get(filePath))
                    .map(line -> {
                        try {
                            return parseTaskFromLine(line);
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private Task parseTaskFromLine(String line) throws Exception {
        // Split the line by " | " to extract task details
        // Format: Type | IsDone | Description [| Date/Time]
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = null;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                String by = parts[3];
                task = new Deadline(description, by);
                break;
            case "E":
                String from = parts[3];
                String to = parts[4];
                task = new Event(description, from, to);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        if (task != null && isDone) {
            task.markAsDone();
        }
        return task;
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
            tasks.stream()
                    .map(task -> task.toFileFormat() + System.lineSeparator())
                    .forEach(line -> {
                        try {
                            writer.write(line);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
            writer.close();
        } catch (IOException | UncheckedIOException e) {
            throw new ChatbotExceptions("Error saving data: " + e.getMessage());
        }
    }
}
