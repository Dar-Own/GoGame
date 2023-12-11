package IHM;

import java.util.Arrays;
import java.util.Scanner;
import Go.Board;

public class test {
    private static int currentInstructionNumber = 1;

    public static void main(String[] args) {
        Board go = null;
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
                
            } else if (commandParts[0].equals("boardsize")) {       
                if (commandParts.length == 2) {
                    int newSize = Integer.valueOf(commandParts[1]);
                    if (newSize < 2 || newSize > 25) {
                        printResult(instructionNumber, false, "Unacceptable size");
                    } else {
                        go = new Board(newSize);
                        printResult(instructionNumber, true, "");
                    }
                }   
            }
  
             else if (commandParts[0].equals("showboard") && go != null) {
            	printResult(instructionNumber, true, "");
                go.showBoard();
            } else if (commandParts[0].equals("play") && go != null) {
                StringBuilder moveCommand = new StringBuilder("move");
                for (int i = 1; i < commandParts.length; i++) {
                    moveCommand.append(" ").append(commandParts[i]);
                }
                char playerColor = (commandParts[1].equalsIgnoreCase("white") || commandParts[1].equalsIgnoreCase("w")) ? 'W' : 'B';

                if (playerColor != go.getActivePlayer()) {
                    printResult(instructionNumber, false, "It's not your turn");
                } else if (go.play(moveCommand.toString())) {
                    printResult(instructionNumber, true, "");
                } else {
                    printResult(instructionNumber, false, "Illegal move");
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
            if (success==false) {
                System.out.println(message);
            }
        }
        else {
            if (success) {
                System.out.println("=" + instructionNumber);
            } else {
                System.out.println("?" + instructionNumber + " " + message);
            }
        }
    }
}