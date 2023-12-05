package Go;
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
                if(size >=4 && size<=26) {
                	go = new Board(size);
                }
                else {
                	System.out.println("error 603");
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