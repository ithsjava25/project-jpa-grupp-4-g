package org.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class TravelGameController {

    @FXML private ImageView mapView;
    @FXML private Pane drawingPane;
    @FXML private ListView<String> logList;
    @FXML private Button rollButton;
    @FXML private Label playerLabel;

    private MapVisualizer visualizer;
    private int playerX = 0;
    private int playerY = 0;
    private int currentRoll = 0;
    private boolean waitingForMove = false;
    private final int GRID_SIZE = 50;

    @FXML
    private void initialize() {
        visualizer = new MapVisualizer(drawingPane);
        mapView.setImage(new Image(getClass().getResourceAsStream("/assets/map.png")));

        double width = 900;
        double height = 700;

        mapView.setFitWidth(width);
        mapView.setFitHeight(height);

        drawingPane.setPrefSize(width, height);
        drawingPane.setMaxSize(width, height);

        logList.getItems().add("🌍 Spelet startade. Slå tärningen!");


        Platform.runLater(() -> updateGraphics());
    }

    public void setupGame(String playerName) {
        playerLabel.setText(playerName);
    }

    public void onRoll(ActionEvent actionEvent) {
        if (waitingForMove) return;

        currentRoll = (int) (Math.random() * 6) + 1;
        logList.getItems().add("🎲 Du slog " + currentRoll + "! Välj en ruta.");

        waitingForMove = true;
        rollButton.setDisable(true);
        updateGraphics();
    }

    @FXML
    private void onMapClicked(MouseEvent event) {
        if (!waitingForMove) return;

        double cellWidth = drawingPane.getWidth() / GRID_SIZE;
        double cellHeight = drawingPane.getHeight() / GRID_SIZE;

        int clickedX = (int) (event.getX() / cellWidth);

        int clickedY = (int) ((drawingPane.getHeight() - event.getY()) / cellHeight);

        clickedX = Math.max(0, Math.min(GRID_SIZE - 1, clickedX));
        clickedY = Math.max(0, Math.min(GRID_SIZE - 1, clickedY));

        int distance = Math.abs(clickedX - playerX) + Math.abs(clickedY - playerY);

        if (distance == currentRoll) {
            playerX = clickedX;
            playerY = clickedY;
            waitingForMove = false;
            rollButton.setDisable(false);
            logList.getItems().add("🚶 Flyttade till [" + playerX + ", " + playerY + "]");
            updateGraphics();
        } else {
            logList.getItems().add("❌ Fel avstånd! (" + distance + "/" + currentRoll + ")");
        }
    }

    private void updateGraphics() {
        double w = drawingPane.getWidth();
        double h = drawingPane.getHeight();

        if (w > 0 && h > 0) {
            visualizer.drawGrid(w, h);
            if (waitingForMove) {
                visualizer.highlightPossibleMoves(playerX, playerY, currentRoll, w, h);
            }
            visualizer.drawPlayer(playerX, playerY, w, h);
        }
    }
    public void startMockJourney() {
        logList.getItems().add("📜 Nytt uppdrag: Resan till den glömda staden!");

        List<int[]> journeyPath = new ArrayList<>();
        journeyPath.add(new int[]{playerX, playerY});
        journeyPath.add(new int[]{10, 15});
        journeyPath.add(new int[]{25, 20});
        journeyPath.add(new int[]{40, 45});
        visualizer.animateJourney(journeyPath, drawingPane.getWidth(), drawingPane.getHeight());
        playerX = 40;
        playerY = 45;
    }
}
