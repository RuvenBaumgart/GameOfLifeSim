package de.creode.model;

import javafx.scene.canvas.GraphicsContext;

public interface IDrawLayer {
    public void draw(GraphicsContext gc);
    public int getLayer();
    public void addListener(IDrawLayerListener listener);
    public void notifyListener();
}
