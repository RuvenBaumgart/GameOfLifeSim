package de.creode;

import de.creode.model.Board;
import de.creode.model.BoundedBoard;
import de.creode.model.CellState;
import de.creode.model.StandardRule;
import de.creode.viewModel.*;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {
    private static final int C_HEIGHT = 600;
    private static final int C_WIDTH = 600;
    private Canvas canvas;
    private Affine affine;
    private Infobar infobar;
    private ApplicationViewModel appViewModel;
    private BoardViewModel boardViewModel;
    private EditorViewModel editorViewModel;
    private SimulationViewModel simulationViewModel;

    public MainView(ApplicationViewModel viewModel, BoardViewModel boardViewModel, EditorViewModel editorViewModel, SimulationViewModel simulationViewModel) {
        this.canvas = new Canvas(C_HEIGHT, C_WIDTH);
        this.canvas.setOnMouseClicked(this::handleMouseDraw);
        this.canvas.setOnMouseDragged(this::handleMouseDraw);
        this.canvas.setOnMouseMoved(this::handleMouseMoved);

        this.editorViewModel = editorViewModel;
        this.appViewModel = viewModel;
        this.boardViewModel = boardViewModel;
        this.boardViewModel.listenToBoard(this::onBoardChanged);
        this.simulationViewModel = simulationViewModel;
        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.infobar = new Infobar(editorViewModel );

        this.infobar.setCursorPosFormat(0,0);

        Toolbar toolbar = new Toolbar(this.editorViewModel, this.appViewModel, this.simulationViewModel );
        this.getChildren().addAll(toolbar, this.canvas, spacer, this.infobar);

        this.affine = new Affine();
        this.affine.appendScale(C_HEIGHT/boardViewModel.getBoard().getHeigth(),C_WIDTH/boardViewModel.getBoard().getWidth());
    }

    private void handleMouseMoved(MouseEvent mouseEvent) {
        Point2D simCoordinates = getSimulationCoordinates(mouseEvent);
        int mouseSimX = (int)simCoordinates.getX();
        int mouseSimY = (int)simCoordinates.getY();
        this.infobar.setCursorPosFormat(mouseSimX, mouseSimY);
    }


    private void onBoardChanged(Board board){
        draw(board);
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
