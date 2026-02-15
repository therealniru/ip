package gojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class StorageTest {

    private static final String TEMP_FILE_PATH = "temp_storage_test.txt";

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEMP_FILE_PATH));
    }

    @Test
    public void saveAndLoad_validTasks_success() throws ChatbotExceptions {
        Storage storage = new Storage(TEMP_FILE_PATH);
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("read book"));
        tasks.add(new Deadline("submit report", "25/12/2025 1800"));

        // Save
        storage.save(tasks);

        // Verify file exists
        File file = new File(TEMP_FILE_PATH);
        assertTrue(file.exists());

        // Load
        List<Task> loadedTasks = storage.load();
        assertEquals(2, loadedTasks.size());
        assertEquals("[T][ ] read book", loadedTasks.get(0).toString());
        assertEquals("[D][ ] submit report (by: Dec 25 2025 18:00)", loadedTasks.get(1).toString());
    }

    @Test
    public void load_nonExistentFile_returnsEmptyList() {
        Storage storage = new Storage("non_existent_file.txt");
        List<Task> loadedTasks = storage.load();
        assertEquals(0, loadedTasks.size());
    }
}
