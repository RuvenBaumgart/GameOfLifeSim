package de.creode.View;

import de.creode.model.CellState;
import de.creode.model.CursorPosition;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;


public class Infobar extends HBox {
    private static String drawModeFormat = "Draw Mode: %s";
    private static String cursorPosFormat = "Cursor: (%d, %d)";
    private Label cursor;
    private Label editingTool;

    public Infobar() {

        this.cursor = new Label();
        this.editingTool = new Label();

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(this.cursor, spacer, this.editingTool);
        this.setCursorPosFormat(new CursorPosition(0,0));
        this.displayMode(CellState.ALIVE);

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
