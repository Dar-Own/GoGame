package Go;

import java.util.Scanner;

public class ConsolePlayer implements IPlayer {
    private char playerColor;
    private Scanner scanner;


    public ConsolePlayer(char playerColor) {
        this.playerColor = playerColor;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public boolean play(String move,Board board) {
        return board.play(move);
    }

    @Override
    public char getPlayerColor() {
        return playerColor;
    }
    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
