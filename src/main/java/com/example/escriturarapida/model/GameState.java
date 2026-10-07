package com.example.escriturarapida.model;

/**
 * Keeps the progress of the player and calculates the time per level.
 * The time starts at 20 seconds and decreases 2 seconds every 5 completed
 * levels, with a minimum of 2 seconds.
 */
public class GameState {

    /** Initial time per level, in seconds. */
    public static final int BASE_TIME = 20;
    /** Minimum time per level, in seconds. */
    public static final int MIN_TIME = 2;
    /** Number of completed levels needed to reduce the time. */
    public static final int LEVELS_PER_STEP = 5;
    /** Seconds removed each time the step is reached. */
    public static final int TIME_STEP = 2;

    private int completedLevels;

    /**
     * Returns the level the player is currently playing.
     *
     * @return the current level, starting at 1
     */
    public int getLevel() {
        return completedLevels + 1;
    }

    /**
     * Returns how many levels the player has completed.
     *
     * @return the number of completed levels
     */
    public int getCompletedLevels() {
        return completedLevels;
    }

    /**
     * Registers that the current level was passed.
     */
    public void levelCompleted() {
        completedLevels++;
    }

    /**
     * Calculates the time available for the current level.
     *
     * @return the time in seconds
     */
    public int getTimeForCurrentLevel() {
        int reduction = (completedLevels / LEVELS_PER_STEP) * TIME_STEP;
        return Math.max(MIN_TIME, BASE_TIME - reduction);
    }

    /**
     * Restarts the progress to the first level.
     */
    public void reset() {
        completedLevels = 0;
    }
}