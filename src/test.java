import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Terrain go = null; 

        while (true) {
            Scanner entry = new Scanner(System.in);
            String entre2 = entry.nextLine();
            String[] entree = entre2.split(" ");

            if (entree[0].equals("quit")) {
                entry.close();
                System.exit(0);
            } else if (entree[0].equals("boardsize")) {
                if (entree.length == 2) {
                    go = new Terrain(Integer.valueOf(entree[1]));
                    
                }
            } else if (entree[0].equals("showboard") && go != null) {
                go.afficherPlateau();
            } else if (entree[0].equals("clear_board")) {
                System.out.println("clear board");
            }
        }
    }
}
