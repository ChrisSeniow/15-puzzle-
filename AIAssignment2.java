package ai.assignment2;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.io.*;

public class AIAssignment2 {

    public static void main(String[] args) throws IOException {
        BufferedReader myInput = new BufferedReader(new InputStreamReader(System.in));

        char goal[][] = {
            {'1', '2', '3', '4'},
            {'5', '6', '7', '8'},
            {'9', 'A', 'B', 'C'},
            {'D', 'E', 'F', ' ',}};

        char board[][] = {
            {'5', '1', '2', '4'},
            {'6', 'A', '3', '7'},
            {'9', ' ', 'C', '8'},
            {'D', 'E', 'B', 'F',}};
//        System.out.println("Please enter your 4x4 game in the following format");
//        System.out.println("\"123456789ABCDEF \" \n = \n ");
//        for (int i = 0; i < goal.length; i++) {
//            for (int j = 0; j < goal[i].length; j++) {
//                System.out.print(goal[i][j] + " ");
//            }
//            System.out.println("");
//        }
//        System.out.println("\n");
//
//        String input = myInput.readLine();
//
//        char board[][] = new char[4][4];
//
//        int a = 0;
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[i].length; j++) {
//                board[i][j] = input.charAt(a);
//                a++;
//            }
//        }

        GameBoard newBoard = new GameBoard(board);

        while (true) {

            System.out.println("Which Search method would you like to use?");
            System.out.println("1 - Breadth-first search");
            System.out.println("2 - Greedy best-first search for H1");
            System.out.println("3 - Greedy best-first search for H2");
            System.out.println("4 - A* for H1");
            System.out.println("5 - A* for H2");
            System.out.println("6 - exit");

            int input2 = Integer.parseInt(myInput.readLine());

            switch (input2) {
                case 1:
                    BFS(newBoard, 0);
                case 2:
                    GBFSH1(newBoard, 0);
                case 3:
                    GBFSH2(newBoard, 0);
                case 4:
                    AStarH1(newBoard, 0);
                case 5:
                    AStarH2(newBoard, 0);
                case 6:
                    System.exit(0);
            }
        }

//        
//        char board[][] = {
//            {'5', '1', '2', '4'},
//            {'6', 'A', '3', '7'},
//            {'9', ' ', 'C', '8'},
//            {'D', 'E', 'B', 'F',}};
        //GameBoard newBoard = new GameBoard(board);
        //BFS(newBoard, 0);
        //AStarH2(newBoard, 0);
    }

    public static void printPath(GameBoard origional, String path, int depth, int nodesExpanded, int fringe, int statesAccessed) {
        GameBoard start = new GameBoard(origional);

        System.out.println(start.toString());

        System.out.println(path);

        System.out.println("Depth = " + depth);
        System.out.println("Number of Nodes Expanded = " + nodesExpanded);
        System.out.println("Maximum size of the fringe = " + fringe);
        System.out.println("Number of states accessed =  " + statesAccessed);
    }

