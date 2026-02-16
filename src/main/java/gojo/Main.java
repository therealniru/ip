package gojo;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Gojo using FXML.
 */
public class Main extends Application {
    /**
     * Main entry point of the Gojo application.
     * Initializes and launches the primary UI.
     */
    private Gojo gojo = new Gojo();

    /**
     * Starts the JavaFX application.
     *
     * @param stage primary stage for this application
     */

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Gojo Satoru");
            fxmlLoader.<MainWindow>getController().setGojo(gojo);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
