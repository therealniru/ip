package gojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DateParserTest {

    @Test
    public void parseDateTime_validFormat_success() throws ChatbotExceptions {
        LocalDateTime date = DateParser.parseDateTime("25/12/2025 1800");
        assertEquals(LocalDateTime.of(2025, 12, 25, 18, 0), date);
    }

    @Test
    public void parseDateTime_dateOnly_defaultsToEndOfDay() throws ChatbotExceptions {
        LocalDateTime date = DateParser.parseDateTime("25/12/2025");
        assertEquals(LocalDateTime.of(2025, 12, 25, 23, 59), date);
    }

    @Test
    public void parseDateTime_invalidDate_throwsException() {
        assertThrows(ChatbotExceptions.class, () -> DateParser.parseDateTime("30/02/2025"));
    }

    @Test
    public void parseDateTime_invalidFormat_throwsException() {
        assertThrows(ChatbotExceptions.class, () -> DateParser.parseDateTime("invalid-date"));
    }

    @Test
    public void parseDateTime_emptyString_throwsException() {
        assertThrows(ChatbotExceptions.class, () -> DateParser.parseDateTime(""));
    }
}
