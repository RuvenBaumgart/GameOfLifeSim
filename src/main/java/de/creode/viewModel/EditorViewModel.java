package de.creode.viewModel;

import de.creode.model.Board;
import de.creode.model.CellState;

import java.util.LinkedList;
import java.util.List;

public class EditorViewModel {
    private Board editorBoard;
    private CellState drawMode = CellState.ALIVE;
    private List<ISimpleChangeListener<CellState>> listeners;
    private boolean drawingEnabled = true;
    private BoardViewModel boardViewModel;

    public EditorViewModel(BoardViewModel boardViewModel, Board initialBoard){
        this.editorBoard = initialBoard;
        this.boardViewModel = boardViewModel;
        listeners = new LinkedList<>();
    }


    public void onAppStateChanged(ApplicationState state){
        if(state == ApplicationState.EDITING){
            drawingEnabled = true;
            boardViewModel.setBoard(editorBoard);
        } else if(state == ApplicationState.SIMULATING) {
            drawingEnabled = false;
        }
    }

    public void listenToEditorViewModel(ISimpleChangeListener<CellState> listener){
        this.listeners.add(listener);
    }

    public void setDrawMode(CellState mode){
        this.drawMode = mode;
        notifyAllListeners(mode);
    }

    public void notifyAllListeners(CellState mode){
        for (ISimpleChangeListener<CellState> listener : listeners) {
            listener.valueChanged(mode);
        }
    }

    public void boardPresses(int mouseSimX, int mouseSimY) {
        if(drawingEnabled){
            this.editorBoard.setState(mouseSimX, mouseSimY, drawMode);
            this.boardViewModel.setBoard(editorBoard);
        }
    }
}
