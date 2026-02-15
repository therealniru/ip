package gojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    public void toString_validInput_success() throws ChatbotExceptions {
        Event event = new Event("project meeting", "25/12/2025 1400", "25/12/2025 1600");
        assertEquals("[E][ ] project meeting (from: Dec 25 2025 14:00 to: Dec 25 2025 16:00)", event.toString());
    }

    @Test
    public void toFileFormat_validInput_success() throws ChatbotExceptions {
        Event event = new Event("project meeting", "25/12/2025 1400", "25/12/2025 1600");
        assertEquals("E | 0 | project meeting | 2025-12-25 1400 | 2025-12-25 1600", event.toFileFormat());
    }

    @Test
    public void constructor_invalidDates_throwsException() {
        assertThrows(ChatbotExceptions.class, () -> new Event("project meeting", "invalid", "25/12/2025 1600"));
    }
}
