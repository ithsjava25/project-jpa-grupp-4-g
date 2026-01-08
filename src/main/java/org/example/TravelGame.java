package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
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

        DialogPane dialogPane = dialog.getDialogPane();

        //Styling
//        dialogPane.getStylesheets().add(
//            TravelGame.class.getResource("/app.css").toExternalForm()
//        );
//        dialogPane.getStyleClass().add("name-dialog");




        Optional<String> result = dialog.showAndWait();

        if (result.isPresent() && !result.get().trim().isEmpty()) {
            String playerName = result.get();

            FXMLLoader loader = new FXMLLoader(
                TravelGame.class.getResource("/travelGame.fxml")
            );

            Scene scene = new Scene(loader.load(), 1300, 700);

            TravelGameController controller = loader.getController();

            controller.setupGame(playerName);


            stage.setTitle("Travel Game - Spelare: " + playerName);
            stage.setScene(scene);
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
