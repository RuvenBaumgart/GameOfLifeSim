package de.creode.View;

import de.creode.App;
import de.creode.model.ApplicationState;
import de.creode.model.CellState;
import de.creode.model.CursorPosition;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;


public class Infobar extends HBox {
    private static String drawModeFormat = "Draw Mode: %s";
    private static String cursorPosFormat = "Cursor: (%d, %d)";
    private static String appStateFormat = "State: %s";
    private Label cursor;
    private Label editingTool;
    private Label appState;

    public Infobar() {

        this.cursor = new Label();
        cursor.setPadding(new Insets(5.0));
        cursor.getStyleClass().add("cursorLabel");

        this.editingTool = new Label();
        editingTool.setPadding(new Insets(5.0));
        editingTool.getStyleClass().add("editingStatus");

        this.appState = new Label();
        appState.setPadding(new Insets(5.0));
        appState.getStyleClass().add("appStateLabel");

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.setSpacing(8.0);
        this.getChildren().addAll(this.cursor, spacer, this.appState, this.editingTool);
        this.setCursorPosFormat(new CursorPosition(0,0));
        this.displayMode(CellState.ALIVE);
        this.displayState(ApplicationState.EDITING);

    }

    public void displayState(ApplicationState state){
        String newState;
        if(state == ApplicationState.SIMULATING){
            newState = "Simulating";
        } else {
            newState = "Editing";
        }
        this.appState.setText(String.format(appStateFormat, newState));
    }

    public void displayMode(CellState drawMode){
        String drawModeString;
        if(drawMode == CellState.ALIVE){
            drawModeString = "Drawing";
        } else {
            drawModeString = "Erasing";
        }
        this.editingTool.setText(String.format(drawModeFormat, drawModeString));
    }

    public void setCursorPosFormat(CursorPosition cp){
        this.cursor.setText((String.format(cursorPosFormat, cp.getPosX(), cp.getPosY())));
    }

}
