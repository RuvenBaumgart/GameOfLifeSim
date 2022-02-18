package de.creode.View;

import de.creode.model.Board;
import de.creode.model.CellState;
import de.creode.model.CursorPosition;
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
    private EditorViewModel editorViewModel;
    private BoardViewModel boardViewModel;

    public SimulationView(EditorViewModel editorViewModel, BoardViewModel boardViewModel){
        this.boardViewModel = boardViewModel;
        this.boardViewModel.getBoardProperty().listen(this::draw);
        this.editorViewModel = editorViewModel;
        this.editorViewModel.getCursorPositionProperty().listen(cellPosition -> draw(boardViewModel.getBoardProperty().get()));

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
        this.editorViewModel.getCursorPositionProperty().set(cursorPosition);
    }

    @Override
        public void resize(double width, double height){
            super.resize(width, height);
            draw(boardViewModel.getBoardProperty().get());
        }

    private void handleMouseDraw(MouseEvent mouseEvent) {
        CursorPosition cursorPosition = getSimulationCoordinates(mouseEvent);
        this.editorViewModel.boardPresses(cursorPosition);
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
        drawHighlight();
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

    private void drawHighlight(){
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        if(editorViewModel.getCursorPositionProperty().isPresent()){
            CursorPosition cursorPosition = editorViewModel.getCursorPositionProperty().get();
            gc.setFill(new Color(0, 0, 0.0, 1.0));
            gc.fillRect(cursorPosition.getPosX(), cursorPosition.getPosY(), 1, 1);
        }
    }
}
