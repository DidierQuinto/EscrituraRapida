package com.example.escriturarapida;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main JavaFX application class. Loads the FXML view and shows the window.
 */
public class MainApp extends Application {

    /**
     * Loads the game view and displays the primary stage.
     *
     * @param stage the primary stage provided by JavaFX
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("game-view.fxml"));
        Scene scene = new Scene(loader.load(), 700, 460);
        stage.setTitle("Escritura Rápida");
        stage.setScene(scene);
        stage.show();
    }
}