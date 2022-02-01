package de.creode;

public class Game {

    public static final int ALIVE = 1;
    public static final int DEAD = 0;
    private int height;
    private int width;
    private int[][] board;

    public Game(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = new int[width][height];
    }

    public static Game copy(Game game){
        Game copy = new Game(game.getHeight(), game.getWidth());
        for (int i = 0; i < game.getWidth() ; i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                copy.setState(i,j, game.getCondition(i,j));
            }

        }
        return copy;
    }



    public void printBoard(){
        System.out.println("----");
        for (int i = 0; i < width; i++) {
            String line = "|";
            for (int j = 0; j < height; j++) {
                if (this.board[i][j] == DEAD) {
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
        result += getCondition(x - 1, y - 1);
        result += getCondition(x , y - 1);
        result += getCondition(x + 1, y - 1);

        result += getCondition(x - 1, y );
        result += getCondition(x + 1, y );

        result += getCondition(x - 1, y + 1);
        result += getCondition(x , y + 1);
        result += getCondition(x + 1, y + 1);
        return result;
    }


    public int getCondition(int x, int y){
        if(x < 0 || x >= width){
            return 0;
        }

        if( y >= height || y < 0){
            return 0;
        }
        return this.board[x][y];
    }

    public void setAlive(int x, int y){
        this.setState(x,y,ALIVE);
    }

    public void setDead(int x , int y){
        this.setState(x,y,DEAD);
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
                if(getCondition(i,j) == 1){
                    if(neightbours < 2){
                        newBoard[i][j] = DEAD;
                    } else if(neightbours == 2 || neightbours == 3){
                        newBoard[i][j] = ALIVE;
                    } else if(neightbours > 3){
                        newBoard[i][j] = DEAD;
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
