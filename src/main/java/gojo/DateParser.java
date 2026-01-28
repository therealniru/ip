package gojo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class for parsing and formatting dates and times in the Gojo
 * application.
 * Supports flexible formats including standard date-time patterns and natural
 * language keywords
 * like "today" and "tomorrow".
 */
public class DateParser {

    // List of supported date-time formats
    private static final List<String> DATE_TIME_FORMATS = Arrays.asList(
            "d/M/yyyy HHmm",
            "yyyy-MM-dd HHmm",
            "d-M-yyyy HHmm",
            "dd MMM yyyy HH:mm" // For parity if needed
    );

    // List of supported date-only formats (default time will be set)
    private static final List<String> DATE_ONLY_FORMATS = Arrays.asList(
            "d/M/yyyy",
            "yyyy-MM-dd",
            "d-M-yyyy",
            "MMM d yyyy");

    /**
     * Parses a string input into a LocalDateTime object.
     * 
     * @param input The date string to parse.
     * @return The parsed LocalDateTime object.
     * @throws ChatbotExceptions If the input cannot be parsed into a valid
     *                           date-time.
     */
    public static LocalDateTime parseDateTime(String input) throws ChatbotExceptions {
        String trimmedInput = input.trim().toLowerCase();

        // Handle keywords
        if (trimmedInput.equals("today")) {
            return LocalDateTime.now().with(LocalTime.MAX); // End of today? Or maybe start? Let's say 23:59 for
                                                            // deadlines
        } else if (trimmedInput.equals("tomorrow")) {
            return LocalDateTime.now().plusDays(1).with(LocalTime.MAX);
        }

        // Try parsing with time
        // Try parsing with time
        for (String format : DATE_TIME_FORMATS) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return LocalDateTime.parse(input.trim(), formatter);
            } catch (DateTimeParseException ignored) {
                // Try next format
            }
        }

        // Try parsing without time (default to 23:59)
        // Try parsing without time (default to 23:59)
        for (String format : DATE_ONLY_FORMATS) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                LocalDate date = LocalDate.parse(input.trim(), formatter);
                return date.atTime(23, 59);
            } catch (DateTimeParseException ignored) {
                // Try next format
            }
        }

        throw new ChatbotExceptions(
                "OOPS!!! Invalid date format. Please use d/M/yyyy HHmm (e.g., 2/12/2019 1800) or 'today'/'tomorrow'.");
    }

    /**
     * Formats a LocalDateTime object into a user-friendly string.
     * 
     * @param dateTime The LocalDateTime object to format.
     * @return A formatted string (e.g., "MMM d yyyy HH:mm").
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy HH:mm"));
    }

    /**
     * Formats a LocalDateTime object for file storage.
     * 
     * @param dateTime The LocalDateTime object to format.
     * @return A formatted string (ISO-8601 like pattern preferred for stability).
     */
    public static String toFileString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }
}
