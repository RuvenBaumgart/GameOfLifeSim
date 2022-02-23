package de.creode.View;

import de.creode.model.*;

import de.creode.utilities.event.EventBus;
import de.creode.utilities.event.MyMouseEvent;
import de.creode.viewModel.BoardViewModel;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SimulationView extends Pane {
    private static final int C_HEIGHT = 900;
    private static final int C_WIDTH = 800;
    private Canvas canvas;
    private Affine affine;
    private EventBus eventBus;

    private List<IDrawLayer> drawLayers = new LinkedList<>();


    public SimulationView(EventBus eventBus) {

        this.eventBus = eventBus;
        this.canvas = new Canvas(C_WIDTH, C_HEIGHT);
        this.canvas.setOnMouseClicked(this::handleMouseDraw);
        this.canvas.setOnMouseDragged(this::handleMouseDraw);
        this.canvas.setOnMouseMoved(this::handleMouseMoved);
        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());
        this.getChildren().add(this.canvas);
        setAffineScale();
    }

    public void addDrawLayer(IDrawLayer drawLayer){
        this.drawLayers.add(drawLayer);
        this.drawLayers.sort(Comparator.comparing(IDrawLayer::getLayer));
        drawLayer.addListener(this::draw);
    }

    private void handleMouseMoved(MouseEvent mouseEvent) {
        CursorPosition cursorPosition = getSimulationCoordinates(mouseEvent);
        this.eventBus.emit(new MyMouseEvent(MyMouseEvent.Type.MOVED, cursorPosition));
    }

    @Override
        public void resize(double width, double height){
            super.resize(width, height);
            setAffineScale();
            draw();
        }

    private void handleMouseDraw(MouseEvent mouseEvent) {
        CursorPosition cursorPosition = getSimulationCoordinates(mouseEvent);
        this.eventBus.emit(new MyMouseEvent(MyMouseEvent.Type.DRAGED, cursorPosition));
    }

    private CursorPosition getSimulationCoordinates(MouseEvent event){
        try {
            Point2D simCoordinates = this.affine.inverseTransform(event.getX(), event.getY());
            return new CursorPosition ((int)simCoordinates.getX(), (int)simCoordinates.getY());
        } catch (NonInvertibleTransformException e) {
            throw new RuntimeException("Inverse Transformation Error");
        }
    }

    private void setAffineScale(){
        this.affine = new Affine();
        this.affine.appendScale(this.getWidth()/25.0, this.getHeight()/25.0);
    }

    public void draw(){
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setTransform(this.affine);
        fill(gc);

        for (IDrawLayer drawLayer : drawLayers) {
            drawLayer.draw(gc);
        }

    }

    private void fill(GraphicsContext gc){
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0, this.canvas.getWidth(), this.canvas.getHeight());
    }

}
