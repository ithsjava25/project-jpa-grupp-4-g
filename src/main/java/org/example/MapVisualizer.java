package org.example;

import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapVisualizer {
    private final Pane gridLayer;
    private final Pane markerLayer;
    private final Pane pathLayer;
    private final Pane playerLayer;
    private final int gridSize = 50;

    private final Map<Integer, Group> pathGroups = new HashMap<>();

    public MapVisualizer(Pane gridLayer, Pane markerLayer, Pane pathLayer, Pane playerLayer) {
        this.gridLayer = gridLayer;
        this.markerLayer = markerLayer;
        this.pathLayer = pathLayer;
        this.playerLayer = playerLayer;
    }

    public void drawGrid(double width, double height) {
        gridLayer.getChildren().clear();

        for (int i = 0; i <= gridSize; i++) {
            double x = (width / gridSize) * i;
            Line vLine = new Line(x, 0, x, height);
            vLine.setStroke(Color.web("#ffffff", 0.35));
            vLine.setMouseTransparent(true);

            double y = (height / gridSize) * i;
            Line hLine = new Line(0, y, width, y);
            hLine.setStroke(Color.web("#ffffff", 0.35));
            hLine.setMouseTransparent(true);

            gridLayer.getChildren().addAll(vLine, hLine);
        }
    }

    public void clearMarkers() {
        markerLayer.getChildren().clear();
    }

    public void clearPlayers() {
        playerLayer.getChildren().clear();
    }

    public void clearPaths() {
        pathLayer.getChildren().clear();
        pathGroups.clear();
    }

    public void drawPlayers(List<int[]> players, int currentIndex, double totalWidth, double totalHeight) {
        playerLayer.getChildren().clear();

        double cellWidth = totalWidth / gridSize;
        double cellHeight = totalHeight / gridSize;

        for (int i = 0; i < players.size(); i++) {
            int[] p = players.get(i);
            int gridX = p[0];
            int gridY = p[1];

            double centerX = (gridX * cellWidth) + (cellWidth / 2);
            double centerY = totalHeight - (gridY * cellHeight) - (cellHeight / 2);

            double rPlayer = (cellWidth / 2) * 0.8;

            if (i == currentIndex) {
                Color c = colorForPlayer(i);
                Circle halo = new Circle(centerX, centerY, rPlayer * 1.25);
                halo.setFill(c.deriveColor(0, 1, 1, 0.25));
                halo.setMouseTransparent(true);
                playerLayer.getChildren().add(halo);
            }

            Circle token = new Circle(centerX, centerY, rPlayer);
            token.setFill(colorForPlayer(i));

            if (i == currentIndex) {
                token.setStroke(Color.WHITE);
                token.setStrokeWidth(3);
            } else {
                token.setStroke(Color.BLACK);
                token.setStrokeWidth(1.5);
            }

            token.setMouseTransparent(true);
            playerLayer.getChildren().add(token);
        }
    }

    public void drawPlayersInterpolated(List<double[]> players, int currentIndex, double totalWidth, double totalHeight) {
        playerLayer.getChildren().clear();

        double cellWidth = totalWidth / gridSize;
        double cellHeight = totalHeight / gridSize;

        for (int i = 0; i < players.size(); i++) {
            double[] p = players.get(i);
            double gridX = p[0];
            double gridY = p[1];

            double centerX = (gridX * cellWidth) + (cellWidth / 2);
            double centerY = totalHeight - (gridY * cellHeight) - (cellHeight / 2);

            double rPlayer = (cellWidth / 2) * 0.8;

            if (i == currentIndex) {
                Color c = colorForPlayer(i);
                Circle halo = new Circle(centerX, centerY, rPlayer * 1.25);
                halo.setFill(c.deriveColor(0, 1, 1, 0.25));
                halo.setMouseTransparent(true);
                playerLayer.getChildren().add(halo);
            }

            Circle token = new Circle(centerX, centerY, rPlayer);
            token.setFill(colorForPlayer(i));

            if (i == currentIndex) {
                token.setStroke(Color.WHITE);
                token.setStrokeWidth(3);
            } else {
                token.setStroke(Color.BLACK);
                token.setStrokeWidth(1.5);
            }

            token.setMouseTransparent(true);
            playerLayer.getChildren().add(token);
        }
    }

    public void drawDestMarkers(List<Location> destinations, double w, double h) {
        markerLayer.getChildren().clear();

        double cW = w / gridSize;
        double cH = h / gridSize;

        double r = Math.min(cW, cH) * 0.40;

        for (Location loc : destinations) {
            int gx = clamp(loc.getX());
            int gy = clamp(loc.getY());

            double centerX = (gx * cW) + (cW / 2);
            double centerY = h - (gy * cH) - (cH / 2);

            Circle marker = new Circle(centerX, centerY, r);
            marker.setFill(Color.RED);
            marker.setStroke(Color.WHITE);
            marker.setStrokeWidth(Math.max(2.0, r * 0.20));

            marker.setMouseTransparent(true);
            markerLayer.getChildren().add(marker);
        }
    }

    public void addRouteProgressSegment(
        int playerIndex,
        int fromGridX, int fromGridY,
        int toGridX, int toGridY,
        double fracFrom, double fracTo,
        double totalWidth, double totalHeight
    ) {
        fracFrom = clamp01(fracFrom);
        fracTo = clamp01(fracTo);
        if (fracTo <= fracFrom) return;

        double cellWidth = totalWidth / gridSize;
        double cellHeight = totalHeight / gridSize;

        double ax = (fromGridX * cellWidth) + (cellWidth / 2);
        double ay = totalHeight - (fromGridY * cellHeight) - (cellHeight / 2);

        double bx = (toGridX * cellWidth) + (cellWidth / 2);
        double by = totalHeight - (toGridY * cellHeight) - (cellHeight / 2);

        double x1 = ax + (bx - ax) * fracFrom;
        double y1 = ay + (by - ay) * fracFrom;
        double x2 = ax + (bx - ax) * fracTo;
        double y2 = ay + (by - ay) * fracTo;

        Group group = pathGroups.get(playerIndex);
        if (group == null) {
            group = new Group();
            group.setMouseTransparent(true);
            pathGroups.put(playerIndex, group);
            pathLayer.getChildren().add(group);
        }

        Color base = colorForPlayer(playerIndex);

        Line seg = new Line(x1, y1, x2, y2);
        seg.setStroke(base.deriveColor(0, 1, 1, 0.75));
        seg.setStrokeWidth(3.5);
        seg.setMouseTransparent(true);

        group.getChildren().add(seg);
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
        markerLayer.getChildren().add(line);

        line.setStrokeDashOffset(500);
        Transition anim = new Transition() {
            { setCycleDuration(Duration.seconds(3)); }
            protected void interpolate(double frac) {
                line.setStrokeDashOffset(500 * (1 - frac));
            }
        };
        anim.play();
    }

    private Color colorForPlayer(int index) {
        return switch (index % 4) {
            case 0 -> Color.GOLD;
            case 1 -> Color.DEEPSKYBLUE;
            case 2 -> Color.LIMEGREEN;
            default -> Color.ORANGERED;
        };
    }

    private double clamp01(double v) {
        if (v < 0) return 0;
        if (v > 1) return 1;
        return v;
    }

    private int clamp(int v) {
        if (v < 0) return 0;
        if (v > gridSize - 1) return gridSize - 1;
        return v;
    }
}
