package org.example;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainController {

    @FXML
    private ImageView mapView;

    @FXML
    private void initialize() {
        mapView.setImage(new Image(getClass().getResourceAsStream("/assets/map.png")));
    }
}
