package de.creode;

public class Game {

    private int height;
    private int width;
    int[][] board;

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

    public void setAlive(int x, int y){
        this.board[x][y] = 1;
    }

    public void setDead(int x , int y){
        this.board[x][y] = 0;
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

    public static void main(String[] args) {
        Game game = new Game(8,5);

        game.setAlive(2,3);
        game.setAlive(3,3);
        game.setAlive(4,3);

        game.printBoard();

        game.step();
        game.printBoard();
        game.step();
        game.printBoard();
    }
}
