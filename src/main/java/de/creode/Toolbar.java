package de.creode;

import de.creode.model.Board;
import de.creode.model.CellState;
import de.creode.model.StandardRule;
import de.creode.viewModel.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    private SimulationViewModel simulationViewModel;
    private ApplicationViewModel applicationViewModel;
    private EditorViewModel editorViewModel;
    public Toolbar(EditorViewModel editorViewModel, ApplicationViewModel applicationViewModel, SimulationViewModel simulationViewModel) {

        this.applicationViewModel = applicationViewModel;
        this.editorViewModel = editorViewModel;
        this.simulationViewModel = simulationViewModel;
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
        changeToSimulationState();
        this.simulationViewModel.start();
    }

    private void handleStop(ActionEvent actionEvent) {
        this.simulationViewModel.stop();
    }

    private void handleReset(ActionEvent actionEvent) {
        this.applicationViewModel.getProperty().set(ApplicationState.EDITING);
    }

    private void handleStep(ActionEvent actionEvent) {
        changeToSimulationState();
        this.simulationViewModel.doSimulation();
    }

    private void changeToSimulationState(){
        this.applicationViewModel.getProperty().set(ApplicationState.SIMULATING);
    }

    private void handleErase(ActionEvent actionEvent) {
        editorViewModel.setDrawMode(CellState.DEAD);
    }

    private void handleDraw(ActionEvent actionEvent) {
        editorViewModel.setDrawMode(CellState.ALIVE);
    }


}
