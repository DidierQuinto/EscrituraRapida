module com.example.escriturarapida {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.escrituratrabajo to javafx.graphics, javafx.fxml;
    exports org.example.escrituratrabajo;
}
