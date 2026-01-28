package gojo;

/**
 * Parses user input to identify commands and arguments.
 * <p>
 * This utility class processes raw string input from the user and extracts
 * meaningful commands and data for the application to act upon.
 * </p>
 */
public class Parser {

    /**
     * Parses the command word from the user input.
     * <p>
     * The first word of the input is considered the command. Case-insensitivity
     * is handled by converting the command part to uppercase.
     * </p>
     * 
     * @param fullCommand The full user input string.
     * @return The Command enum corresponding to the input.
     * @throws ChatbotExceptions If the command is unknown or invalid.
     */
    public static Command parseCommand(String fullCommand) throws ChatbotExceptions {
        // Split input into command word and the rest
        String[] parts = fullCommand.trim().split(" ", 2);
        String commandStr = parts[0].toUpperCase();
        try {
            return Command.valueOf(commandStr);
        } catch (IllegalArgumentException e) {
            throw new ChatbotExceptions("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Extracts the arguments from the user input (part after the command).
     * 
     * @param fullCommand The full user input string.
     * @return The arguments string, or empty string if no arguments are provided.
     */
    public static String getArguments(String fullCommand) {
        String[] parts = fullCommand.trim().split(" ", 2);
        if (parts.length < 2) {
            return "";
        }
        return parts[1];
    }

    /**
     * Parses an integer index from the arguments (1-based to 0-based).
     * <p>
     * Converts the user-provided 1-based index (e.g., "1" for the first item)
     * to a system-internal 0-based index (e.g., 0).
     * </p>
     * 
     * @param args The arguments string containing the index.
     * @return The 0-based index.
     * @throws ChatbotExceptions If the argument is not a valid integer.
     */
    public static int parseIndex(String args) throws ChatbotExceptions {
        try {
            // Remove non-digit characters and parse
            return Integer.parseInt(args.replaceAll("\\D+", "")) - 1;
        } catch (NumberFormatException e) {
            throw new ChatbotExceptions("OOPS!!! The task number must be an integer.");
        }
    }
}
