package com.example.escriturarapida.events;

/**
 * Custom event interface. Any class that wants to react to what happens
 * in the game (for example, the user interface) implements this interface.
 */
public interface GameListener {

    /**
     * Called when a new level starts.
     *
     * @param level       the level number that starts
     * @param word        the word or phrase to type
     * @param secondsLeft the time available for this level
     */
    void onLevelStarted(int level, String word, int secondsLeft);

    /**
     * Called every second while a level is running.
     *
     * @param secondsLeft remaining seconds
     */
    void onTick(int secondsLeft);

    /**
     * Called when the player passes a level.
     *
     * @param completedLevels total number of completed levels
     */
    void onLevelPassed(int completedLevels);

    /**
     * Called when the player submits a wrong answer before time runs out.
     */
    void onWrongAnswer();

    /**
     * Called when the game ends.
     *
     * @param completedLevels total number of completed levels
     * @param secondsLeft     remaining seconds when the game ended
     * @param won             true if the player reached the maximum level
     */
    void onGameOver(int completedLevels, int secondsLeft, boolean won);
}