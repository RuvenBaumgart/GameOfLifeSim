package de.creode.model;

public class CursorPosition {
    private final int posX;
    private final int posY;

    public CursorPosition(int x, int y){
        this.posX = x;
        this.posY = y;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
