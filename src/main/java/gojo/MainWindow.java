package gojo;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    private static final int FONT_SIZE = 14;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private Canvas matrixCanvas;

    private Gojo gojo;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image gojoImage = new Image(this.getClass().getResourceAsStream("/images/gojo.jpg"));
    private int[] drops;

    /**
     * Controller for Initialize.
     */
    @FXML
    public void initialize() {
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        // Bind canvas size to main pane size
        matrixCanvas.widthProperty().bind(mainPane.widthProperty());
        matrixCanvas.heightProperty().bind(mainPane.heightProperty());

        // Initialize drops array for Matrix effect
        int columns = (int) (matrixCanvas.getWidth() / FONT_SIZE);
        drops = new int[columns];
        for (int i = 0; i < columns; i++) {
            drops[i] = 1;
        }

        GraphicsContext gc = matrixCanvas.getGraphicsContext2D();

        // Handle canvas resizing
        matrixCanvas.widthProperty().addListener(obs -> {
            int newCols = (int) (matrixCanvas.getWidth() / FONT_SIZE);
            int[] newDrops = new int[newCols];
            System.arraycopy(drops, 0, newDrops, 0, Math.min(drops.length, newCols));
            drops = newDrops;
        });

        new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 50_000_000) { // Update roughly every 50ms
                    drawMatrix(gc);
                    lastUpdate = now;
                }
            }
        }.start();
    }

    private void drawMatrix(GraphicsContext gc) {
        // Semi-transparent black background to create fading trail
        gc.setFill(Color.rgb(18, 18, 18, 0.1)); // Using the background color code with alpha
        gc.fillRect(0, 0, matrixCanvas.getWidth(), matrixCanvas.getHeight());

        gc.setFill(Color.web("#800080")); // Purple
        gc.setFont(Font.font("Monospaced", FONT_SIZE));

        for (int i = 0; i < drops.length; i++) {
            String text = String.valueOf((char) (0x30A0 + Math.random() * 96)); // Random Katakana
            double x = i * FONT_SIZE;
            double y = drops[i] * FONT_SIZE;

            gc.fillText(text, x, y);

            if (y > matrixCanvas.getHeight() && Math.random() > 0.975) {
                drops[i] = 0;
            }
            drops[i]++;
        }
    }

    public void setGojo(Gojo g) {
        gojo = g;
        // Show welcome message
        dialogContainer.getChildren().add(
                DialogBox.getGojoDialog("Yo! I'm Gojo Satoru. Welcome to my Domain Expansion: Unfinished Checklist.",
                        gojoImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Gojo's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = gojo.getResponse(input);

        DialogBox gojoDialog;
        if (response.startsWith("OOPS!!!") || response.startsWith("Something went wrong:")) {
            gojoDialog = DialogBox.getErrorDialog(response, gojoImage);
        } else {
            gojoDialog = DialogBox.getGojoDialog(response, gojoImage);
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                gojoDialog);
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> {
                Platform.exit();
                System.exit(0);
            });
            delay.play();
        }
    }
}
