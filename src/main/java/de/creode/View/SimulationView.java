package de.creode.View;

import de.creode.model.Board;
import de.creode.model.CellState;
import de.creode.model.CursorPosition;
import de.creode.utilities.event.EventBus;
import de.creode.utilities.event.MyMouseEvent;
import de.creode.viewModel.BoardViewModel;
import de.creode.viewModel.EditorViewModel;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class SimulationView extends Pane {
    private static final int C_HEIGHT = 600;
    private static final int C_WIDTH = 600;
    private Canvas canvas;
    private Affine affine;
    private EventBus eventBus;
    private BoardViewModel boardViewModel;

    public SimulationView(BoardViewModel boardViewModel, EventBus eventBus){
        this.boardViewModel = boardViewModel;
        this.eventBus = eventBus;

        this.canvas = new Canvas(C_HEIGHT, C_WIDTH);
        this.canvas.setOnMouseClicked(this::handleMouseDraw);
        this.canvas.setOnMouseDragged(this::handleMouseDraw);
        this.canvas.setOnMouseMoved(this::handleMouseMoved);

        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());

        this.getChildren().add(this.canvas);
        this.affine = new Affine();
        this.affine.appendScale(
                C_HEIGHT/this.boardViewModel.getBoardProperty().get().getHeigth(),
                C_WIDTH/this.boardViewModel.getBoardProperty().get().getWidth());

        }

    private void handleMouseMoved(MouseEvent mouseEvent) {
        CursorPosition cursorPosition = getSimulationCoordinates(mouseEvent);
        this.eventBus.emit(new MyMouseEvent(MyMouseEvent.Type.MOVED, cursorPosition));
    }

    @Override
        public void resize(double width, double height){
            super.resize(width, height);
            draw(boardViewModel.getBoardProperty().get());
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

    public void draw(Board board){
        fill();
        drawGrid(board);
        drawRect(board);
    }

    private void fill(){
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0,C_HEIGHT,C_WIDTH);
        gc.setTransform(this.affine);
    }

    private void drawGrid(Board board){
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(0.05d);
        for (int i = 0; i <= board.getWidth(); i++) {
            gc.strokeLine(i,0, i, board.getWidth());
        }

        for (int i = 0; i <= board.getHeigth(); i++) {
            gc.strokeLine(0, i, board.getWidth(), i);
        }
    }

    private void drawRect (Board board){
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        for (int i = 0; i < board.getWidth() ; i++) {
            for (int j = 0; j < board.getHeigth(); j++) {
                if (board.getState(i, j) == CellState.ALIVE) {
                    gc.fillRect(i, j, 1, 1);
                }
            }
        }
    }

    public void drawHighlight(CursorPosition cursorPosition){
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setFill(new Color(0.9, 0.8, 0.0, 1.0));
        gc.fillRect(cursorPosition.getPosX(), cursorPosition.getPosY(), 1, 1);
        }
}
