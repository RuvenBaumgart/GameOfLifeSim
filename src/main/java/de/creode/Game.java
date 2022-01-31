package de.creode;

public class Game {
    private int height;
    private int width;
    private int[][] board;

    public Game(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = new int[width][height];
    }

    public void printBoard(){
        System.out.println("----");
        for (int i = 0; i < width; i++) {
            String line = "|";
            for (int j = 0; j < height; j++) {
                if (this.board[i][j] == 0) {
                    line += "*";
                } else {
                    line += "0";
                }
            }
            line += "|";
            System.out.println(line);
        }
        System.out.println("----\n");
    }

    public int countAllAliveNeightbours(int x, int y){
        int result = 0;
        result += alive(x - 1, y - 1);
        result += alive(x , y - 1);
        result += alive(x + 1, y - 1);

        result += alive(x - 1, y );
        result += alive(x + 1, y );

        result += alive(x - 1, y + 1);
        result += alive(x , y + 1);
        result += alive(x + 1, y + 1);
        return result;
    }


    public int alive(int x, int y){
        if(x < 0 || x >= width){
            return 0;
        }

        if( y >= height || y < 0){
            return 0;
        }
        return this.board[x][y];
    }

    public void setAlive(int x, int y){
        this.setState(x,y,1);
    }

    public void setDead(int x , int y){
        this.setState(x,y,1);
    }

    public void setState(int x , int y, int state){
        if(x < 0 || x >= width){
            return;
        }

        if( y >= height || y < 0){
            return ;
        }

        this.board[x][y] = state;
    }


    public void step(){
        int[][] newBoard = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int neightbours = countAllAliveNeightbours(i,j);
                if(alive(i,j) == 1){
                    if(neightbours < 2){
                        newBoard[i][j] = 0;
                    } else if(neightbours == 2 || neightbours == 3){
                        newBoard[i][j] = 1;
                    } else if(neightbours > 3){
                        newBoard[i][j] = 0;
                    }
                } else {
                    if(neightbours == 3){
                        newBoard[i][j] = 1;
                    }
                }
            }
        }
        this.board = newBoard;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int[][] getBoard() {
        return board;
    }

}
