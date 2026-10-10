package com.example.escriturarapida.model;

import com.example.escriturarapida.events.GameListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the rules of the game. It does not know anything about the user
 * interface: it only notifies its {@link GameListener} objects.
 */
public class GameEngine {

    /** Level that must be completed to win the game. */
    public static final int MAX_LEVEL = 46;

    private final WordBank wordBank = new WordBank();
    private final GameState state = new GameState();
    private final List<GameListener> listeners = new ArrayList<>();

    private String currentWord = "";
    private int secondsLeft;
    private boolean running;

    /**
     * Registers an object that wants to receive game events.
     *
     * @param listener the listener to add
     */
    public void addListener(GameListener listener) {
        listeners.add(listener);
    }

    /**
     * Starts a new game from the first level.
     */
    public void start() {
        state.reset();
        running = true;
        startLevel();
    }

    /**
     * Validates the text typed by the player (exact comparison).
     * A wrong answer does not end the game; the player keeps trying
     * until the time is over.
     *
     * @param typed the text written by the player
     */
    public void submit(String typed) {
        if (!running) {
            return;
        }
        if (currentWord.equals(typed)) {
            passLevel();
        } else {
            for (GameListener listener : listeners) {
                listener.onWrongAnswer();
            }
        }
    }

    /**
     * Must be called once per second. When the time reaches zero the typed
     * text is validated automatically.
     *
     * @param typed the text currently written by the player
     */
    public void tick(String typed) {
        if (!running) {
            return;
        }
        secondsLeft--;
        for (GameListener listener : listeners) {
            listener.onTick(secondsLeft);
        }
        if (secondsLeft <= 0) {
            if (currentWord.equals(typed)) {
                passLevel();
            } else {
                finish(false);
            }
        }
    }

    private void startLevel() {
        currentWord = wordBank.nextWord();
        secondsLeft = state.getTimeForCurrentLevel();
        for (GameListener listener : listeners) {
            listener.onLevelStarted(state.getLevel(), currentWord, secondsLeft);
        }
    }

    private void passLevel() {
        state.levelCompleted();
        for (GameListener listener : listeners) {
            listener.onLevelPassed(state.getCompletedLevels());
        }
        if (state.getCompletedLevels() >= MAX_LEVEL) {
            finish(true);
        } else {
            startLevel();
        }
    }

    private void finish(boolean won) {
        running = false;
        for (GameListener listener : listeners) {
            listener.onGameOver(state.getCompletedLevels(), secondsLeft, won);
        }
    }
}