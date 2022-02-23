package de.creode.viewModel;

import de.creode.logic.Simulation;
import de.creode.model.ApplicationState;
import de.creode.model.StandardRule;

import de.creode.utilities.event.ToolBarEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SimulationViewModel {
    private Simulation simulation;
    private Timeline timeline;
    private BoardViewModel boardViewModel;
    private ApplicationViewModel applicationViewModel;


    public SimulationViewModel( BoardViewModel boardViewModel, ApplicationViewModel applicationViewModel) {
        this.boardViewModel = boardViewModel;
        this.applicationViewModel = applicationViewModel;
        timeline = new Timeline(new KeyFrame(Duration.millis(200), event -> this.doSimulation()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.simulation = new Simulation(boardViewModel.getBoardProperty().get(), new StandardRule());
    }


    public void doSimulation() {
        if(this.applicationViewModel.getProperty().get() != ApplicationState.SIMULATING)
            this.applicationViewModel.getProperty().set(ApplicationState.SIMULATING);
        this.simulation.step();
        this.boardViewModel.getBoardProperty().set(this.simulation.getBoard());
    }

    public void start(){
        this.timeline.play();
    }

    public void stop(){
        this.timeline.stop();
    }


    public void handle(ToolBarEvent toolBarEvent) {
        switch(toolBarEvent.getType()){
            case STEP:
                this.doSimulation();
                break;
            case STOP:
                this.stop();
                break;
            case START:
                this.start();
                break;
            case RESET:
                this.reset();
                break;
        }
    }

    private void reset() {
        this.applicationViewModel.getProperty().set(ApplicationState.EDITING);
        this.simulation = new Simulation(boardViewModel.getBoardProperty().get(), new StandardRule());
    }
}
