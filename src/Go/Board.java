package Go;
public class Board {
    private char[][] plateau;
    private int taille;
    private static final String alphabet = "ABCDEFGHJKLMNOPQRSTUVWXYZ";

    public Board(int size) {
        this.taille = size;
        this.plateau = new char[taille][taille];

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                plateau[i][j] = '.';
            }
        }
    }

    public void showBoard() {
        System.out.print("    "); 
        for (int i = 0; i < taille; i++) {
            System.out.print(alphabet.charAt(i) + " ");
            
        }
        System.out.println("    ");
        for (int i = taille - 1; i >= 0; i--) {
            int indiceLateral = i + 1;
            System.out.print((indiceLateral < 10 ? " " : "") + indiceLateral + "  ");
            
            for (int j = 0; j < taille; j++) {
                System.out.print(plateau[i][j] + " ");
            }
            System.out.print(" " + (indiceLateral < 10 ? " " : "") + indiceLateral); 
            System.out.println();
        }
        System.out.print("    ");
        for (int i = 0; i < taille; i++) {
            System.out.print(alphabet.charAt(i) + " ");
        }
        System.out.println("    ");
    }

}
