package de.creode;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

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
    }

    public void setDrawMode(int drawMode){
        String drawModeString;
        if(drawMode == Game.ALIVE){
            drawModeString = "Drawing";
        } else {
            drawModeString = "Erasing";
        }
        this.editingTool.setText(String.format(drawModeFormat, drawModeString));
    }

    public void setCursorPosFormat(int x, int y){
        this.cursor.setText((String.format(cursorPosFormat, x, y)));
    }
}
