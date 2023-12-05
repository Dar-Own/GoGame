package IHM;
import java.util.Scanner;
import Go.Board;

public class test {
    public static void main(String[] args) {
        Board go = null; 
        while (true) {
            Scanner entry = new Scanner(System.in);
            String entre2 = entry.nextLine();
            String[] entree = entre2.split(" ");
            

            if (entree[0].equals("quit")) {
                entry.close();
                System.exit(0);
            } else if (entree[0].equals("boardsize")) {
                if (entree.length == 2) {
                    go = new Board(Integer.valueOf(entree[1]));

                }
            } else if (entree[0].equals("showboard") && go != null) {
                go.showBoard();
            }
        }
    }
}




/*
package board;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Board go = null;
        Scanner entry = new Scanner(System.in);
        while (true) {
            String input = entry.nextLine();
            String[] inputParts = input.split(" ");
            
            if (inputParts.length == 2 && inputParts[1].equals("boardsize")) {
                int size = Integer.parseInt(inputParts[0]);
                if(size >=4 && size<=25) {
                	go = new Board(size);
                }
                else {
                	System.out.println("Taille incorrect");
                }
            } else if (inputParts[0].equals("showboard") && go != null) {
                go.showBoard();
            } else if (inputParts[0].equals("quit")) {
                entry.close();
                System.exit(0);
            }
        }
    }
}

*/