package Go;

import java.util.Random;

public class ComputerPlayer implements IPlayer {
    private char playerColor;
    private Random random = new Random();


    public ComputerPlayer(char playerColor) {
        this.playerColor = playerColor;
    }
    
    public boolean play(String move, Board board) {
        if (move.equalsIgnoreCase("random")) {
            move = generateRandomMove(board);
        }
        return board.play(playerColor + " " + move);
    }
    
    private String generateRandomMove(Board board) {
        int row, col;
        String randomMove = "";

        while (true) {
            row = random.nextInt(board.getTaille());
            col = random.nextInt(board.getTaille()); 
            randomMove = board.getAlphabet().charAt(col) + String.valueOf(row + 1); 

            if (board.getPlateau()[row][col] == '.' && board.isValidVertex(randomMove) && board.isCorrectPlayer(playerColor)) {
                break;
            }
            
        }
        System.out.println(randomMove);
        return randomMove;
    }

    
    @Override
    public char getPlayerColor() {
        return playerColor;
    }


}
