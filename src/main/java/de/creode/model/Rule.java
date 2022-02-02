package de.creode.model;

public interface Rule {
    CellState getNextState(int x, int y, Board board);
}
