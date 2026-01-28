import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The entry point for the Gojo chatbot application.
 * Gojo is a CLI-based task manager that helps users track todos, deadlines, and
 * events.
 * It supports commands to list, mark, unmark, delete, and add various types of
 * tasks.
 */
public class Gojo {
    private static final String FILE_PATH = "data/gojo.txt";
    private static List<Task> tasks = new ArrayList<>();

    /**
     * The main method that initializes the chatbot and handles the user input loop.
     * It displays a welcome message and continuously processes user commands until
     * "bye" is entered.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        loadData();
        /*
         * Scanner used to read user input from standard input.
         * This serves as the primary input mechanism for the chatbot.
         */
        Scanner sc = new Scanner(System.in);

        /*
         * ASCII logo displayed during program startup.
         * Used purely for visual branding and does not affect logic.
         */

        String logo = "  ██████   ██████      ██   ██████  \n"
                + " ██        ██   ██     ██   ██    ██ \n"
                + " ██  ████  ██   ██     ██   ██    ██ \n"
                + " ██    ██  ██   ██     ██   ██    ██ \n"
                + "  ██████   ██████   ██ ██    ██████  \n";

        String gojoFace = """
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⢾⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⠏⠁⣸⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣤⣴⡶⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⡿⠉⠀⠀⡿⠀⠀⠀⠀⠀⢀⣀⣤⡴⠶⠛⠋⣉⡾⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡿⠀⠀⠀⢸⣇⣀⣤⠴⠶⠛⠋⠉⠀⠀⠀⠀⣼⣯⣤⣤⣤⣤⣤⣤⣤⡤⠴⠶⠶⠛⠛⢹⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣼⠃⠀⠀⠀⠙⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⢷⡄⠀⠀⠀⠀⠀⠀⠀⠀⠈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠷⣤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⠛⠷⠦⣤⣄⣀⣀⡀⠀⠀⠀⠀⢀⡀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⡴⠟⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠉⠉⠉⠉⠉⣹⠏⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⣀⣀⣠⡤⠶⠚⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⣦⡀⠀⠀⢀⣤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣼⠏⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠻⣿⠋⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⢷⣄⠀⠀⠸⣿⠳⢦⣤⣀⣀⣀⠀⢀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⡴⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠈⠻⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢷⣄⠀⢹⣆⠀⠀⠉⠉⠉⠛⠋⠙⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠛⣿⣍⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠈⠻⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢷⣔⢻⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠻⢦⣄⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⣶⣤⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠻⣿⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠛⠶⢦⣄⣀⡀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⡼⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⢙⣿⠗
                ⠀⠀⠀⠀⠀⠀⣠⡴⠞⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⠾⠋⠁⠀
                ⣀⣀⣤⠴⠖⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣶⠶⠖⠛⠉⠁⠀⠀⠀⠀
                ⠉⠻⢦⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠀⠀⠀⠀⠀⠀⠀⠀⣼⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⢷⣀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠈⠙⠛⠲⠶⠶⢤⣤⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀⠀⠀⠀⠀⣠⡾⠙⣧⠁⠀⠀⢰⣧⠀⠀⠀⠀⠀⠀⣾⣧⡀⠀⠀⠀⠀⠀⠀⣤⠀⠀⠀⠀⠀⠀⠀⣦⡀⠙⢶⣄⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⡇⠀⠀⠀⠀⠀⣴⠟⠀⠀⣿⠀⠀⢀⡿⢻⡄⠀⠀⠀⠀⢰⡇⡘⢧⡀⠀⠀⠀⠀⠀⣿⠀⠀⠀⠀⢀⡀⠀⢹⡟⠳⠦⣽⣷⡦⠄⠀⠀
                ⠀⠀⠀⠀⠀⠀⢀⣀⡴⠏⠀⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⢀⣾⣃⣀⣀⣀⣿⠀⢀⣸⠇⠨⣿⡀⠀⠀⠀⣼⠁⠃⠈⢷⡀⠀⠀⠀⠀⣿⠀⠀⠀⠀⢸⠇⠀⠀⣷⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠘⠛⠛⠛⢻⠟⠀⠀⠀⠀⠀⣶⢄⠀⠀⠀⠀⠸⡇⠀⢀⣴⣿⣿⢿⣻⢿⣻⣿⣷⣤⡿⠀⠀⢹⣯⠀⠀⢸⠇⠀⣀⣭⣾⣿⣶⣶⣦⣤⡿⠀⠀⠀⠀⣼⠂⠀⠀⢹⡧⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⡟⠀⠀⠀⠀⠀⠀⢹⡆⠀⠀⠀⠀⣸⣷⢠⣿⣿⡿⣽⢯⣿⢿⡽⣾⡽⣿⣿⡀⠀⠈⢻⡄⢠⣯⣤⣾⣿⣿⢿⣽⣻⡽⣿⣻⣿⣦⠀⠀⠀⣿⣅⠀⠀⠀⢿⡀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⢠⡟⠀⠀⠀⣤⠀⠀⢀⢸⣧⠀⠀⠀⣴⠏⣿⣿⡿⣯⡽⣯⣟⡾⣿⣽⣳⣟⣷⣻⣿⠀⠀⠈⢿⡞⠀⣿⣿⢿⣽⣻⢾⣽⣻⡽⣷⣿⣿⣿⣄⠀⣿⠉⠛⢦⣄⡘⣧⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠰⠿⠶⠶⠚⠛⡿⠀⣶⡟⠋⢻⡄⠀⣰⡟⠀⠻⣿⣟⣷⡻⣗⣯⣟⡷⣽⣳⣟⣾⣻⣿⠶⠶⠶⠾⠷⢾⣿⣯⣟⡾⣽⣻⣞⡷⣿⣳⢿⣿⣿⠙⣿⡟⠀⠀⠀⠈⠛⠿⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⡇⢸⡇⠀⠀⢸⣷⢀⡿⠀⠀⠀⢿⣿⣷⣿⣹⡾⣷⢿⣷⡿⣾⣿⣿⡏⠀⠀⠀⠀⠀⢸⣿⣷⣏⡿⣷⢿⣸⢿⣹⢿⡿⣿⡏⠀⠈⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣾⡷⢻⡇⠀⠀⠈⢹⡿⠁⠀⠀⠀⠈⠻⣿⣿⣷⣿⣻⣟⣾⣻⣿⣿⠏⠀⠀⠀⠀⠀⠀⠀⠸⣿⣿⣿⣽⢯⣟⣯⣟⡿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠁⠀⠀⠻⣆⠠⡀⠀⠁⠀⠀⠀⠀⠀⠀⠈⠙⠻⠷⠿⠾⠷⠟⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠛⢿⣿⣿⣿⣾⣿⠿⠋⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠻⣦⣄⣀⠀⢀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣦⣤⣀⣀⣠⡀⠀⠀⠀⠀⠀⠀⠉⠉⠁⠀⠀⢀⡾⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠙⠉⠉⠉⠛⠶⢤⣄⣀⠀⠀⠀⠀⠀⠀⠀⠐⣿⠀⠉⢉⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣴⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⣿⣷⣶⣤⣤⣀⣀⣀⠙⢷⡶⠟⠁⠀⠀⠀⣀⣀⣀⣤⡴⠶⠛⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠋⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⣿⣿⣿⣿⡿⣿⢿⡿⣿⢿⣿⣿⣿⣿⣿⣿⣿⣧⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⣿⣿⣿⣻⣿⣿⢯⣿⣽⣻⢿⣿⣽⣿⣿⡏⠉⣿⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⡿⣽⣿⣿⣿⣻⣽⣾⣻⣟⡿⣞⣯⣿⣿⣾⣿⣿⣿⣧⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⣿⣿⡿⣽⣿⢿⣿⣯⣟⣾⢷⣻⡾⣟⣿⣽⣾⣳⣯⣟⣿⣿⣿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣯⡿⣟⣾⣿⣿⣷⣻⡽⣿⣽⣻⣿⣽⣾⣷⣿⣳⣯⣿⣿⣿⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⣿⣿⣿⣟⣷⣿⣿⣯⡿⣽⡷⣯⣟⣾⣻⣽⣞⡿⣷⢯⣿⣿⣯⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⣿⣿⣿⣿⣿⣿⣿⣽⣯⣿⣽⣯⣷⣿⣷⣿⣿⢿⡿⣷⣿⣿⣿⠗⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠹⣿⣿⣿⣿⣿⣿⣿⣷⣿⣯⣟⣿⣽⣾⣷⣿⣿⣿⣿⣿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣯⢿⣽⡿⣿⢿⣿⣿⣿⣿⣿⣿⣿⣿⡿⣿⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣾⡿⣯⣿⢿⣿⣿⣿⣿⣿⣟⣿⣳⡿⣿⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣾⣿⣽⣯⢿⣯⣿⣿⣿⣿⢾⣽⣷⡿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣟⣾⡽⣿⣾⣿⣿⣿⣽⡿⣽⣾⣿⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢼⣿⣿⣻⣾⢯⣿⢿⣿⢿⣿⣿⣞⣿⣽⣾⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢼⣿⣷⣿⣞⣿⡽⣿⠃⠸⣿⣽⣾⣿⣞⣷⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⡏⠀⠀⢿⣿⣿⣿⣽⣿⣧⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠙⠛⠛⠛⠛⠃⠀⠀⠈⠉⠉⠉⠉⠉⠉⠁⠀⠀⠀⠀
                                                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                """;
        /*
         * Display the initial greeting, branding, and usage prompt to the user.
         *
         * This section is executed exactly once at program startup and serves to:
         * - Introduce the chatbot and its purpose
         * - Provide a clear visual separation from user input
         * - Establish the tone and personality of the application
         *
         * No user interaction or state modification occurs in this section.
         */
        System.out.println("Hello from:\n");
        System.out.println(gojoFace);
        System.out.println(logo);
        System.out.println("____________________________________________________________");
        System.out.println("Hey! I'm Gojo 🤞🏻.");
        System.out.println("Consider this your domain of organisation.");
        System.out.println("What would you like to do?");
        System.out.println("____________________________________________________________");

