package de.creode.View;

import de.creode.model.Board;
import de.creode.model.CellState;
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
        this.boardViewModel.listenToBoard(this::draw);
        this.editorViewModel = editorViewModel;
        this.canvas = new Canvas(C_HEIGHT, C_WIDTH);
        this.canvas.setOnMouseClicked(this::handleMouseDraw);
        this.canvas.setOnMouseDragged(this::handleMouseDraw);

        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());

        this.getChildren().add(this.canvas);

        this.affine = new Affine();
        this.affine.appendScale(
                C_HEIGHT/this.boardViewModel.getBoard().getHeigth(),
                C_WIDTH/this.boardViewModel.getBoard().getWidth());
        }

        @Override
        public void resize(double width, double height){
            super.resize(width, height);
            draw(boardViewModel.getBoard());
        }

    private void handleMouseDraw(MouseEvent mouseEvent) {
        Point2D simCoordinates = getSimulationCoordinates(mouseEvent);
        int mouseSimX = (int)simCoordinates.getX();
        int mouseSimY = (int)simCoordinates.getY();
        this.editorViewModel.boardPresses(mouseSimX, mouseSimY);
    }

    private Point2D getSimulationCoordinates(MouseEvent event){
        try {
            return this.affine.inverseTransform(event.getX(), event.getY());
        } catch (NonInvertibleTransformException e) {
            throw new RuntimeException("Inverse Transformation Error");
        }
    }

    public void draw(Board board){
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0,C_HEIGHT,C_WIDTH);
        gc.setTransform(this.affine);
        drawBoard(board);
    }

    private void drawBoard (Board board){
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        for (int i = 0; i < board.getWidth() ; i++) {
            for (int j = 0; j < board.getHeigth(); j++) {
                if(board.getState(i,j) == CellState.ALIVE){
                    gc.fillRect(i, j, 1, 1 );
                }
            }
        }
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(0.05d);
        for (int i = 0; i <= board.getWidth(); i++) {
            gc.strokeLine(i,0, i, board.getWidth());
        }

        for (int i = 0; i <= board.getHeigth(); i++) {
            gc.strokeLine(0, i, board.getWidth(), i);
        }
    }
}
