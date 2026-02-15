package gojo;

import java.util.Scanner;

/**
 * Handles all interactions with the user, including reading input and printing
 * output.
 * <p>
 * This class isolates the user interface logic from the rest of the
 * application,
 * allowing for easier modification of the UI implementation (e.g., switching to
 * GUI).
 * </p>
 */
public class UI {
    private static final String LINE_SEPARATOR = "____________________________________________________________";
    private static final String LOGO = "  ██████   ██████      ██   ██████  \n"
            + " ██        ██   ██     ██   ██    ██ \n"
            + " ██  ████  ██   ██     ██   ██    ██ \n"
            + " ██    ██  ██   ██     ██   ██    ██ \n"
            + "  ██████   ██████   ██ ██    ██████  \n";
    private static final String GOJO_FACE = """
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
    private static final String WELCOME_FACE_MSG = "Hello from:\n";
    private static final String WELCOME_GREETING = "Hey! I'm gojo 🤞🏻.";
    private static final String WELCOME_DOMAIN = "Consider this your domain of organisation.";
    private static final String WELCOME_PROMPT = "What would you like to do?";

    private final Scanner sc;

    /**
     * Constructs a new Ui instance.
     * Initializes the Scanner to read from standard input (keyboard).
     */
    public UI() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Showing the welcome logo and message.
     * <p>
     * Displays a greeting message along with the Gojo logo and a character face
     * to welcome the user upon application start.
     * </p>
     */
    public void showWelcome() {
        System.out.println(WELCOME_FACE_MSG);
        System.out.println(GOJO_FACE);
        System.out.println(LOGO);
        this.showLine();
        System.out.println(WELCOME_GREETING);
        System.out.println(WELCOME_DOMAIN);
        System.out.println(WELCOME_PROMPT);
        this.showLine();
    }

    /**
     * Reads a command from the user.
     * <p>
     * Prints a prompt "You: " and waits for the user to type a line of text.
     * </p>
     *
     * @return The user specified command string.
     */
    public String readCommand() {
        System.out.print("You: ");
        return sc.nextLine();
    }

    /**
     * Prints a divider line.
     * Used to visually separate different sections of the output or command
     * responses.
     */
    public void showLine() {
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Prints a general message or multiple messages to the user.
     *
     * @param messages The message(s) to show.
     */
    public void showMessage(String... messages) {
        assert messages != null : "Messages cannot be null";
        for (String message : messages) {
            System.out.println(message);
        }
    }

    /**
     * Prints an error message or multiple error messages.
     *
     * @param messages The error message(s) to show.
     */
    public void showError(String... messages) {
        assert messages != null : "Error messages cannot be null";
        for (String message : messages) {
            System.out.println(message);
        }
    }

    /**
     * Prints a loading error message.
     * Called when the storage file cannot be accessed or parsed correctly.
     */
    public void showLoadingError() {
        System.out.println("Error loading data from file.");
    }
}
