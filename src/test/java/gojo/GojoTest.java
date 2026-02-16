package gojo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class GojoTest {

    @TempDir
    Path tempDir;

    @Test
    public void testAddAndList() {
        Path tempFile = tempDir.resolve("gojo_test.txt");
        Gojo gojo = new Gojo(tempFile.toString());

        // Add a todo
        String addResponse = gojo.getResponse("todo read book");
        assertTrue(addResponse.contains("Confirmed"), "Add response should confirm");
        assertTrue(addResponse.contains("1 tasks"), "Should have 1 task");

        // List
        String listResponse = gojo.getResponse("list");
        assertTrue(listResponse.contains("1. [T][ ] read book"), "List should contain the task");

        // Add another
        addResponse = gojo.getResponse("todo write code");
        assertTrue(addResponse.contains("2 tasks"), "Should have 2 tasks");

        // List again
        listResponse = gojo.getResponse("list");
        assertTrue(listResponse.contains("1. [T][ ] read book"), "List should contain task 1");
        assertTrue(listResponse.contains("2. [T][ ] write code"), "List should contain task 2");

        // Add third
        addResponse = gojo.getResponse("todo debug");
        assertTrue(addResponse.contains("3 tasks"), "Should have 2 tasks");

        // List again
        listResponse = gojo.getResponse("list");
        assertTrue(listResponse.contains("3. [T][ ] debug"), "List should contain task 3");
    }

    @Test
    public void testByeShortcuts() {
        Path tempFile = tempDir.resolve("gojo_test_bye.txt");
        Gojo gojo = new Gojo(tempFile.toString());

        // Test 'b'
        String response = gojo.getResponse("b");
        assertTrue(response.contains("Bye, until next time"), "Response to 'b' should be bye message");

        // Test 'quit'
        response = gojo.getResponse("quit");
        assertTrue(response.contains("Bye, until next time"), "Response to 'quit' should be bye message");
    }

    @Test
    public void testPersistenceAcrossRestarts() {
        Path tempFile = tempDir.resolve("gojo_test_persistence.txt");

        // First session: Add a task
        Gojo session1 = new Gojo(tempFile.toString());
        session1.getResponse("todo persistent task");

        // Simulate restart by creating a new Gojo instance with the same file
        Gojo session2 = new Gojo(tempFile.toString());
        String listResponse = session2.getResponse("list");

        // Verify the task is present in the second session
        assertTrue(listResponse.contains("persistent task"),
                "Task should be visible after restarting application (" + listResponse + ")");
    }
}
