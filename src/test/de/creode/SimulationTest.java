package de.creode;

import de.creode.logic.Simulation;
import de.creode.model.Board;
import de.creode.model.BoundedBoard;
import de.creode.model.CellState;
import de.creode.model.StandardRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void step_simulatesEntireBounds() {
        Board board = new BoundedBoard(5,3);
        board.setState(0,0, CellState.ALIVE);
        board.setState(4,0, CellState.ALIVE);
        board.setState(4,2, CellState.ALIVE);
        board.setState(0,2, CellState.ALIVE);

        Simulation simulation = new Simulation(board, new StandardRule());
        simulation.step();

        for (int i = 0; i < board.getHeigth(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
               assertEquals(CellState.DEAD, simulation.getBoard().getState(i,j));
            }
        }
    }
}