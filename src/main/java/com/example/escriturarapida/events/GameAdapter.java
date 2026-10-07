package com.example.escriturarapida.events;

/**
 * Adapter class for {@link GameListener}. All methods are empty, so a
 * subclass only overrides the events it is interested in.
 */
public class GameAdapter implements GameListener {

    @Override
    public void onLevelStarted(int level, String word, int secondsLeft) {
    }

    @Override
    public void onTick(int secondsLeft) {
    }

    @Override
    public void onLevelPassed(int completedLevels) {
    }

    @Override
    public void onWrongAnswer() {
    }

    @Override
    public void onGameOver(int completedLevels, int secondsLeft, boolean won) {
    }
}