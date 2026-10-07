package com.example.escriturarapida.ui;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Visual timer drawn as a bomb. The remaining seconds are shown inside the
 * bomb and the fuse gets shorter while time passes. A spark burns at the end
 * of the fuse. In the last seconds the bomb turns red and shakes.
 */
public class BombTimer extends Pane {

    private static final double WIDTH = 180;
    private static final double HEIGHT = 200;
    private static final double CENTER_X = 90;
    private static final double CENTER_Y = 132;
    private static final double RADIUS = 54;
    private static final int FUSE_STEPS = 24;
    private static final int DANGER_SECONDS = 3;

    /** Control points of the fuse curve: start (neck), control and end (tip). */
    private static final double[] P0 = {90, 70};
    private static final double[] P1 = {88, 28};
    private static final double[] P2 = {132, 20};

    private final Circle body = new Circle(CENTER_X, CENTER_Y, RADIUS);
    private final Polyline fuse = new Polyline();
    private final Group spark = new Group();
    private final Label timeLabel = new Label("20");
    private final DoubleProperty fuseProgress = new SimpleDoubleProperty(1.0);
    private final RotateTransition wiggle;

    private Timeline fuseTimeline;
    private boolean danger;

    /**
     * Creates the bomb with all its shapes and animations.
     */
    public BombTimer() {
        setPrefSize(WIDTH, HEIGHT);
        setMinSize(WIDTH, HEIGHT);
        setMaxSize(WIDTH, HEIGHT);

        body.setStroke(Color.web("#0b0b12"));
        body.setStrokeWidth(2);
        paintBody(false);

        Rectangle neck = new Rectangle(76, 66, 28, 16);
        neck.setArcWidth(6);
        neck.setArcHeight(6);
        neck.setFill(Color.web("#2a2a38"));
        neck.setStroke(Color.web("#0b0b12"));

        fuse.setStroke(Color.web("#d9c38a"));
        fuse.setStrokeWidth(4);
        fuse.setStrokeLineCap(StrokeLineCap.ROUND);
        fuse.setStrokeLineJoin(StrokeLineJoin.ROUND);

        Circle highlight = new Circle(70, 110, 13, Color.color(1, 1, 1, 0.16));
        highlight.setMouseTransparent(true);

        timeLabel.setPrefSize(110, 60);
        timeLabel.setLayoutX(CENTER_X - 55);
        timeLabel.setLayoutY(CENTER_Y - 30);
        timeLabel.setAlignment(Pos.CENTER);
        timeLabel.setFont(Font.font("System", FontWeight.BOLD, 36));
        timeLabel.setTextFill(Color.WHITE);

        spark.getChildren().addAll(
                new Circle(0, 0, 8, Color.ORANGE),
                new Circle(0, 0, 4, Color.YELLOW));
        spark.setEffect(new DropShadow(14, Color.ORANGE));
        spark.setMouseTransparent(true);

        getChildren().addAll(body, neck, fuse, highlight, timeLabel, spark);

        fuseProgress.addListener((obs, oldValue, newValue) -> updateFuse(newValue.doubleValue()));
        updateFuse(1.0);

        ScaleTransition flicker = new ScaleTransition(Duration.millis(120), spark);
        flicker.setFromX(0.7);
        flicker.setToX(1.3);
        flicker.setFromY(0.7);
        flicker.setToY(1.3);
        flicker.setAutoReverse(true);
        flicker.setCycleCount(Timeline.INDEFINITE);
        flicker.play();

        wiggle = new RotateTransition(Duration.millis(70), this);
        wiggle.setFromAngle(-3);
        wiggle.setToAngle(3);
        wiggle.setAutoReverse(true);
        wiggle.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Starts the fuse animation for a new level. The fuse burns at a constant
     * speed and reaches the bomb exactly when the time is over.
     *
     * @param totalSeconds the time available for the level
     */
    public void start(int totalSeconds) {
        stop();
        setSecondsLeft(totalSeconds);
        fuseProgress.set(1.0);
        fuseTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(fuseProgress, 1.0)),
                new KeyFrame(Duration.seconds(totalSeconds),
                        new KeyValue(fuseProgress, 0.0, Interpolator.LINEAR)));
        fuseTimeline.play();
    }

    /**
     * Stops all the animations of the bomb and restores its normal look.
     */
    public void stop() {
        if (fuseTimeline != null) {
            fuseTimeline.stop();
        }
        wiggle.stop();
        setRotate(0);
        danger = false;
        paintBody(false);
        timeLabel.setTextFill(Color.WHITE);
    }

    /**
     * Updates the number shown inside the bomb. In the last seconds the bomb
     * turns red and starts to shake.
     *
     * @param secondsLeft the remaining seconds
     */
    public void setSecondsLeft(int secondsLeft) {
        timeLabel.setText(String.valueOf(Math.max(secondsLeft, 0)));
        boolean nowDanger = secondsLeft <= DANGER_SECONDS;
        if (nowDanger != danger) {
            danger = nowDanger;
            paintBody(danger);
            timeLabel.setTextFill(danger ? Color.web("#ffb3c0") : Color.WHITE);
            if (danger) {
                wiggle.play();
            } else {
                wiggle.stop();
                setRotate(0);
            }
        }
    }

    private void paintBody(boolean red) {
        Color light = red ? Color.web("#9b2c3f") : Color.web("#4d4d63");
        Color dark = red ? Color.web("#2b0b13") : Color.web("#14141d");
        body.setFill(new RadialGradient(0, 0, 0.35, 0.3, 0.9, true,
                CycleMethod.NO_CYCLE, new Stop(0, light), new Stop(1, dark)));
    }

    /**
     * Redraws the fuse from the bomb up to the burning point.
     *
     * @param progress 1.0 = full fuse, 0.0 = completely burnt
     */
    private void updateFuse(double progress) {
        List<Double> points = new ArrayList<>();
        for (int i = 0; i <= FUSE_STEPS; i++) {
            double t = progress * i / FUSE_STEPS;
            points.add(bezier(P0[0], P1[0], P2[0], t));
            points.add(bezier(P0[1], P1[1], P2[1], t));
        }
        fuse.getPoints().setAll(points);
        spark.setLayoutX(bezier(P0[0], P1[0], P2[0], progress));
        spark.setLayoutY(bezier(P0[1], P1[1], P2[1], progress));
        spark.setVisible(progress > 0.0);
    }

    private static double bezier(double a, double b, double c, double t) {
        double u = 1 - t;
        return u * u * a + 2 * u * t * b + t * t * c;
    }
}