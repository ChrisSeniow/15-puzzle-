package ai.assignment2;

import java.util.*;
import java.io.*;
//each node of the tree
public class GameBoard {

    private char board[][];
    private final char goal[][] = {
        {'1', '2', '3', '4'},
        {'5', '6', '7', '8'},
        {'9', 'A', 'B', 'C'},
        {'D', 'E', 'F', ' ',}};
    
    
    private int h1; //ammount of incorrect tiles
    private int h2; //amount of manhattandistance
    private GameBoard up;
    private GameBoard right;
    private GameBoard left;
    private GameBoard down;
    private int lastMove; //1=up 2=down 3=left 4=right
    private String path;
    private int depth;
    

    public GameBoard(char[][] board) {
        this.board = board;
        calculateH1();
        calculateH2();
        path = "";
        depth = 0;
    }
    
    public GameBoard(GameBoard newBoard) {
        board = new char[4][4];
        
        for (int i = 0; i < board.length; i++) {
            board[i] = Arrays.copyOf(newBoard.getBoard()[i], board[i].length);
        }
        
        calculateH1();
        calculateH2();
        path = "";
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public char[][] getBoard() {
        return board;
    }
    
    public void swap(int i1, int j1, int i2, int j2){
        char holder = board[i1][j1];
        board[i1][j1] = board[i2][j2];
        board[i2][j2] = holder;
    }

    
    public boolean checkTopRow(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] == ' '){
                    if(i == 0){ //if i == 0 so in top row
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean checkBottomRow(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] == ' '){
                    if(i == 3){ //if i = 3 so in bottom row
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean checkLeftColumn(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] == ' '){
                    if(j == 0){ //if j == 0 so in left most column
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean checkRightColumn(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] == ' '){
                    if(j == 3){ //if j = 3 so in left most column
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean checkGoal() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != goal[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    //child with move up  
    public GameBoard getUp() {
        return up;
    }
    
    //child with move down
    public GameBoard getDown() {
        return down;
    }

    //child with move left
    public GameBoard getLeft() {
        return left;
    }

    //child with move right
    public GameBoard getRight() {
        return right;
    }

    public void setUp(GameBoard up) {
        this.up = up;
    }
    
    public void setDown(GameBoard down) {
        this.down = down;
    }

    public void setLeft(GameBoard left) {
        this.left = left;
    }

    public void setRight(GameBoard right) {
        this.right = right;
    }

    public int getH1() {
        return h1;
    }

    public int getH2() {
        return h2;
    }

    public void calculateH1() {
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j] != goal[i][j]){
                    h1++;
                }
            }   
        }
    }
    
    public void calculateH2(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j] != ' '){
                    String tile = board[i][j]+ "";
                    int tileValue = Integer.parseInt(tile, 16);
                    int targetX = (tileValue - 1) / 4;
                    int targetY = (tileValue - 1) % 4;
                    int dx = i - targetX;
                    int dy = j - targetY;
                    h2 += Math.abs(dx) + Math.abs(dy);
                }
            }
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
    
    public void printPath(){
        
    }
    
    @Override
    public String toString() {
        String printBoard = "";
        
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                printBoard += board[i][j];
                printBoard += " ";
            }
            printBoard += "\n";
        }
        
        printBoard += "\n";
        return printBoard; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}