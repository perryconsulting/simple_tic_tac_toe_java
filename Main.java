package tictactoe;

import java.util.*;

public class Main {
    static char currentPlayer;

    public static void main(String[] args) {

        String currentGameState = "In Progress";
        currentPlayer = 'X';

        char[][] gameBoard = setGameBoard();
        drawGameBoard(gameBoard, currentGameState);
        do {
            makeAMove(gameBoard);
            currentGameState = validateGameState(gameBoard);
            drawGameBoard(gameBoard, currentGameState);

        } while (currentGameState.equals("In Progress"));

    }

    private static void makeAMove(char[][] board) {
        boolean validMove = false;
        while (!validMove) {
            int x;
            int y;
            Scanner in = new Scanner(System.in);
            try {
                x = in.nextInt() - 1;
                y = in.nextInt() - 1;
            } catch (Exception e) {
                System.out.println("You should enter numbers!");
                continue;
            }
            if ((x < 0 || x > 2) || (y < 0 || y > 2)) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            if (board[x][y] == 'X' || board[x][y] == 'O') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            board[x][y] = currentPlayer;
            validMove = true;
            if (currentPlayer == 'X') currentPlayer = 'O'; else currentPlayer = 'X';
        }
    }


    private static String validateGameState(char[][] board) {

        String gameState = "In Progress";

        boolean validTurnCount = validateTurnCount(board);
        boolean isBoardFull = checkIsBoardFull(board);
        HashMap<String, Boolean> playerWinStatus = checkIfPlayerWon(board);

        if (!validTurnCount) {
            gameState = "Impossible";
        } else if (playerWinStatus.get("Impossible")) {
            gameState = "Impossible";
        } else if (playerWinStatus.get("X")) {
            gameState = "X wins";
        } else if (playerWinStatus.get("O")) {
            gameState = "O wins";
        } else if (isBoardFull) {
            gameState = "Draw";
        }

        return gameState;

    }

    private static HashMap<String, Boolean> checkIfPlayerWon(char[][] board) {

        HashMap<String, Boolean> playerWinMap = new HashMap<>();
        boolean xIsWinner = false;
        boolean oIsWinner = false;
        boolean impossibleWinner = false;

        // Check for Horizontal Win
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                if (board[i][0] == 'X') {
                    xIsWinner = true;
                } else if (board[i][0] == 'O') {
                    oIsWinner = true;
                }
            }
        }

        // Check for Vertical Win
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                if (board[0][i] == 'X') {
                    xIsWinner = true;
                } else if (board[0][i] == 'O') {
                    oIsWinner = true;
                }
            }
        }

        // Check for Diagonal Win L -> R
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            if (board[0][0] == 'X') {
                xIsWinner = true;
            } else if (board[0][0] == 'O') {
                oIsWinner = true;
            }
        }

        // Check for Diagonal Win R -> L
        if (board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            if (board[0][2] == 'X') {
                xIsWinner = true;
            } else if (board[0][2] == 'O') {
                oIsWinner = true;
            }
        }

        if (xIsWinner && oIsWinner) {
            impossibleWinner = true;

        }
        playerWinMap.put("Impossible", impossibleWinner);
        playerWinMap.put("X", xIsWinner);
        playerWinMap.put("O", oIsWinner);

        return playerWinMap;
    }

    private static boolean checkIsBoardFull(char[][] board) {
        int boardCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'X' || board[i][j] == 'O') {
                    boardCount++;
                }
            }
        }
        return boardCount == 9;
    }

    private static boolean validateTurnCount(char[][] board) {
        int turnCountDifference = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'X') {
                    turnCountDifference++;
                } else if (board[i][j] == 'O') {
                    turnCountDifference--;
                }
            }
        }

        int absoluteDifference = Math.abs(turnCountDifference);
        return (absoluteDifference == 0) || absoluteDifference == 1;
    }

    private static char[][] setGameBoard() {
        String initialBoard = "         ";
        List<Character> charArrayList = stringToArrayList(initialBoard);
        return convertArrayListTo2DArray(charArrayList);
    }

    private static char[][] convertArrayListTo2DArray(List<Character> charArrayList) {
        char[][] board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = charArrayList.get(0);
                charArrayList.remove(0);
            }
        }
        return board;
    }

    private static List<Character> stringToArrayList(String stringInput) {
        char[] charArray = stringInput.toCharArray();
        List<Character> characterList = new ArrayList<>();
        for (char c : charArray) {
            characterList.add(c);
        }
        return characterList;
    }

    private static void drawGameBoard(char[][] board, String gameState) {
        System.out.println("---------");
        System.out.println("| " + board[0][0] + " " + board[0][1] + " " + board[0][2] + " |");
        System.out.println("| " + board[1][0] + " " + board[1][1] + " " + board[1][2] + " |");
        System.out.println("| " + board[2][0] + " " + board[2][1] + " " + board[2][2] + " |");
        System.out.println("---------");
        if (!Objects.equals(gameState, "In Progress")) {
            System.out.println(gameState);
        }
    }
}
