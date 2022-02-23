package de.creode.model.board;

import de.creode.model.CellState;
import de.creode.model.IDrawLayer;
import de.creode.model.IDrawLayerListener;
import de.creode.viewModel.BoardViewModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class BoardDrawLayer implements IDrawLayer {

    private List<IDrawLayerListener> listeners = new LinkedList<>();
    private BoardViewModel boardViewModel;

    public BoardDrawLayer(BoardViewModel boardViewModel){
        this.boardViewModel = boardViewModel;
    }

    @Override
    public void draw(GraphicsContext gc) {
        Board board = boardViewModel.getBoardProperty().get();
        gc.setFill(Color.BLACK);
        for (int i = 0; i < board.getHeigth() ; i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.getState(i, j) == CellState.ALIVE) {
                    gc.fillRect(i, j, 1, 1);
                }
            }
        }
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public void addListener(IDrawLayerListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void notifyListener() {
        listeners.forEach(IDrawLayerListener::isInvalid);
    }

}
