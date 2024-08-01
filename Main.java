import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        char[][] board = new char[3][3];
        initializeBoard(board);
        char turn = 0;
        char end = 0;

        System.out.println("Welcome to Tic-Tac-Toe!");

        // Determine who goes first
        System.out.print("Would you like to go first? (y/n): ");
        char goFirst = scanner.next().charAt(0);
        if (goFirst == 'y') {
            turn = 'P';
            System.out.println("You are first!");
            playerTurn(board);
        } else if (goFirst == 'n') {
            turn = 'C';
            System.out.println("You are second!");
            compTurn(board);
        } else {
            System.out.println("Invalid input. Exiting...");
            System.exit(1);
        }
        printBoard(board);

        // Main game loop
        while (true) {
            if (turn == 'P') {
                compTurn(board);
                turn = 'C';
                System.out.println("Computer's Move:");
            } else {
                playerTurn(board);
                turn = 'P';
            }
            printBoard(board);
            end = checkWinner(board);
            if (end != 0) {
                break;
            }
        }

        // Display the result
        if (end == 'X') {
            System.out.println("Game Over! You win!");
        } else if (end == 'O') {
            System.out.println("Game Over! Computer wins!");
        } else {
            System.out.println("Game Over! It's a draw!");
        }

        // Ask to play again
        System.out.print("Would you like to play again? (y/n): ");
        goFirst = scanner.next().charAt(0);
        if (goFirst == 'y') {
            main(args);
        }
        System.out.println("Thanks for playing!");
        System.exit(0);
    }

    // Initialize the board with spaces
    public static void initializeBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // Check for a winner or a draw
    public static char checkWinner(char[][] board) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i];
            }
        }

        // Check diagonals
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }

        // Check for a draw
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return 0;
                }
            }
        }

        // If all positions are filled and no winner, it's a draw
        return 'D';
    }

    // Improved computer's turn to play with basic strategy
    public static void compTurn(char[][] board) {
        // Check if computer can win in the next move
        if (tryToWin(board, 'O')) {
            return;
        }

        // Block the player from winning
        if (tryToWin(board, 'X')) {
            return;
        }

        // Take the center if available
        if (board[1][1] == ' ') {
            board[1][1] = 'O';
            return;
        }

        // Take any empty corner
        int[][] corners = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        for (int[] corner : corners) {
            if (board[corner[0]][corner[1]] == ' ') {
                board[corner[0]][corner[1]] = 'O';
                return;
            }
        }

        // Take any empty side
        int[][] sides = {{0, 1}, {1, 0}, {1, 2}, {2, 1}};
        for (int[] side : sides) {
            if (board[side[0]][side[1]] == ' ') {
                board[side[0]][side[1]] = 'O';
                return;
            }
        }
    }

    // Helper method to try to win or block the opponent
    public static boolean tryToWin(char[][] board, char symbol) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (checkAndPlace(board, i, 0, i, 1, i, 2, symbol)) return true;
            if (checkAndPlace(board, 0, i, 1, i, 2, i, symbol)) return true;
        }
        // Check diagonals
        if (checkAndPlace(board, 0, 0, 1, 1, 2, 2, symbol)) return true;
        if (checkAndPlace(board, 0, 2, 1, 1, 2, 0, symbol)) return true;

        return false;
    }

    // Helper method to check and place a symbol to win or block
    public static boolean checkAndPlace(char[][] board, int x1, int y1, int x2, int y2, int x3, int y3, char symbol) {
        if (board[x1][y1] == symbol && board[x2][y2] == symbol && board[x3][y3] == ' ') {
            board[x3][y3] = 'O';
            return true;
        }
        if (board[x1][y1] == symbol && board[x3][y3] == symbol && board[x2][y2] == ' ') {
            board[x2][y2] = 'O';
            return true;
        }
        if (board[x2][y2] == symbol && board[x3][y3] == symbol && board[x1][y1] == ' ') {
            board[x1][y1] = 'O';
            return true;
        }
        return false;
    }

    // Player's turn to play
    public static void playerTurn(char[][] board) {
        int[] cdn = new int[2];

        getCoordinates(cdn);

        // Check if the position is already occupied
        if (board[cdn[0]][cdn[1]] == ' ') {
            board[cdn[0]][cdn[1]] = 'X';
        } else {
            System.out.println("Error: a token has already been placed in that position, try again");
            playerTurn(board);
        }
    }

    // Get coordinates from the player
    public static void getCoordinates(int[] cdn) {
        System.out.println("Please enter the row coordinate (x) to place your marker (0-2): ");
        cdn[0] = scanner.nextInt(); // Row (x-coordinate)

        System.out.println("Please enter the column coordinate (y) to place your marker (0-2): ");
        cdn[1] = scanner.nextInt(); // Column (y-coordinate)

        // Validate coordinates
        if (cdn[0] < 0 || cdn[0] > 2 || cdn[1] < 0 || cdn[1] > 2) {
            System.out.println("Invalid coordinate value, please try again");
            getCoordinates(cdn);
        }
    }

    // Print the current state of the board
    public static void printBoard(char[][] board) {
        System.out.println("  0 1 2");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("  -----");
            }
        }
    }
}
