package com.example.escriturarapida;

import com.example.escriturarapida.events.GameAdapter;
import com.example.escriturarapida.model.GameEngine;
import com.example.escriturarapida.ui.BombTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller of the game view. It connects the FXML components with the
 * game engine using custom event handlers (inner classes).
 */
public class GameController {

    @FXML private VBox gamePane;
    @FXML private VBox summaryPane;
    @FXML private StackPane bombHolder;
    @FXML private Label levelLabel;
    @FXML private Label wordLabel;
    @FXML private Label messageLabel;
    @FXML private Label summaryTitleLabel;
    @FXML private Label summaryLabel;
    @FXML private TextField inputField;
    @FXML private Button checkButton;
    @FXML private Button restartButton;

    private final GameEngine engine = new GameEngine();
    private final BombTimer bomb = new BombTimer();
    private Timeline timeline;

    /**
     * Called automatically by JavaFX after the FXML is loaded.
     * Registers the event handlers and starts the first game.
     */
    @FXML
    private void initialize() {
        bombHolder.getChildren().add(bomb);

        engine.addListener(new UiGameListener());

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new TickHandler()));
        timeline.setCycleCount(Timeline.INDEFINITE);

        inputField.setOnKeyPressed(new EnterKeyHandler());

        HoverHandler hoverHandler = new HoverHandler();
        for (Button button : new Button[]{checkButton, restartButton}) {
            button.setOnMouseEntered(hoverHandler);
            button.setOnMouseExited(hoverHandler);
        }

        engine.start();
    }

    /**
     * Mouse event: the player clicks the check button.
     */
    @FXML
    private void onCheckClicked() {
        engine.submit(inputField.getText());
    }

    /**
     * Mouse event: the player clicks the restart button.
     */
    @FXML
    private void onRestartClicked() {
        messageLabel.setText("");
        setPaneVisible(summaryPane, false);
        setPaneVisible(gamePane, true);
        engine.start();
    }

    private void showMessage(String text, boolean success) {
        messageLabel.setText(text);
        messageLabel.getStyleClass().removeAll("success", "error");
        messageLabel.getStyleClass().add(success ? "success" : "error");
    }

    private void setPaneVisible(VBox pane, boolean visible) {
        pane.setVisible(visible);
        pane.setManaged(visible);
    }

    /**
     * Keyboard event handler: validates the answer when ENTER is pressed.
     */
    private class EnterKeyHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                engine.submit(inputField.getText());
            }
        }
    }

    /**
     * Timer event handler: runs once per second and updates the engine.
     */
    private class TickHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            engine.tick(inputField.getText());
        }
    }

    /**
     * Mouse event handler: enlarges a button while the mouse is over it.
     */
    private class HoverHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            Button button = (Button) event.getSource();
            double scale = event.getEventType() == MouseEvent.MOUSE_ENTERED ? 1.08 : 1.0;
            button.setScaleX(scale);
            button.setScaleY(scale);
        }
    }

    /**
     * Game listener that updates the user interface. It extends the adapter,
     * so only the needed events are overridden.
     */
    private class UiGameListener extends GameAdapter {

        @Override
        public void onLevelStarted(int level, String word, int secondsLeft) {
            levelLabel.setText("Nivel " + level);
            wordLabel.setText(word);
            inputField.clear();
            bomb.start(secondsLeft);
            timeline.playFromStart();
            Platform.runLater(() -> inputField.requestFocus());
        }

        @Override
        public void onTick(int secondsLeft) {
            bomb.setSecondsLeft(secondsLeft);
        }

        @Override
        public void onLevelPassed(int completedLevels) {
            showMessage("¡Correcto! ¡Nivel superado!", true);
        }

        @Override
        public void onWrongAnswer() {
            showMessage("Incorrecto, revisa e intenta de nuevo", false);
        }

        @Override
        public void onGameOver(int completedLevels, int secondsLeft, boolean won) {
            timeline.stop();
            bomb.stop();
            setPaneVisible(gamePane, false);
            setPaneVisible(summaryPane, true);
            summaryTitleLabel.setText(won ? "¡Ganaste el juego!" : "¡Fin de la partida! Tiempo agotado");
            summaryLabel.setText("Niveles completados: " + completedLevels
                    + "\nTiempo restante: " + secondsLeft + " s");
            restartButton.requestFocus();
        }
    }
}