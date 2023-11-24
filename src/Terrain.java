import java.util.ArrayList;

public class Terrain {
    private ArrayList<ArrayList<Character>> plateau;
    private int taille;
    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    
    public Terrain(int taille) {
        this.taille = taille;
        this.plateau = new ArrayList<>();
        
        for (int i = 0; i < taille; i++) {
            ArrayList<Character> ligne = new ArrayList<>();
            for (int j = 0; j < taille; j++) {
                ligne.add('.');
            }
            plateau.add(ligne);
        }
    }

    
    public void afficherPlateau() {
       
        System.out.print("    "); 
        for (int i = 0; i < taille; i++) {
            System.out.print(alphabet.charAt(i) + " ");
        }
        System.out.println("    ");

        
        for (int i = taille - 1; i >= 0; i--) {
            int indiceLateral = i + 1;
            System.out.print((indiceLateral < 10 ? " " : "") + indiceLateral + "  ");
            for (int j = 0; j < taille; j++) {
                System.out.print(plateau.get(i).get(j) + " ");
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