    public static void BFS(GameBoard board, int lastMove) {
        int nodesExpanded = 0;
        int statesAccessed = 0;
        int fringe = 0;

        Queue<GameBoard> queue = new LinkedList<>();
        queue.add(board);

        while (!queue.isEmpty()) {

            fringe = Math.max(fringe, queue.size());

            GameBoard focusNode = queue.poll();

            statesAccessed++;

            if (focusNode.checkGoal()) {
                //System.out.println("goal reached");
                printPath(board, focusNode.getPath(), focusNode.getDepth(), nodesExpanded, fringe, statesAccessed);
                return;
            } else {

                if (lastMove != 2) { //if last move was not down move up 
                    if (!focusNode.checkTopRow()) {//if its not at the top and can actually move up
                        //if it gets here it can move up so it will now create an 'up' child

                        GameBoard newBoard1 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard1.getBoard().length; i++) {
                            for (int j = 0; j < newBoard1.getBoard()[i].length; j++) {
                                if (newBoard1.getBoard()[i][j] == ' ') { //swap the ' ' with the one above it(i - 1)
                                    newBoard1.swap(i, j, i - 1, j);
                                    newBoard1.setPath(focusNode.getPath() + newBoard1.toString());
                                    newBoard1.setDepth(focusNode.getDepth() + 1);
                                    queue.add(newBoard1);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }

                    }

                }

                if (lastMove != 1) { //if last move was not up move down
                    if (!focusNode.checkBottomRow()) {//if its not at the bottom and can actually move up
                        //if it gets here it can move down so it will now create an 'down' child

                        GameBoard newBoard2 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard2.getBoard().length; i++) {
                            for (int j = 0; j < newBoard2.getBoard()[i].length; j++) {
                                if (newBoard2.getBoard()[i][j] == ' ') { //swap the ' ' with the one below it(i - 1)                                
                                    newBoard2.swap(i, j, i + 1, j);
                                    newBoard2.setPath(focusNode.getPath() + newBoard2.toString());
                                    newBoard2.setDepth(focusNode.getDepth() + 1);
                                    queue.add(newBoard2);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }
                    }
                }

                if (lastMove != 4) { //if last move was not right move left
                    if (!focusNode.checkLeftColumn()) {//if its not at the left and can actually move left
                        //if it gets here it can move left so it will now create an 'left' child

                        GameBoard newBoard3 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard3.getBoard().length; i++) {
                            for (int j = 0; j < newBoard3.getBoard()[i].length; j++) {
                                if (newBoard3.getBoard()[i][j] == ' ') { //swap the ' ' with the one to the left
                                    newBoard3.swap(i, j, i, j - 1);
                                    newBoard3.setPath(focusNode.getPath() + newBoard3.toString());
                                    newBoard3.setDepth(focusNode.getDepth() + 1);
                                    queue.add(newBoard3);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }
                    }
                }

                if (lastMove != 3) { //if last move was not left move right
                    if (!focusNode.checkRightColumn()) {//if its not at the left and can actually move left
                        //if it gets here it can move right so it will now create an 'right' child

                        GameBoard newBoard4 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard4.getBoard().length; i++) {
                            for (int j = 0; j < newBoard4.getBoard()[i].length; j++) {
                                if (newBoard4.getBoard()[i][j] == ' ') { //swap the ' ' with the one to the right
                                    newBoard4.swap(i, j, i, j + 1);
                                    newBoard4.setPath(focusNode.getPath() + newBoard4.toString());
                                    newBoard4.setDepth(focusNode.getDepth() + 1);
                                    queue.add(newBoard4);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }
                    }
                }

            }

        }
    }

    public static void GBFSH1(GameBoard board, int lastMove) {
        int nodesExpanded = 0;
        int statesAccessed = 0;
        int fringe = 0;

        LinkedList<GameBoard> list = new LinkedList<>();
        list.add(board);

        while (!list.isEmpty()) {

            fringe = Math.max(fringe, list.size());

            Collections.sort(list, (a, b) -> a.getH1() - b.getH1());//-------------------------------------------------------------------------------

            GameBoard focusNode = list.remove(0); //same as poll in bfs
            statesAccessed++;
            //System.out.println(focusNode.toString());

            if (focusNode.checkGoal()) {
                System.out.println("goal reached");
                printPath(board, focusNode.getPath(), focusNode.getDepth(), nodesExpanded, fringe, statesAccessed);
                return;
            } else {

                if (lastMove != 2) { //if last move was not down move up 
                    if (!focusNode.checkTopRow()) {//if its not at the top and can actually move up
                        //if it gets here it can move up so it will now create an 'up' child

                        GameBoard newBoard1 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard1.getBoard().length; i++) {
                            for (int j = 0; j < newBoard1.getBoard()[i].length; j++) {
                                if (newBoard1.getBoard()[i][j] == ' ') { //swap the ' ' with the one above it(i - 1)
                                    newBoard1.swap(i, j, i - 1, j);
                                    newBoard1.setPath(focusNode.getPath() + newBoard1.toString());
                                    newBoard1.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard1);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }

                    }

                }

                if (lastMove != 1) { //if last move was not up move down
                    if (!focusNode.checkBottomRow()) {//if its not at the bottom and can actually move up
                        //if it gets here it can move down so it will now create an 'down' child

                        GameBoard newBoard2 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard2.getBoard().length; i++) {
                            for (int j = 0; j < newBoard2.getBoard()[i].length; j++) {
                                if (newBoard2.getBoard()[i][j] == ' ') { //swap the ' ' with the one below it(i - 1)                                
                                    newBoard2.swap(i, j, i + 1, j);
                                    newBoard2.setPath(focusNode.getPath() + newBoard2.toString());
                                    newBoard2.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard2);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }
                    }
                }

                if (lastMove != 4) { //if last move was not right move left
                    if (!focusNode.checkLeftColumn()) {//if its not at the left and can actually move left
                        //if it gets here it can move left so it will now create an 'left' child

                        GameBoard newBoard3 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard3.getBoard().length; i++) {
                            for (int j = 0; j < newBoard3.getBoard()[i].length; j++) {
                                if (newBoard3.getBoard()[i][j] == ' ') { //swap the ' ' with the one to the left
                                    newBoard3.swap(i, j, i, j - 1);
                                    newBoard3.setPath(focusNode.getPath() + newBoard3.toString());
                                    newBoard3.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard3);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }
                    }
                }

                if (lastMove != 3) { //if last move was not left move right
                    if (!focusNode.checkRightColumn()) {//if its not at the left and can actually move left
                        //if it gets here it can move right so it will now create an 'right' child

                        GameBoard newBoard4 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard4.getBoard().length; i++) {
                            for (int j = 0; j < newBoard4.getBoard()[i].length; j++) {
                                if (newBoard4.getBoard()[i][j] == ' ') { //swap the ' ' with the one to the right
                                    newBoard4.swap(i, j, i, j + 1);
                                    newBoard4.setPath(focusNode.getPath() + newBoard4.toString());
                                    newBoard4.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard4);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }
                    }
                }

            }

        }
    }

    public static void GBFSH2(GameBoard board, int lastMove) {
        int nodesExpanded = 0;
        int statesAccessed = 0;
        int fringe = 0;

        LinkedList<GameBoard> list = new LinkedList<>();
        list.add(board);

        while (!list.isEmpty()) {

            fringe = Math.max(fringe, list.size());

            Collections.sort(list, (a, b) -> a.getH2() - b.getH2());//-------------------------------------------------------------------------------

            GameBoard focusNode = list.remove(0); //same as poll in bfs

            statesAccessed++;

            if (focusNode.checkGoal()) {
                System.out.println("goal reached");
                printPath(board, focusNode.getPath(), focusNode.getDepth(), nodesExpanded, fringe, statesAccessed);
                return;
            } else {

                if (lastMove != 2) { //if last move was not down move up 
                    if (!focusNode.checkTopRow()) {//if its not at the top and can actually move up
                        //if it gets here it can move up so it will now create an 'up' child

                        GameBoard newBoard1 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard1.getBoard().length; i++) {
                            for (int j = 0; j < newBoard1.getBoard()[i].length; j++) {
                                if (newBoard1.getBoard()[i][j] == ' ') { //swap the ' ' with the one above it(i - 1)
                                    newBoard1.swap(i, j, i - 1, j);
                                    newBoard1.setPath(focusNode.getPath() + newBoard1.toString());
                                    newBoard1.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard1);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }

                    }

                }

                if (lastMove != 1) { //if last move was not up move down
                    if (!focusNode.checkBottomRow()) {//if its not at the bottom and can actually move up
                        //if it gets here it can move down so it will now create an 'down' child

                        GameBoard newBoard2 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard2.getBoard().length; i++) {
                            for (int j = 0; j < newBoard2.getBoard()[i].length; j++) {
                                if (newBoard2.getBoard()[i][j] == ' ') { //swap the ' ' with the one below it(i - 1)                                
                                    newBoard2.swap(i, j, i + 1, j);
                                    newBoard2.setPath(focusNode.getPath() + newBoard2.toString());
                                    newBoard2.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard2);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }
                    }
                }

                if (lastMove != 4) { //if last move was not right move left
                    if (!focusNode.checkLeftColumn()) {//if its not at the left and can actually move left
                        //if it gets here it can move left so it will now create an 'left' child

                        GameBoard newBoard3 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard3.getBoard().length; i++) {
                            for (int j = 0; j < newBoard3.getBoard()[i].length; j++) {
                                if (newBoard3.getBoard()[i][j] == ' ') { //swap the ' ' with the one to the left
                                    newBoard3.swap(i, j, i, j - 1);
                                    newBoard3.setPath(focusNode.getPath() + newBoard3.toString());
                                    newBoard3.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard3);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }
                    }
                }

                if (lastMove != 3) { //if last move was not left move right
                    if (!focusNode.checkRightColumn()) {//if its not at the left and can actually move left
                        //if it gets here it can move right so it will now create an 'right' child

                        GameBoard newBoard4 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard4.getBoard().length; i++) {
                            for (int j = 0; j < newBoard4.getBoard()[i].length; j++) {
                                if (newBoard4.getBoard()[i][j] == ' ') { //swap the ' ' with the one to the right
                                    newBoard4.swap(i, j, i, j + 1);
                                    newBoard4.setPath(focusNode.getPath() + newBoard4.toString());
                                    newBoard4.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard4);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }
                    }
                }

            }

        }
    }

    public static void AStarH1(GameBoard board, int lastMove) {

        int nodesExpanded = 0;
        int statesAccessed = 0;
        int fringe = 0;

        LinkedList<GameBoard> list = new LinkedList<>();
        list.add(board);

        while (!list.isEmpty()) {

            fringe = Math.max(fringe, list.size());

            Collections.sort(list, (a, b) -> (a.getH1() + a.getDepth()) - (b.getH1() + b.getDepth()));//-------------------------------------------------------------------------------

            GameBoard focusNode = list.remove(0); //same as poll in bfs
            statesAccessed++;

            if (focusNode.checkGoal()) {
                System.out.println("goal reached");
                printPath(board, focusNode.getPath(), focusNode.getDepth(), nodesExpanded, fringe, statesAccessed);
                return;
            } else {

                if (lastMove != 2) { //if last move was not down move up 
                    if (!focusNode.checkTopRow()) {//if its not at the top and can actually move up
                        //if it gets here it can move up so it will now create an 'up' child

                        GameBoard newBoard1 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard1.getBoard().length; i++) {
                            for (int j = 0; j < newBoard1.getBoard()[i].length; j++) {
                                if (newBoard1.getBoard()[i][j] == ' ') { //swap the ' ' with the one above it(i - 1)
                                    newBoard1.swap(i, j, i - 1, j);
                                    newBoard1.setPath(focusNode.getPath() + newBoard1.toString());
                                    newBoard1.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard1);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }

                    }

                }

                if (lastMove != 1) { //if last move was not up move down
                    if (!focusNode.checkBottomRow()) {//if its not at the bottom and can actually move up
                        //if it gets here it can move down so it will now create an 'down' child

                        GameBoard newBoard2 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard2.getBoard().length; i++) {
                            for (int j = 0; j < newBoard2.getBoard()[i].length; j++) {
                                if (newBoard2.getBoard()[i][j] == ' ') { //swap the ' ' with the one below it(i - 1)                                
                                    newBoard2.swap(i, j, i + 1, j);
                                    newBoard2.setPath(focusNode.getPath() + newBoard2.toString());
                                    newBoard2.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard2);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }
                    }
                }

                if (lastMove != 4) { //if last move was not right move left
                    if (!focusNode.checkLeftColumn()) {//if its not at the left and can actually move left
                        //if it gets here it can move left so it will now create an 'left' child

                        GameBoard newBoard3 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard3.getBoard().length; i++) {
                            for (int j = 0; j < newBoard3.getBoard()[i].length; j++) {
                                if (newBoard3.getBoard()[i][j] == ' ') { //swap the ' ' with the one to the left
                                    newBoard3.swap(i, j, i, j - 1);
                                    newBoard3.setPath(focusNode.getPath() + newBoard3.toString());
                                    newBoard3.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard3);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }
                    }
                }

                if (lastMove != 3) { //if last move was not left move right
                    if (!focusNode.checkRightColumn()) {//if its not at the left and can actually move left
                        //if it gets here it can move right so it will now create an 'right' child

                        GameBoard newBoard4 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard4.getBoard().length; i++) {
                            for (int j = 0; j < newBoard4.getBoard()[i].length; j++) {
                                if (newBoard4.getBoard()[i][j] == ' ') { //swap the ' ' with the one to the right
                                    newBoard4.swap(i, j, i, j + 1);
                                    newBoard4.setPath(focusNode.getPath() + newBoard4.toString());
                                    newBoard4.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard4);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }
                    }
                }

            }

        }
    }

    public static void AStarH2(GameBoard board, int lastMove) {

        int nodesExpanded = 0;
        int statesAccessed = 0;
        int fringe = 0;

        LinkedList<GameBoard> list = new LinkedList<>();
        list.add(board);

        while (!list.isEmpty()) {

            fringe = Math.max(fringe, list.size());

            Collections.sort(list, (a, b) -> (a.getH2() + a.getDepth()) - (b.getH2() + b.getDepth()));//-------------------------------------------------------------------------------

            GameBoard focusNode = list.remove(0); //same as poll in bfs
            statesAccessed++;

            if (focusNode.checkGoal()) {
                System.out.println("goal reached");
                printPath(board, focusNode.getPath(), focusNode.getDepth(), nodesExpanded, fringe, statesAccessed);
                return;
            } else {

                if (lastMove != 2) { //if last move was not down move up 
                    if (!focusNode.checkTopRow()) {//if its not at the top and can actually move up
                        //if it gets here it can move up so it will now create an 'up' child

                        GameBoard newBoard1 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard1.getBoard().length; i++) {
                            for (int j = 0; j < newBoard1.getBoard()[i].length; j++) {
                                if (newBoard1.getBoard()[i][j] == ' ') { //swap the ' ' with the one above it(i - 1)
                                    newBoard1.swap(i, j, i - 1, j);
                                    newBoard1.setPath(focusNode.getPath() + newBoard1.toString());
                                    newBoard1.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard1);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }

                    }

                }

                if (lastMove != 1) { //if last move was not up move down
                    if (!focusNode.checkBottomRow()) {//if its not at the bottom and can actually move up
                        //if it gets here it can move down so it will now create an 'down' child

                        GameBoard newBoard2 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard2.getBoard().length; i++) {
                            for (int j = 0; j < newBoard2.getBoard()[i].length; j++) {
                                if (newBoard2.getBoard()[i][j] == ' ') { //swap the ' ' with the one below it(i - 1)                                
                                    newBoard2.swap(i, j, i + 1, j);
                                    newBoard2.setPath(focusNode.getPath() + newBoard2.toString());
                                    newBoard2.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard2);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }
                    }
                }

                if (lastMove != 4) { //if last move was not right move left
                    if (!focusNode.checkLeftColumn()) {//if its not at the left and can actually move left
                        //if it gets here it can move left so it will now create an 'left' child

                        GameBoard newBoard3 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard3.getBoard().length; i++) {
                            for (int j = 0; j < newBoard3.getBoard()[i].length; j++) {
                                if (newBoard3.getBoard()[i][j] == ' ') { //swap the ' ' with the one to the left
                                    newBoard3.swap(i, j, i, j - 1);
                                    newBoard3.setPath(focusNode.getPath() + newBoard3.toString());
                                    newBoard3.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard3);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }
                    }
                }

                if (lastMove != 3) { //if last move was not left move right
                    if (!focusNode.checkRightColumn()) {//if its not at the left and can actually move left
                        //if it gets here it can move right so it will now create an 'right' child

                        GameBoard newBoard4 = new GameBoard(focusNode);

                        outerloop:
                        for (int i = 0; i < newBoard4.getBoard().length; i++) {
                            for (int j = 0; j < newBoard4.getBoard()[i].length; j++) {
                                if (newBoard4.getBoard()[i][j] == ' ') { //swap the ' ' with the one to the right
                                    newBoard4.swap(i, j, i, j + 1);
                                    newBoard4.setPath(focusNode.getPath() + newBoard4.toString());
                                    newBoard4.setDepth(focusNode.getDepth() + 1);
                                    list.add(newBoard4);
                                    nodesExpanded++;
                                    break outerloop;
                                }
                            }
                        }
                    }
                }

            }

        }
    }

}