package de.creode.model.board;

import de.creode.model.CellState;

public interface Board {
    Board copy();
    void setState(int x, int y, CellState state);
    CellState getState(int x, int y);
    int getHeigth();
    int getWidth();
}
