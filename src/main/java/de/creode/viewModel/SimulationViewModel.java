package de.creode.viewModel;

import de.creode.Simulation;
import de.creode.model.StandardRule;
import de.creode.viewModel.BoardViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class SimulationViewModel {
    private Simulation simulation;
    private Timeline timeline;
    private BoardViewModel boardViewModel;

    public SimulationViewModel( BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        timeline = new Timeline(new KeyFrame(Duration.millis(200), event -> this.doSimulation()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void onAppStateChange(ApplicationState state){
        if(state == ApplicationState.SIMULATING){
            this.simulation = new Simulation(boardViewModel.getBoard(), new StandardRule());
        }
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
