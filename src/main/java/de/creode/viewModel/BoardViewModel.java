package de.creode.viewModel;

import de.creode.model.Board;
import de.creode.model.BoundedBoard;

import java.util.LinkedList;
import java.util.List;

public class BoardViewModel {

    private Board board;
    private List<ISimpleChangeListener<Board>> listeners;

    public BoardViewModel(){
        listeners = new LinkedList<>();
    }

    public void listenToBoard(ISimpleChangeListener<Board> listener){
        this.listeners.add(listener);
    }

    public void setBoard(Board board) {
        this.board = board;
        notifyAllListeners();
    }

    private void notifyAllListeners() {
        for (ISimpleChangeListener<Board> listener : listeners) {
            listener.valueChanged(this.board);
        }
    }

    public Board getBoard() {
        return board;
    }
}
