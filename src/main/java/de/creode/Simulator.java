package de.creode;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Simulator {
    private Game game;
    private MainView mainView;
    private Timeline timeline;

    public Simulator(Game game, MainView mainView) {
        this.game = game;
        this.mainView = mainView;
        timeline = new Timeline(new KeyFrame(Duration.millis(200), this::doSimulation));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void doSimulation(ActionEvent actionEvent) {
        this.game.step();
        this.mainView.draw();
    }

    public void start(){
        this.timeline.play();
    }

    public void stop(){
        this.timeline.stop();
    }



}
