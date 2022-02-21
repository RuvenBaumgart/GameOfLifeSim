package de.creode.View;

import de.creode.model.ApplicationState;
import de.creode.model.CellState;
import de.creode.utilities.event.EventBus;
import de.creode.utilities.event.ToolBarEvent;
import de.creode.viewModel.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private EditorViewModel editorViewModel;
    private EventBus eventBus;
    public Toolbar(EditorViewModel editorViewModel, EventBus eventBus) {
        this.eventBus = eventBus;
        this.editorViewModel = editorViewModel;

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
        eventBus.emit(new ToolBarEvent(ToolBarEvent.Type.START));
    }

    private void handleStop(ActionEvent actionEvent) {
        eventBus.emit(new ToolBarEvent(ToolBarEvent.Type.STOP));
    }

    private void handleReset(ActionEvent actionEvent) {
        eventBus.emit(new ToolBarEvent(ToolBarEvent.Type.RESET));
    }

    private void handleStep(ActionEvent actionEvent) {

        eventBus.emit(new ToolBarEvent(ToolBarEvent.Type.STEP));
    }

    private void handleErase(ActionEvent actionEvent) {
        editorViewModel.getCellStateProperty().set(CellState.DEAD);
    }

    private void handleDraw(ActionEvent actionEvent) {
        editorViewModel.getCellStateProperty().set(CellState.ALIVE);
    }


}
