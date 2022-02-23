package de.creode.model.board;

import de.creode.model.CellState;

public class BoundedBoard implements Board{
    private int height;
    private int width;
    CellState[][] board;

    public BoundedBoard(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = new CellState[height][width];
        for (int i = 0; i < this.getHeigth() ; i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                setState(i,j,CellState.DEAD);
            }
        }
    }

    @Override
    public Board copy() {
        BoundedBoard copy = new BoundedBoard(this.getHeigth(), this.getWidth());
        for (int i = 0; i < this.getHeigth() ; i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                copy.setState(i,j, this.getState(i,j));
            }
        }
        return copy;
    }

    @Override
    public void setState(int x, int y, CellState state) {
        if(x < 0 || x >= height){
            return;
        }

        if( y >= width || y < 0){
            return ;
        }

        this.board[x][y] = state;
    }

    @Override
    public CellState getState(int x, int y) {
        if(x < 0 || x >= height){
            return CellState.DEAD;
        }

        if( y >= width || y < 0){
            return CellState.DEAD;
        }
        return this.board[x][y];
    }

    @Override
    public int getHeigth() {
        return this.height;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

}
