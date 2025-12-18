package org.example;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MapVisualizer {

    private final Pane mapPane;

    private final double MIN_WORLD_X = 0;
    private final double MAX_WORLD_X = 100;
    private final double MIN_WORLD_Y = 0;
    private final double MAX_WORLD_Y = 100;

    public MapVisualizer(Pane mapPane) {
        this.mapPane = mapPane;
    }

}
