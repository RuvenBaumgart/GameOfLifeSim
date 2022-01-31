package de.creode;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class MainView extends VBox {

    private static final int C_HEIGHT = 400;
    private static final int C_WIDTH = 400;
    private static final int B_HEIGHT = 10;
    private static final int B_WIDTH = 10;
    private Button stepButton;
    private Canvas canvas;
    private Game game;
    private Affine affine;


    public MainView() {
        this.stepButton = new Button("Step");
        stepButton.setOnAction(actionEvent -> {
            game.step();
            this.draw();
        });
        this.canvas = new Canvas(C_HEIGHT, C_WIDTH);
        this.getChildren().addAll(stepButton, canvas);
        this.game = new Game(B_HEIGHT, B_WIDTH);
        this.affine = new Affine();
        this.affine.appendScale(C_HEIGHT/B_HEIGHT,C_WIDTH/B_WIDTH);
        game.setAlive(2,3);
        game.setAlive(3,3);
        game.setAlive(4,3);

        game.setAlive(5,5);
        game.setAlive(5,6);
        game.setAlive(6,7);
        game.setAlive(7,8);
        game.setAlive(8,8);
        game.setAlive(9,7);

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
