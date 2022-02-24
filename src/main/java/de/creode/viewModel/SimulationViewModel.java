package de.creode.viewModel;

import de.creode.logic.Simulation;
import de.creode.model.ApplicationState;
import de.creode.model.StandardRule;

import de.creode.utilities.event.OptionsEvent;
import de.creode.utilities.event.ToolBarEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SimulationViewModel {
    private Simulation simulation;
    private Timeline timeline;
    private int timelineDuration;
    private BoardViewModel boardViewModel;
    private ApplicationViewModel applicationViewModel;


    public SimulationViewModel( BoardViewModel boardViewModel, ApplicationViewModel applicationViewModel) {
        this.boardViewModel = boardViewModel;
        this.applicationViewModel = applicationViewModel;
        this.simulation = new Simulation(boardViewModel.getBoardProperty().get(), new StandardRule());
        this.timelineDuration = 200;
    }


    public void doSimulation() {
        if(this.applicationViewModel.getProperty().get() != ApplicationState.SIMULATING)
            this.applicationViewModel.getProperty().set(ApplicationState.SIMULATING);
        this.simulation.step();
        this.boardViewModel.getBoardProperty().set(this.simulation.getBoard());
    }

    public void start(){
        createNewTimeline();
        this.timeline.play();
    }

    public void setTimelineDuration(int timelineDuration) {
        int transformedTimeLineValue = 501 - timelineDuration;
        this.timelineDuration = transformedTimeLineValue;
    }

    public void createNewTimeline(){
        this.timeline = new Timeline();
        this.timeline.getKeyFrames().add(new KeyFrame(Duration.millis(timelineDuration), event->this.doSimulation()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }



    public void stop(){
        if(this.timeline != null)
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

    public void handle(OptionsEvent optionsEvent){
        switch (optionsEvent.getType()){
            case GRID_SIZE:
                this.reset();
                break;
            case SIMULATION_SPEED:
                this.stop();
                this.setTimelineDuration(optionsEvent.getValue());
                this.start();
                break;
        }
    }

    public void reset() {
        this.applicationViewModel.getProperty().set(ApplicationState.EDITING);
        this.simulation = new Simulation(boardViewModel.getBoardProperty().get(), new StandardRule());
    }
}
