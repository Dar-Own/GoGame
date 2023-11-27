package board;
import java.util.Scanner;

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