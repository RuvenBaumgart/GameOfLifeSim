package de.creode;

import de.creode.model.CellState;
import de.creode.viewModel.ApplicationState;
import de.creode.viewModel.ApplicationViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private MainView mainView;
    private Simulator simulator;
    private ApplicationViewModel applicationViewModel;

    public Toolbar(MainView mainView, ApplicationViewModel applicationViewModel) {
        this.mainView = mainView;
        this.applicationViewModel = applicationViewModel;
        Button draw = new Button("Draw");
        draw.setOnAction(this::handleDraw);

        Button erase = new Button("Erase");
        erase.setOnAction(this::handleErase);

        Button step = new Button("Step");
        step.setOnAction(this::handleStep);

        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);

        Button start = new Button("Start");
        start.setOnAction(this::handleStart);

        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);

        this.getItems().addAll(draw, erase, step, reset, start, stop);

    }

    private void handleStart(ActionEvent actionEvent) {
        this.applicationViewModel.setCurrentState(ApplicationState.SIMULATING);
        this.simulator = new Simulator(this.mainView.getSimulation(), this.mainView);
        this.simulator.start();
    }

    private void handleStop(ActionEvent actionEvent) {
        this.simulator.stop();
    }

    private void handleReset(ActionEvent actionEvent) {
        this.applicationViewModel.setCurrentState(ApplicationState.EDITING);
        this.simulator = null;
        this.mainView.draw();
    }

    private void handleStep(ActionEvent actionEvent) {
        this.applicationViewModel.setCurrentState(ApplicationState.SIMULATING);
        this.simulator = new Simulator(this.mainView.getSimulation(), this.mainView);
        this.mainView.getSimulation().step();
        this.mainView.draw();
    }

    private void handleErase(ActionEvent actionEvent) {
        this.mainView.setDrawMode(CellState.DEAD);
    }

    private void handleDraw(ActionEvent actionEvent) {
        this.mainView.setDrawMode(CellState.ALIVE);
    }


}
