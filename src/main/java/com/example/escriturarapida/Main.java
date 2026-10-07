package com.example.escriturarapida;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Crear un botón
        Button btn = new Button("six seven!!");

        // Acción al presionar el botón
        btn.setOnAction(e -> btn.setText("¡JavaFX invento el 67! 🎉"));

        // Organizar el botón al centro de la ventana
        StackPane root = new StackPane(btn);
        Scene scene = new Scene(root, 400, 250);

        // Configurar la ventana principal
        stage.setTitle("seis y siete");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
