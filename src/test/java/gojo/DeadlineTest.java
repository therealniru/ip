package gojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void toString_validInput_success() throws ChatbotExceptions {
        Deadline deadline = new Deadline("submit report", "25/12/2025 1800");
        assertEquals("[D][ ] submit report (by: Dec 25 2025 18:00)", deadline.toString());
    }

    @Test
    public void toFileFormat_validInput_success() throws ChatbotExceptions {
        Deadline deadline = new Deadline("submit report", "25/12/2025 1800");
        assertEquals("D | 0 | submit report | 2025-12-25 1800", deadline.toFileFormat());
    }

    @Test
    public void constructor_invalidDate_throwsException() {
        assertThrows(ChatbotExceptions.class, () -> new Deadline("submit report", "invalid-date"));
    }
}
