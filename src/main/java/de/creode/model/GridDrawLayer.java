package de.creode.model;

import de.creode.viewModel.BoardViewModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class GridDrawLayer implements  IDrawLayer{

    private List<IDrawLayerListener> listeners = new LinkedList<>();
    private BoardViewModel boardViewModel;

    public GridDrawLayer(BoardViewModel boardViewModel){
        this.boardViewModel = boardViewModel;
    }

    @Override
    public void draw(GraphicsContext gc) {
        Board board = this.boardViewModel.getBoardProperty().get();
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(0.05d);
        for (int i = 0; i <= board.getHeigth(); i++) {
            gc.strokeLine(i,0, i, board.getWidth());
        }

        for (int i = 0; i <= board.getWidth(); i++) {
            gc.strokeLine(0, i, board.getWidth(), i);
        }
    }

    @Override
    public int getLayer() {
        return 10;
    }

    @Override
    public void addListener(IDrawLayerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void notifyListener() {
        listeners.forEach(IDrawLayerListener::isInvalid);
    }
}
