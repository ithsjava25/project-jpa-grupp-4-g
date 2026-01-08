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

    @FXML
    private void initialize() {
        mapView.setImage(new Image(getClass().getResourceAsStream("/assets/map.png")));
        visualizer = new MapVisualizer(drawingPane);

        logList.getItems().add("🌍 Spelet startade. Slå tärningen!");

        Platform.runLater(() -> updateGraphics());
    }

    public void setupGame(String playerName) {
        playerLabel.setText(playerName);
    }

    public void onRoll(ActionEvent actionEvent) {
        if (waitingForMove) return;

        currentRoll = (int) (Math.random() * 3) + 1;
        logList.getItems().add("🎲 Du slog " + currentRoll + "! Klicka på en ruta som är " + currentRoll + " steg bort.");

        waitingForMove = true;
        rollButton.setDisable(true);

        // Visa möjliga drag
        updateGraphics();
    }

    @FXML
    private void onMapClicked(MouseEvent event) {
        if (!waitingForMove) return;


        double cellWidth = mapView.getFitWidth() / 10;
        double cellHeight = mapView.getFitHeight() / 10;

        int clickedX = (int) (event.getX() / cellWidth);
        int clickedY = (int) (event.getY() / cellHeight);


        int distance = Math.abs(clickedX - playerX) + Math.abs(clickedY - playerY);

        if (distance == currentRoll) {
            playerX = clickedX;
            playerY = clickedY;
            waitingForMove = false;
            rollButton.setDisable(false);

            logList.getItems().add("🚶 Flyttade till [" + playerX + ", " + playerY + "]");
            updateGraphics();
        } else {
            logList.getItems().add("❌ Felaktigt drag! Du måste flytta exakt " + currentRoll + " steg.");
        }
    }

    private void updateGraphics() {
        double w = mapView.getFitWidth();
        double h = mapView.getFitHeight();
        visualizer.drawGrid(w, h);

        if (waitingForMove) {
            visualizer.highlightPossibleMoves(playerX, playerY, currentRoll, w, h);
        }

        visualizer.drawPlayer(playerX, playerY, w, h);
    }
}
