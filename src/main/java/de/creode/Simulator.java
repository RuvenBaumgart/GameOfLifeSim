package de.creode;

import de.creode.viewModel.BoardViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Simulator {
    private Simulation simulation;
    private Timeline timeline;
    private BoardViewModel boardViewModel;

    public Simulator(Simulation simulation, BoardViewModel boardViewModel) {
        this.simulation = simulation;
        this.boardViewModel = boardViewModel;
        timeline = new Timeline(new KeyFrame(Duration.millis(200), event -> this.doSimulation()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void doSimulation() {
        this.simulation.step();
        this.boardViewModel.setBoard(this.simulation.getBoard());
    }

    public void start(){
        this.timeline.play();
    }

    public void stop(){
        this.timeline.stop();
    }



}
