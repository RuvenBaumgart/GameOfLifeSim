package de.creode;

import de.creode.model.Board;
import de.creode.model.BoundedBoard;
import de.creode.model.CellState;
import de.creode.model.StandardRule;
import de.creode.viewModel.ApplicationState;
import de.creode.viewModel.ApplicationViewModel;
import de.creode.viewModel.BoardViewModel;
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
    private Board initalBoard;
    private Affine affine;
    private CellState drawMode = CellState.ALIVE;
    private Infobar infobar;
    private ApplicationViewModel appViewModel;
    private boolean isDrawingEnabled = true;
    private BoardViewModel boardViewModel;

    public MainView(ApplicationViewModel viewModel, BoardViewModel boardViewModel, Board initalBoard) {
        this.canvas = new Canvas(C_HEIGHT, C_WIDTH);
        this.canvas.setOnMouseClicked(this::handleMouseDraw);
        this.canvas.setOnMouseDragged(this::handleMouseDraw);
        this.canvas.setOnMouseMoved(this::handleMouseMoved);
        this.initalBoard = initalBoard;


        this.appViewModel = viewModel;
        this.appViewModel.listenToAppState(this::onApplicationStateChanged);

        this.boardViewModel = boardViewModel;
        this.boardViewModel.listenToBoard(this::onBoardChanged);

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.infobar = new Infobar();
        this.infobar.setDrawMode(this.drawMode);
        this.infobar.setCursorPosFormat(0,0);

        Toolbar toolbar = new Toolbar(this, this.appViewModel, this.boardViewModel);
        this.getChildren().addAll(toolbar, this.canvas, spacer, this.infobar);

        this.affine = new Affine();
        this.affine.appendScale(C_HEIGHT/initalBoard.getHeigth(),C_WIDTH/initalBoard.getWidth());
    }

    private void handleMouseMoved(MouseEvent mouseEvent) {
        Point2D simCoordinates = getSimulationCoordinates(mouseEvent);
        int mouseSimX = (int)simCoordinates.getX();
        int mouseSimY = (int)simCoordinates.getY();
        this.infobar.setCursorPosFormat(mouseSimX, mouseSimY);
    }


    public void setDrawMode(CellState mode){
        this.drawMode = mode;
        this.infobar.setDrawMode(mode);
    }

    private void  onApplicationStateChanged(ApplicationState newState){
        if(newState == ApplicationState.EDITING){
            this.isDrawingEnabled = true;
            this.boardViewModel.setBoard(this.initalBoard);
        } else if(newState == ApplicationState.SIMULATING){
            this.isDrawingEnabled = false;
        } else {
            throw new IllegalArgumentException( "Unsupported Application state " + newState);
        }
    }


    private void onBoardChanged(Board board){
        draw(board);
    }

    private void handleMouseDraw(MouseEvent mouseEvent) {
        if(!isDrawingEnabled){
            return;
        }
        Point2D simCoordinates = getSimulationCoordinates(mouseEvent);
        int mouseSimX = (int)simCoordinates.getX();
        int mouseSimY = (int)simCoordinates.getY();
        this.initalBoard.setState(mouseSimX, mouseSimY, drawMode);
        boardViewModel.setBoard(this.initalBoard);
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
