package gojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the list of tasks and supports operations to add, delete, and
 * manage tasks.
 * <p>
 * This class serves as a wrapper around the underlying List data structure,
 * providing domain-specific methods for task manipulation.
 * </p>
 */
public class TaskList {
    private List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(List<Task> tasks) {
        assert tasks != null : "Initial task list cannot be null";
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        assert task != null : "Cannot add a null task to the list";
        tasks.add(task);
    }

    /**
     * Removes a task from the list at the specified index.
     *
     * @param index The zero-based index of the task to remove.
     * @return The removed task.
     * @throws ChatbotExceptions If the index is out of bounds (less than 0 or >=
     *                           size).
     */
    public Task delete(int index) throws ChatbotExceptions {
        if (index < 0 || index >= tasks.size()) {
            throw new ChatbotExceptions("OOPS!!! The task number is out of bounds.");
        }
        // If we passed the check above, the index MUST be valid.
        assert index >= 0 && index < tasks.size() : "Index should be valid for deletion";
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the list at the specified index.
     *
     * @param index The zero-based index of the task so retrieve.
     * @return The task at the specified index.
     * @throws ChatbotExceptions If the index is out of bounds.
     */
    public Task get(int index) throws ChatbotExceptions {
        if (index < 0 || index >= tasks.size()) {
            throw new ChatbotExceptions("OOPS!!! The task number is out of bounds.");
        }
        assert index >= 0 && index < tasks.size() : "Index should be valid for retrieval";
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying list of tasks.
     * Useful for Storage to save data.
     *
     * @return The list of tasks.
     */
    public List<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Finds tasks whose description contains the specified keyword.
     *
     * @param keyword The keyword to search for.
     * @return A list of tasks matching the keyword.
     */
    public List<Task> findTasks(String keyword) {
        return tasks.stream()
                .filter(task -> task.description.contains(keyword))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Checks if a task with the same description and parameters already exists.
     *
     * @param task The task to check.
     * @return true if a duplicate exists, false otherwise.
     */
    public boolean isDuplicate(Task task) {
        return tasks.stream().anyMatch(t -> t.toString().equals(task.toString()));
    }
}
