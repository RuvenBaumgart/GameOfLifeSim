package de.creode;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Simulator {
    private Simulation simulation;
    private MainView mainView;
    private Timeline timeline;

    public Simulator(Simulation simulation, MainView mainView) {
        this.simulation = simulation;
        this.mainView = mainView;
        timeline = new Timeline(new KeyFrame(Duration.millis(200), this::doSimulation));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void doSimulation(ActionEvent actionEvent) {
        this.simulation.step();
        this.mainView.draw();
    }

    public void start(){
        this.timeline.play();
    }

    public void stop(){
        this.timeline.stop();
    }



}
