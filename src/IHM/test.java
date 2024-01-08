package IHM;

import java.util.Arrays;
import java.util.Scanner;
import Go.Board;
import Go.IPlayer;
import Go.ConsolePlayer;
import Go.ComputerPlayer;

public class test {
    private static int currentInstructionNumber = 1;
    private static boolean isGameStarted = false;

    public static void main(String[] args) {
        Board go = null;
        IPlayer whitePlayer = new ConsolePlayer('W');
        IPlayer blackPlayer = new ConsolePlayer('B');
        Scanner entry = new Scanner(System.in);

        while (true) {
            String input = entry.nextLine();
            String[] commandParts = input.split(" ");
            int instructionNumber = -1;

            if (commandParts.length > 0 && commandParts[0].matches("\\d+")) {
                instructionNumber = Integer.parseInt(commandParts[0]);
                commandParts = Arrays.copyOfRange(commandParts, 1, commandParts.length);
            } else {
                instructionNumber = -1; 
            }

            if (instructionNumber != -1 && instructionNumber == currentInstructionNumber) {
                currentInstructionNumber++;
            }
            


            if (commandParts[0].equals("quit")) {
                printResult(instructionNumber, true, "");
                entry.close();
                System.exit(0);
            } else if (commandParts[0].equals("player") && commandParts.length == 3) {
                if (!isGameStarted) {
                    char color = commandParts[1].equalsIgnoreCase("white") ? 'W' : 'B';
                    String playerType = commandParts[2].toLowerCase();
                    if (color == 'W') {
                        whitePlayer = createPlayer(playerType, color);
                    } else {
                        blackPlayer = createPlayer(playerType, color);
                    }
                    printResult(instructionNumber, true, ""); // Confirme le changement
                } else {
                    printResult(instructionNumber, false, "Cannot change player type after game has started");
                }
            }
            else if (commandParts[0].equals("player") && isGameStarted) {
                printResult(instructionNumber, false, "Cannot change player type after game has started");
            } else if (commandParts[0].equals("boardsize") && commandParts.length == 2) {
                int newSize = Integer.valueOf(commandParts[1]);
                if (newSize < 2 || newSize > 25) {
                    printResult(instructionNumber, false, "Unacceptable size");
                } else {
                    go = new Board(newSize);
                    printResult(instructionNumber, true, "");
                }
            } else if (commandParts[0].equals("showboard") && go != null) {
                printResult(instructionNumber, true, "");
                go.showBoard();
            } else if (commandParts[0].equals("play") && go != null) {
                char color = commandParts[1].charAt(0);
                color = Character.toUpperCase(color); 

                char activePlayerColor = go.getActivePlayer();

                if (color != activePlayerColor) {
                    printResult(instructionNumber, false, "It's not your turn");
                } else {
                    boolean moveSuccess = false;


                    StringBuilder moveCommand = new StringBuilder();
                    for (int i = 1; i < commandParts.length; i++) {
                        moveCommand.append(commandParts[i]).append(" ");
                    }

                    if (activePlayerColor == 'W' && whitePlayer instanceof ComputerPlayer) {

                        String move = commandParts.length > 2 && commandParts[2].equalsIgnoreCase("random") ? "random" : moveCommand.toString().trim();
                        moveSuccess = whitePlayer.play(move, go);
                    } 
             
                    else if (activePlayerColor == 'W' && whitePlayer instanceof ConsolePlayer) {
                        moveSuccess = whitePlayer.play(moveCommand.toString().trim(), go);
                    }
                    
         
                    if (activePlayerColor == 'B' && blackPlayer instanceof ComputerPlayer) {
                        String move = commandParts.length > 2 && commandParts[2].equalsIgnoreCase("random") ? "random" : moveCommand.toString().trim();
                        moveSuccess = blackPlayer.play(move, go);
                    } 
                    else if (activePlayerColor == 'B' && blackPlayer instanceof ConsolePlayer) {
                        moveSuccess = blackPlayer.play(moveCommand.toString().trim(), go);
                    }
                    
                    if (!moveSuccess) {
                        printResult(instructionNumber, false, "Illegal move");
                    } else {
                    	isGameStarted = true; 
                        printResult(instructionNumber, true, "");
                        if ((activePlayerColor == 'W' && whitePlayer instanceof ComputerPlayer) || 
                                (activePlayerColor == 'B' && blackPlayer instanceof ComputerPlayer)) {
                                go.showBoard();
                            }
                    }
                    
                    if (go.isFinished()) {
                    	go.gagnant();
                    	 entry.close();
                    	 System.exit(0);
                    }
                }
            } else if (commandParts[0].equals("clearboard") && go != null) {
                go.clearBoard();
                printResult(instructionNumber, true, "");
            } else {
                printResult(instructionNumber, false, "Unknown command");
            }
        }
    }

    private static void printResult(int instructionNumber, boolean success, String message) {
        if (instructionNumber == -1) {
            System.out.println(message);
        } else {
            if (success) {
                System.out.println("=" + instructionNumber);
            } else {
                System.out.println("?" + instructionNumber + " " + message);
            }
        }
    }

    private static IPlayer createPlayer(String type, char color) {
        switch (type) {
            case "console":
                return new ConsolePlayer(color);
            case "random":
                return new ComputerPlayer(color);
            default:
                System.out.println("Unknown player type: " + type);
                return null;
        }
    }
}
