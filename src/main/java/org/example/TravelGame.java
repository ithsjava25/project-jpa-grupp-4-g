package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Optional;

public class TravelGame extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Starta spelet");
        dialog.setHeaderText("Välkommen till Travel Game!");
        dialog.setContentText("Ange ditt spelarnamn:");
        dialog.setGraphic(null);

        DialogPane dialogPane = dialog.getDialogPane(); // ok att ha kvar eller ta bort
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent() && !result.get().trim().isEmpty()) {
            String playerName = result.get().trim();

            FXMLLoader loader = new FXMLLoader(TravelGame.class.getResource("/travelGame.fxml"));
            Parent root = loader.load();

            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            double w = bounds.getWidth() * 0.95;
            double h = bounds.getHeight() * 0.95;

            Scene scene = new Scene(root, w, h);

            TravelGameController controller = loader.getController();
            controller.setupGame(playerName);

            stage.setOnCloseRequest(e -> controller.shutdown());
            stage.setTitle("Travel Game - Spelare: " + playerName);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();

        } else {
            System.out.println("Inget namn angivet. Avslutar.");
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
