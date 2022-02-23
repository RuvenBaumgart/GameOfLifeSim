package de.creode.View;

import de.creode.utilities.event.EventBus;
import de.creode.utilities.event.OptionsEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

        gridSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> this.handleGridSizeChanged((double)newValue));

        Slider simulationSpeed = new Slider(0.1,1,0.5);
        simulationSpeed.setShowTickMarks(true);
        simulationSpeed.setShowTickLabels(true);
        simulationSpeed.setMajorTickUnit(0.25f);
        simulationSpeed.setBlockIncrement(0.1f);

        Label speedLabel= new Label("Simulation Speed");

        simulationSpeed.valueProperty().addListener((observable, oldValue, newValue) -> this.handleSimulationSpeedChanged((double)newValue));

        this.setPadding(new Insets(4.0));
        this.setSpacing(4.0);
        this.getChildren().addAll(gridSizeLabel, gridSizeSlider, speedLabel, simulationSpeed);
    }

    private void handleGridSizeChanged(double newValue) {
        int value = (int)newValue;
        eventBus.emit(new OptionsEvent(OptionsEvent.Type.GRID_SIZE, value));
    }

    private void handleSimulationSpeedChanged(double  newValue){
        //eventBus.emit(new OptionsEvent(OptionsEvent.Type.SIMULATION_SPEED, newValue));
    }

}
