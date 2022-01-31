package de.creode;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    private static final int C_HEIGHT = 400;
    private static final int C_WIDTH = 400;
    private static final int B_HEIGHT = 10;
    private static final int B_WIDTH = 10;
    private Button stepButton;
    private Canvas canvas;
    private Game game;
    private Affine affine;
    private int drawMode = 1;


    public MainView() {
        this.stepButton = new Button("Step");
        stepButton.setOnAction(actionEvent -> {
            game.step();
            this.draw();
        });
        this.canvas = new Canvas(C_HEIGHT, C_WIDTH);
        this.canvas.setOnMouseClicked(this::handleMouseDraw);
        this.canvas.setOnMouseDragged(this::handleMouseDraw);
        this.setOnKeyPressed(this::handleKeyPressed);

        this.getChildren().addAll(stepButton, canvas);
        this.game = new Game(B_HEIGHT, B_WIDTH);
        this.affine = new Affine();
        this.affine.appendScale(C_HEIGHT/B_HEIGHT,C_WIDTH/B_WIDTH);
    }

    private void handleKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() ==  KeyCode.D){
            drawMode = 1;
            System.out.println("Drawing Mode");
        } else if(keyEvent.getCode() == KeyCode.E){
            drawMode = 0;
            System.out.println("Delete Mode");
        }
    }

    private void handleMouseDraw(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();
        Point2D mouseSimCoord = new Point2D(0,0);

        try {
            mouseSimCoord= this.affine.inverseTransform(mouseX, mouseY);
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }

        int mouseSimX = (int)mouseSimCoord.getX();
        int mouseSimY = (int)mouseSimCoord.getY();

        this.game.setState(mouseSimX, mouseSimY, drawMode);
        draw();
    }

    public void draw(){
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0,400,400);
        gc.setTransform(this.affine);

        gc.setFill(Color.BLACK);
        for (int i = 0; i < game.getWidth() ; i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                if(game.getBoard()[i][j] == 1){
                    gc.fillRect(i, j, 1, 1 );
                }
            }

        }

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(0.05d);
        for (int i = 0; i <= game.getWidth(); i++) {
            gc.strokeLine(i,0, i,10);
        }

        for (int i = 0; i <= game.getHeight(); i++) {
            gc.strokeLine(0, i, 10, i);
        }
    }
}
