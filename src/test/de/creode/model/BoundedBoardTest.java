package de.creode.model;

import de.creode.model.Board;
import de.creode.model.BoundedBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundedBoardTest {
    Board board;

    @BeforeEach
    void setUp() {
        board = new BoundedBoard(5,3);
    }

    @Test
    public void copy_createACopyOfEqualSize(){
        Board copy = board.copy();
        assertEquals(5, copy.getHeigth());
        assertEquals(3, copy.getWidth());
    }

    @Test
    public void copy_createsADeepCopy(){
        Board copy = board.copy();
        copy.setState(3,2, CellState.ALIVE);
        assertEquals(CellState.ALIVE, copy.getState(3,2));
        assertEquals(CellState.DEAD, board.getState(3,2));
    }

    @Test
    public void copy_contentOfBothAreSame(){
        board.setState(2,2,CellState.ALIVE);
        board.setState(3,2,CellState.ALIVE);
        board.setState(4,3,CellState.ALIVE);

        Board copy = board.copy();

        for (int i = 0; i < copy.getHeigth(); i++) {
            for (int j = 0; j < copy.getWidth(); j++) {
                assertEquals(board.getState(i, j), copy.getState(i, j));
            }
        }
    }

    @Test
    public void setState_dontThrowErrorWhenOutOfBounds(){
        board.setState(6,5, CellState.ALIVE);
        board.setState(-1, 2, CellState.ALIVE);
        board.setState(0, -1, CellState.ALIVE);
    }


    @Test
    public void getState_dontThrowErrorWhenOutOfBounds(){
        board.getState(6,5);
        board.getState(-1, 2);
        board.getState(0, -1);
    }
}