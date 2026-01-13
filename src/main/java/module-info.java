module travelGame {
    requires javafx.controls;
    requires javafx.fxml;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;

    opens org.example to javafx.fxml, org.hibernate.orm.core;
    exports org.example;
}
