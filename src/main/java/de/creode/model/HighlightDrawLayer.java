package de.creode.model;

import de.creode.viewModel.BoardViewModel;
import de.creode.viewModel.EditorViewModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class HighlightDrawLayer implements IDrawLayer{
    private List<IDrawLayerListener> listeners = new LinkedList<>();
    private EditorViewModel editorViewModel;

    public HighlightDrawLayer(EditorViewModel editorViewModel){
        this.editorViewModel = editorViewModel;
    }

    @Override
    public void draw(GraphicsContext gc) {
        if(editorViewModel.getCursorPositionProperty().isPresent()) {
            CursorPosition cursorPosition = editorViewModel.getCursorPositionProperty().get();
            gc.setFill(new Color(0.9, 0.9, 0.0, 1.0));
            gc.fillRect(cursorPosition.getPosX(), cursorPosition.getPosY(), 1, 1);
        }
    }

    @Override
    public int getLayer() {
        return 0;
    }

    @Override
    public void addListener(IDrawLayerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void notifyListener() {
        for (IDrawLayerListener listener : listeners) {
            listener.isInvalid();
        }
    }
}
