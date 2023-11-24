import java.util.ArrayList;

public class Terrain {
    private ArrayList<ArrayList<Character>> plateau;
    private int taille;
    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Constructeur
    public Terrain(int taille) {
        this.taille = taille;
        this.plateau = new ArrayList<>();
        // Initialiser le plateau avec des intersections videsff
        for (int i = 0; i < taille; i++) {
            ArrayList<Character> ligne = new ArrayList<>();
            for (int j = 0; j < taille; j++) {
                ligne.add('.');
            }
            plateau.add(ligne);
        }
    }

    // Méthode pour afficher le plateau avec les indices
    public void afficherPlateau() {
        // Afficher les indices supérieurs
        System.out.print("   ");
        for (int i = 0; i < taille; i++) {
            System.out.print(alphabet.charAt(i) + " ");
        }
        System.out.println("   ");

        // Afficher le plateau avec les indices latéraux et les intersections
        for (int i = taille - 1; i >= 0; i--) {
            System.out.print((i + 1) + "  "); // Afficher les indices latéraux
            for (int j = 0; j < taille; j++) {
                System.out.print(plateau.get(i).get(j) + " ");
            }
            System.out.print(" " + (i + 1)); // Ajouter les indices droits
            System.out.println();
        }

        // Afficher les indices inférieurs
        System.out.print("   ");
        for (int i = 0; i < taille; i++) {
            System.out.print(alphabet.charAt(i) + " ");
        }
        System.out.println("   ");
    }

    // Vous pouvez ajouter d'autres méthodes selon les besoins du jeu
}
