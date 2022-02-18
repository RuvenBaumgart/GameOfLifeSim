package de.creode.viewModel;

import de.creode.model.Board;
import de.creode.model.CellState;
import de.creode.utilities.Property;

import java.util.LinkedList;
import java.util.List;

public class EditorViewModel {
    private Board editorBoard;
    private boolean drawingEnabled = true;
    private BoardViewModel boardViewModel;

    private final Property<CellState> cellStateProperty = new Property<>(CellState.ALIVE);

    public EditorViewModel(BoardViewModel boardViewModel, Board initialBoard){
        this.editorBoard = initialBoard;
        this.boardViewModel = boardViewModel;
    }

    public void onAppStateChanged(ApplicationState state){
        if(state == ApplicationState.EDITING){
            drawingEnabled = true;
            boardViewModel.getBoardProperty().set(editorBoard);
        } else if(state == ApplicationState.SIMULATING) {
            drawingEnabled = false;
        }
    }

    public void boardPresses(int mouseSimX, int mouseSimY) {
        if(drawingEnabled){
            this.editorBoard.setState(mouseSimX, mouseSimY, cellStateProperty.get());
            this.boardViewModel.getBoardProperty().set(editorBoard);
        }
    }

    public Property<CellState> getCellStateProperty() {
        return cellStateProperty;
    }
}
