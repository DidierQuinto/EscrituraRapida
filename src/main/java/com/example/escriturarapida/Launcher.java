package com.example.escriturarapida;

import javafx.application.Application;

/**
 * Entry point of the application. It only delegates to {@link MainApp}
 * so the JavaFX runtime starts correctly when using modules.
 */
public class Launcher {

    /**
     * Starts the JavaFX application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }
}