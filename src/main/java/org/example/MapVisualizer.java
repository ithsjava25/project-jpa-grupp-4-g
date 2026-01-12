package org.example;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;

public class MapVisualizer {
    private final Pane mapPane;
    private final int gridSize = 50;

    public MapVisualizer(Pane mapPane) {
        this.mapPane = mapPane;
    }

    public void drawGrid(double width, double height) {
        mapPane.getChildren().clear();

        for (int i = 0; i <= gridSize; i++) {
            double x = (width / gridSize) * i;
            Line vLine = new Line(x, 0, x, height);
            vLine.setStroke(Color.web("#ffffff", 0.5));
            vLine.setMouseTransparent(true);

            double y = (height / gridSize) * i;
            Line hLine = new Line(0, y, width, y);
            hLine.setStroke(Color.web("#ffffff", 0.5));
            hLine.setMouseTransparent(true);

            mapPane.getChildren().addAll(vLine, hLine);
        }
    }

    public void highlightPossibleMoves(int px, int py, int roll, double w, double h) {
        double cW = w / gridSize;
        double cH = h / gridSize;

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                if (Math.abs(x - px) + Math.abs(y - py) == roll) {
                    double posY = h - (y * cH) - cH;

                    Rectangle rect = new Rectangle(x * cW, posY, cW, cH);
                    rect.setFill(Color.web("#00FF00", 0.4));
                    rect.setMouseTransparent(true);
                    mapPane.getChildren().add(rect);
                }
            }
        }
    }

    public void drawPlayer(int gridX, int gridY, double totalWidth, double totalHeight) {
        double cellWidth = totalWidth / gridSize;
        double cellHeight = totalHeight / gridSize;

        double centerX = (gridX * cellWidth) + (cellWidth / 2);

        double centerY = totalHeight - (gridY * cellHeight) - (cellHeight / 2);

        Circle playerToken = new Circle(centerX, centerY, (cellWidth / 2) * 0.8, Color.GOLD);
        playerToken.setStroke(Color.BLACK);
        playerToken.setMouseTransparent(true);
        mapPane.getChildren().add(playerToken);
    }
    public void animateJourney(List<int[]> coordinates, double totalWidth, double totalHeight) {
        Polyline line = new Polyline();
        line.setStroke(Color.RED);
        line.setStrokeWidth(3);
        double cellWidth = totalWidth / gridSize;
        double cellHeight = totalHeight / gridSize;

        for (int[] coord : coordinates) {
            double x = (coord[0] * cellWidth) + (cellWidth / 2);
            double y = totalHeight - (coord[1] * cellHeight) - (cellHeight / 2);
            line.getPoints().addAll(x, y);
        }
        mapPane.getChildren().add(line);

        line.setStrokeDashOffset(500);
        Transition anim = new Transition() {
            { setCycleDuration(Duration.seconds(3)); }
            protected void interpolate(double frac) {
                line.setStrokeDashOffset(500 * (1 - frac));
            }
        };
        anim.play();
    }
}
