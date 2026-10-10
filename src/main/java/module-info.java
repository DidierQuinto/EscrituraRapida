module com.example.escriturarapida {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.escriturarapida to javafx.graphics, javafx.fxml;
    exports com.example.escriturarapida;
    exports com.example.escriturarapida.events;
    exports com.example.escriturarapida.model;
    exports com.example.escriturarapida.ui;
}