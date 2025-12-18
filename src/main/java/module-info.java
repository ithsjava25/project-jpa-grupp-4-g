module travelGame {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;

    opens org.example to javafx.fxml;
    exports org.example;
}
