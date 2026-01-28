package gojo;

/**
 * Represents exceptions specific to the Gojo chatbot application.
 * This class is used to handle application-specific errors such as invalid
 * commands,
 * file I/O errors, or invalid task indices.
 */
public class ChatbotExceptions extends Exception {

    /**
     * Constructs a new ChatbotExceptions with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public ChatbotExceptions(String message) {
        super(message);
    }
}
