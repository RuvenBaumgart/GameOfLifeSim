package de.creode.model;

import javafx.scene.control.Cell;

public class StandardRule implements Rule{

    @Override
    public CellState getNextState(int x, int y, Board board) {
        CellState state = CellState.DEAD; //Default
        int neightbours = countAllAliveNeightbours(x, y, board);
        if (board.getState(x,y) == CellState.ALIVE) {
            if (neightbours < 2) {
                state = CellState.DEAD;
            } else if (neightbours == 2 || neightbours == 3) {
                state = CellState.ALIVE;
            } else if (neightbours > 3) {
                state = CellState.DEAD;
            }
        } else {
            if (neightbours == 3) {
                state = CellState.ALIVE;
            }
        }
        return state;
    }

    private int countAllAliveNeightbours(int x, int y, Board board){
        int result = 0;
        result += cellCount(x - 1, y - 1, board);
        result += cellCount(x , y - 1, board);
        result += cellCount(x + 1, y - 1, board);

        result += cellCount(x - 1, y , board);
        result += cellCount(x + 1, y , board);

        result += cellCount(x - 1, y + 1, board);
        result += cellCount(x , y + 1, board);
        result += cellCount(x + 1, y + 1, board);
        return result;
    }

    private int cellCount (int x, int y, Board board){
        if(board.getState(x,y) == CellState.ALIVE){
            return 1;
        } else {
            return 0;
        }
    }


}
