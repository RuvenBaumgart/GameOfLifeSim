package de.creode.model;

import de.creode.model.board.Board;

public interface Rule {
    CellState getNextState(int x, int y, Board board);
}
