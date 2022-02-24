package de.creode.View;

import de.creode.utilities.event.EventBus;
import de.creode.utilities.event.OptionsEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class OptionsView extends VBox {

    private EventBus eventBus;
    public OptionsView (EventBus eventBus){
        this.eventBus = eventBus;
        Slider gridSizeSlider = new Slider(5,100,25);
        gridSizeSlider.setShowTickMarks(true);
        gridSizeSlider.setShowTickLabels(true);
        gridSizeSlider.setMajorTickUnit(5);
        gridSizeSlider.setBlockIncrement(1);

        Label gridSizeLabel = new Label("Grid Size");
        gridSizeLabel.getStyleClass().add("gridLabel");

        gridSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> this.handleGridSizeChanged((double)newValue));

        Slider simulationSpeed = new Slider(1,500,301);
        simulationSpeed.setShowTickMarks(true);
        simulationSpeed.setShowTickLabels(true);
        simulationSpeed.setMajorTickUnit(25);
        simulationSpeed.setBlockIncrement(5);

        Label speedLabel= new Label("Simulation Speed");
        speedLabel.getStyleClass().add("speedLabel");

        simulationSpeed.valueProperty().addListener((observable, oldValue, newValue) -> this.handleSimulationSpeedChanged((double)newValue));

        this.setPadding(new Insets(4.0));
        this.setSpacing(4.0);
        this.getChildren().addAll(gridSizeLabel, gridSizeSlider, speedLabel, simulationSpeed);
    }

    private void handleGridSizeChanged(double newValue) {
        int value = (int)newValue;
        eventBus.emit(new OptionsEvent(OptionsEvent.Type.GRID_SIZE, value));
    }

    private void handleSimulationSpeedChanged(double newValue){
        int value = (int)newValue;
        eventBus.emit(new OptionsEvent(OptionsEvent.Type.SIMULATION_SPEED, value));
    }

}
