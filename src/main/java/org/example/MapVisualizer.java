package org.example;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

import java.util.List;

public class MapVisualizer {
    private final Pane gridLayer;
    private final Pane markerLayer;
    private final Pane pathLayer;
    private final Pane playerLayer;
    private final int gridSize = 50;

    private final java.util.Map<Integer, javafx.scene.Group> pathGroups = new java.util.HashMap<>();

    public MapVisualizer(Pane gridLayer, Pane markerLayer, Pane pathLayer, Pane playerLayer) {
        this.gridLayer = gridLayer;
        this.markerLayer = markerLayer;
        this.pathLayer = pathLayer;
        this.playerLayer = playerLayer;
    }

    public void clearPaths() {
        pathLayer.getChildren().clear();
        pathGroups.clear();
    }

    public void addRouteSegment(int playerIndex,
                                int fromGridX, int fromGridY,
                                int toGridX, int toGridY,
                                double totalWidth, double totalHeight) {

        double cellWidth = totalWidth / gridSize;
        double cellHeight = totalHeight / gridSize;

        double x1 = (fromGridX * cellWidth) + (cellWidth / 2);
        double y1 = totalHeight - (fromGridY * cellHeight) - (cellHeight / 2);

        double x2 = (toGridX * cellWidth) + (cellWidth / 2);
        double y2 = totalHeight - (toGridY * cellHeight) - (cellHeight / 2);

        var group = pathGroups.get(playerIndex);
        if (group == null) {
            group = new javafx.scene.Group();
            group.setMouseTransparent(true);
            pathGroups.put(playerIndex, group);
            pathLayer.getChildren().add(group);
        }

        Line seg = new Line(x1, y1, x2, y2);
        seg.setStroke(Color.RED);
        seg.setStrokeWidth(3);
        seg.setMouseTransparent(true);

        group.getChildren().add(seg);
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

            // halo för current player (tydligare vems tur)
            if (i == currentIndex) {
                Circle halo = new Circle(centerX, centerY, rPlayer * 1.25);
                halo.setFill(Color.web("#ffffff", 0.18));
                halo.setMouseTransparent(true);
                playerLayer.getChildren().add(halo);
            }

            Circle token = new Circle(centerX, centerY, rPlayer);

            token.setFill(switch (i % 4) {
                case 0 -> Color.GOLD;
                case 1 -> Color.DEEPSKYBLUE;
                case 2 -> Color.LIMEGREEN;
                default -> Color.ORANGERED;
            });

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

        // större och tydligare än innan
        double r = Math.min(cW, cH) * 0.40; // test: 0.35–0.45

        for (Location loc : destinations) {
            int gx = clamp(loc.getX());
            int gy = clamp(loc.getY());

            double centerX = (gx * cW) + (cW / 2);
            double centerY = h - (gy * cH) - (cH / 2);

            Circle marker = new Circle(centerX, centerY, r);

            // röd "solid" prick
            marker.setFill(Color.RED);

            // tom vit kant
            marker.setStroke(Color.WHITE);
            marker.setStrokeWidth(Math.max(2.0, r * 0.20));

            marker.setMouseTransparent(true);
            markerLayer.getChildren().add(marker);
        }
    }


    public void animateJourney(List<int[]> coordinates, double totalWidth, double totalHeight) {
        // rita i markerLayer så det hamnar “under” players men “över” grid
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

    private int clamp(int v) {
        if (v < 0) return 0;
        if (v > gridSize - 1) return gridSize - 1;
        return v;
    }
}
