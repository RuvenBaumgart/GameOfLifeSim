package de.creode;

import de.creode.model.Board;
import de.creode.model.Rule;

public class Simulation {

    private Board simulationBoard;
    private Rule rule;

    public Simulation(Board simulationBoard, Rule rule) {
        this.simulationBoard = simulationBoard;
        this.rule = rule;
    }

    public void step(){
        Board nextBoard = this.simulationBoard.copy();
        for (int x = 0; x < nextBoard.getHeigth(); x++) {
            for (int y = 0; y < nextBoard.getWidth(); y++) {
                nextBoard.setState(x, y, rule.getNextState(x,y,this.simulationBoard));
            }
        }
        this.simulationBoard = nextBoard.copy();
    }

    public Board getBoard(){
        return this.simulationBoard;
    }
}