        /*
         * Stores the most recent line of input entered by the user.
         *
         * This variable is reused on each iteration of the main interaction loop
         * to process commands and task input.
         */
        String input = "";

        /*
         * Main interaction loop of the application.
         *
         * This loop continuously:
         * - Prompts the user for input
         * - Interprets the input as either a command or a task
         * - Updates application state accordingly
         *
         * The loop terminates only when the user explicitly enters the "bye" command.
         */
        while (true) {
            try {
                System.out.print("You: ");
                input = sc.nextLine();
                if (input.trim().isEmpty()) {
                    continue;
                }

                String[] inputParts = input.trim().split(" ", 2);
                String commandStr = inputParts[0].toUpperCase();
                Command command;

                try {
                    command = Command.valueOf(commandStr);
                } catch (IllegalArgumentException e) {
                    throw new ChatbotExceptions("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }

                switch (command) {
                    /*
                     * Handles the "BYE" command.
                     * Prints a farewell message to the console and terminates the main method,
                     * effectively exiting the application.
                     */
                    case BYE:
                        System.out.println("Bye, until next time - Stay Limitless ♾️");
                        System.out.println("____________________________________________________________");
                        return; // Exit the main method

                    /*
                     * Handles the "LIST" command.
                     * Iterates through the list of tasks and prints each one with its index.
                     * This allows the user to see all current tasks and their status.
                     */
                    case LIST:
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                        break;

                    /*
                     * Handles the "UNMARK" command.
                     * Parses the task number from the user input, validates it, and marks the
                     * corresponding
                     * task as not done. If the input is invalid or out of bounds, an exception is
                     * thrown.
                     */
                    case UNMARK:
                        if (inputParts.length < 2) {
                            throw new ChatbotExceptions("Please specify a task number to unmark.");
                        }
                        try {
                            int taskNumber = Integer.parseInt(inputParts[1].replaceAll("\\D+", "")) - 1;
                            tasks.get(taskNumber).markAsNotDone();
                            System.out.println("OK, I've marked this task as not done yet:");
                            System.out.println(tasks.get(taskNumber));
                            saveData();
                        } catch (NumberFormatException e) {
                            throw new ChatbotExceptions("OOPS!!! The task number must be an integer.");
                        } catch (IndexOutOfBoundsException e) {
                            throw new ChatbotExceptions("OOPS!!! The task number is out of bounds.");
                        }
                        break;

                    /*
                     * Handles the "MARK" command.
                     * Similar to UNMARK, but marks the specified task as done.
                     * It includes validation for the task number integer and array bounds.
                     */
                    case MARK:
                        if (inputParts.length < 2) {
                            throw new ChatbotExceptions("Please specify a task number to mark.");
                        }
                        try {
                            int taskNumber = Integer.parseInt(inputParts[1].replaceAll("\\D+", "")) - 1;
                            tasks.get(taskNumber).markAsDone();
                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println(tasks.get(taskNumber));
                            saveData();
                        } catch (NumberFormatException e) {
                            throw new ChatbotExceptions("OOPS!!! The task number must be an integer.");
                        } catch (IndexOutOfBoundsException e) {
                            throw new ChatbotExceptions("OOPS!!! The task number is out of bounds.");
                        }
                        break;

                    /*
                     * Handles the "TODO" command.
                     * Creates a new Todo task with the provided description.
                     * Checks if the task list is full (limit 100) and ensures the description is
                     * not empty.
                     */
                    case TODO:
                        if (tasks.size() >= 100) {
                            System.out.println("Cannot add more than 100 items");
                        } else {
                            if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
                                throw new ChatbotExceptions("OOPS!!! The description of a todo cannot be empty.");
                            }
                            String description = inputParts[1].trim();
                            Task newTask = new Todo(description);
                            tasks.add(newTask);
                            System.out.println("Got it. I've added this task:");
                            System.out.println("  " + newTask);
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                            saveData();
                        }
                        break;

                    /*
                     * Handles the "DEADLINE" command.
                     * Creates a new Deadline task. Requires a description and a deadline time
                     * (prefixed with /by).
                     * Validates format and ensures all parts are present before creating the task.
                     */
                    case DEADLINE:
                        if (tasks.size() >= 100) {
                            System.out.println("Cannot add more than 100 items");
                        } else {
                            if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
                                throw new ChatbotExceptions("OOPS!!! The description of a deadline cannot be empty.");
                            }
                            String[] parts = inputParts[1].split(" /by ");
                            if (parts.length < 2) {
                                throw new ChatbotExceptions("OOPS!!! The deadline cannot be empty.");
                            }
                            String description = parts[0].trim();
                            if (description.length() == 0) {
                                throw new ChatbotExceptions("OOPS!!! The description of a deadline cannot be empty.");
                            }
                            String by = parts[1].trim();
                            Task newTask = new Deadline(description, by);
                            tasks.add(newTask);
                            System.out.println("Got it. I've added this task:");
                            System.out.println("  " + newTask);
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                            saveData();
                        }
                        break;

                    /*
                     * Handles the "EVENT" command.
                     * Creates a new Event task. Requires description, start time (/from), and end
                     * time (/to).
                     * Performs validation to ensure proper formatting and existence of all
                     * components.
                     */
                    case EVENT:
                        if (tasks.size() >= 100) {
                            System.out.println("Cannot add more than 100 items");
                        } else {
                            if (inputParts.length < 2 || inputParts[1].trim().isEmpty()) {
                                throw new ChatbotExceptions("OOPS!!! The description of a event cannot be empty.");
                            }
                            String[] parts = inputParts[1].split(" /from ");
                            if (parts.length < 2) {
                                throw new ChatbotExceptions("OOPS!!! The event cannot be empty.");
                            }
                            String description = parts[0].trim();
                            if (description.length() == 0) {
                                throw new ChatbotExceptions("OOPS!!! The description of a event cannot be empty.");
                            }
                            String[] timeParts = parts[1].split(" /to ");
                            if (timeParts.length < 2) {
                                throw new ChatbotExceptions("OOPS!!! The event time is missing.");
                            }
                            String from = timeParts[0].trim();
                            String to = timeParts[1].trim();
                            Task newTask = new Event(description, from, to);
                            tasks.add(newTask);
                            System.out.println("Got it. I've added this task:");
                            System.out.println("  " + newTask);
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                            saveData();
                        }
                        break;

                    /*
                     * Handles the "DELETE" command.
                     * Removes a task from the list based on the provided index.
                     * Validates that the index is within the valid range of existing tasks.
                     */
                    case DELETE:
                        if (inputParts.length < 2) {
                            throw new ChatbotExceptions("Please specify a task number to delete.");
                        }
                        try {
                            int index = Integer.parseInt(inputParts[1]) - 1;
                            if (index < 0 || index >= tasks.size()) {
                                throw new ChatbotExceptions("OOPS!!! The task number is out of bounds.");
                            }
                            Task removedTask = tasks.get(index);
                            tasks.remove(index);
                            System.out.println("Noted. I've removed this task:");
                            System.out.println("  " + removedTask);
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                            saveData();
                        } catch (NumberFormatException e) {
                            throw new ChatbotExceptions("OOPS!!! The task number must be an integer.");
                        }
                        break;
                }
            } catch (ChatbotExceptions ce) {
                // Catches and displays any application-specific exceptions thrown during
                // command execution.
                System.out.println(ce.getMessage());
            }
            System.out.println("____________________________________________________________");
        }
    }

    /**
     * Loads tasks from the data file on startup.
     * Handles file creation if it doesn't exist and parses existing data.
     */
    private static void loadData() {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                File directory = file.getParentFile();
                if (directory != null && !directory.exists()) {
                    directory.mkdirs();
                }
                return;
            }

            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                try {
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
                    }

                    if (task != null) {
                        if (isDone) {
                            task.markAsDone();
                        }
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    System.out.println("Skipping corrupted line: " + line);
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    /**
     * Saves the current list of tasks to the data file.
     */
    private static void saveData() {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}
