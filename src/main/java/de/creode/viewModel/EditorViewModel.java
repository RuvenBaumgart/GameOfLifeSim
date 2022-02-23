package de.creode.viewModel;

import de.creode.model.ApplicationState;
import de.creode.model.board.Board;
import de.creode.model.CellState;
import de.creode.model.CursorPosition;
import de.creode.model.board.BoundedBoard;
import de.creode.utilities.Property;

import de.creode.utilities.event.MyMouseEvent;
import de.creode.utilities.event.OptionsEvent;
import de.creode.utilities.event.ToolBarEvent;

public class EditorViewModel {
    private Board editorBoard;
    private boolean drawingEnabled = true;
    private BoardViewModel boardViewModel;

    private final Property<CellState> cellStateProperty = new Property<>(CellState.ALIVE);
    private final Property<CursorPosition> cursorPositionProperty = new Property<>();

    public EditorViewModel(BoardViewModel boardViewModel){
        this.editorBoard = boardViewModel.getBoardProperty().get();
        this.boardViewModel = boardViewModel;
    }

    public void setEditorBoard(Board editorBoard){
        this.editorBoard = editorBoard;
    }

    public void onAppStateChanged(ApplicationState state){
        if(state == ApplicationState.EDITING){
            drawingEnabled = true;
            boardViewModel.getBoardProperty().set(editorBoard);
        } else if(state == ApplicationState.SIMULATING) {
            drawingEnabled = false;
        }
    }

    public void boardPresses(CursorPosition cursorPosition) {
        if(drawingEnabled){
            this.getCursorPositionProperty().set(cursorPosition);
            this.editorBoard.setState(cursorPosition.getPosX(), cursorPosition.getPosY(), cellStateProperty.get());
            this.boardViewModel.getBoardProperty().set(editorBoard);
        }
    }

    public Property<CellState> getCellStateProperty() {
        return cellStateProperty;
    }

    public Property<CursorPosition> getCursorPositionProperty() {
        return cursorPositionProperty;
    }

    public  void handle(ToolBarEvent toolBarEvent) {
        switch (toolBarEvent.getType()){
            case DRAW:
                cellStateProperty.set(CellState.ALIVE);
                break;
            case ERASE:
                cellStateProperty.set(CellState.DEAD);
                break;
        }
    }

    public void handle(MyMouseEvent mouseEvent){
        switch (mouseEvent.getType()){
            case MOVED:
                this.cursorPositionProperty.set(mouseEvent.getCursorPosition());
                break;
            case DRAGED:
                this.boardPresses(mouseEvent.getCursorPosition());
                break;
        }
    }

    public void handle(OptionsEvent optionsEvent){
        switch (optionsEvent.getType()){
            case GRID_SIZE:
                this.setEditorBoard(new BoundedBoard(optionsEvent.getValue(), optionsEvent.getValue()));
                break;
        }
    }
}
