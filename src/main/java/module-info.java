    module travelGame {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    //opens org.example to javafx.fxml;
    opens org.example to org.hibernate.orm.core;
    exports org.example;
}
