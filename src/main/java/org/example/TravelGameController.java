package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TravelGameController {


    @FXML
    private ImageView mapView;
    @FXML
    private ListView<String> logList;
    @FXML
    private Button rollButton;


    @FXML
    private void initialize() {
        mapView.setImage(new Image(getClass().getResourceAsStream("/assets/map.png")));
        logList.getItems().addAll(
            "🌍 Spelet startade",
            "🗺️ Du står vid startpunkten",
            "🎯 Målet är att nå slutdestinationen"
        );
    }

    public void onRoll(ActionEvent actionEvent) {
        logList.getItems().add("🎲 Du slog tärningen...");
        logList.getItems().add("🚶 Du flyttade 3 steg framåt");
        logList.getItems().add("📍 Du anlände till en ny plats");

    }
}
