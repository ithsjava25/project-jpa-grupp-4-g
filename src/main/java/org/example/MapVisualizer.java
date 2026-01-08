package org.example;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class MapVisualizer {
    private final Pane mapPane;
    private final int gridSize = 10;

    public MapVisualizer(Pane mapPane) {
        this.mapPane = mapPane;
    }

    public void drawGrid(double width, double height) {
        mapPane.getChildren().clear();
        for (int i = 0; i <= gridSize; i++) {
            double x = (width / gridSize) * i;
            Line vLine = new Line(x, 0, x, height);
            vLine.setStroke(Color.web("#ffffff", 0.2));

            double y = (height / gridSize) * i;
            Line hLine = new Line(0, y, width, y);
            hLine.setStroke(Color.web("#ffffff", 0.2));
            mapPane.getChildren().addAll(vLine, hLine);
        }
    }

    public void highlightPossibleMoves(int px, int py, int roll, double w, double h) {
        double cW = w / gridSize;
        double cH = h / gridSize;

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                if (Math.abs(x - px) + Math.abs(y - py) == roll) {
                    Rectangle rect = new Rectangle(x * cW, y * cH, cW, cH);
                    rect.setFill(Color.web("#00FF00", 0.3)); // Grön markering
                    mapPane.getChildren().add(rect);
                }
            }
        }
    }

    public void drawPlayer(int gridX, int gridY, double totalWidth, double totalHeight) {
        double cellWidth = totalWidth / gridSize;
        double cellHeight = totalHeight / gridSize;
        Circle playerToken = new Circle((gridX * cellWidth) + (cellWidth / 2),
            (gridY * cellHeight) + (cellHeight / 2), 12, Color.GOLD);
        playerToken.setStroke(Color.BLACK);
        mapPane.getChildren().add(playerToken);
    }
}
