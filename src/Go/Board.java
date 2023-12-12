package Go;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private char[][] plateau;
    private int taille;
    private static final String alphabet = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
    private char joueurActif;
    private int passesBlancs = 0;
    private int passesNoirs = 0;
    private int nbPointNoir;
    private int nbPointBlanc;
  
    public Board(int size) {
        this.taille = size;
        this.plateau = new char[taille][taille];
        this.joueurActif = 'W';

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                plateau[i][j] = '.';
            }
        }
    }

    public void showBoard() {
    	System.out.println();
    	System.out.println("WHITE (O) has captured "+ nbPointBlanc + "0 stones");
    	System.out.println("BLACK (X) has captured "+ nbPointNoir  + "stones");
    	System.out.println();
        System.out.print("   ");        
        for (int i = 0; i < taille; i++) {
            System.out.print(alphabet.charAt(i) + " ");
        }
        System.out.println("    ");
        for (int i = taille - 1; i >= 0; i--) {
            int indiceLateral = i + 1;
            System.out.print((indiceLateral < 10 ? " " : "") + indiceLateral + " ");

            for (int j = 0; j < taille; j++) {
                System.out.print(plateau[i][j] + " ");
            }
            if (taille <10)
            	System.out.print("" + indiceLateral);
            else
            	System.out.print((indiceLateral < 10 ? " " : "") + indiceLateral);
            	System.out.println();
        }
        System.out.print("   ");
        for (int i = 0; i < taille; i++) {
            System.out.print(alphabet.charAt(i) + " ");
        }
        System.out.println("    ");
    }
    
    
    public boolean play(String move) {
        String[] parts = move.split(" ");

        if (parts.length < 2 || parts.length > 3) {
            System.out.println("Illegal move");
            return false;
        }
        String color;
        String vertex;

        if (parts.length == 3 && parts[0].equalsIgnoreCase("move")) {
            color = parts[1].toLowerCase();
            vertex = parts[2];
        } else {
            color = parts[0].toLowerCase();
            vertex = parts[1];
        }

        //System.out.println("Color: " + color + ", Vertex: " + vertex);

        if (!(color.equals("white") || color.equals("w") || color.equals("black") || color.equals("b"))) {
            return false;
        }
        

        if (!isValidVertex(vertex)) {
            return false;
        }

        char player = (color.equals("white") || color.equals("w")) ? 'W' : 'B';

        if (!isCorrectPlayer(player)) {
            return false;
        }
        
        if (!vertex.equals("pass")) {
            int[] coordinates = getCoordinates(vertex);
            //System.out.println("Coordinates: (" + coordinates[0] + ", " + coordinates[1] + ")");

            if (!move(coordinates[0], coordinates[1])) {
                return false;
            }
            passesBlancs = 0;
            passesNoirs = 0;
        } else {
            if (player == 'W') {
                passesBlancs++;
            } else {
                passesNoirs++;
            }
            if ((player == 'W' && passesBlancs == 2) || (player == 'B' && passesNoirs == 2)) {
                endGame();
                return true;
            }
        }

        switchPlayer();
        return true;
    }

    

    private boolean isValidVertex(String vertex) {
        return vertex.matches("[a-zA-Z](1[0-9]|[1-9])|pass");
    }
    

    private int[] getCoordinates(String vertex) {
        int[] coordinates = new int[2];
        if (vertex.equals("pass")) {
            coordinates[0] = -1;  
            coordinates[1] = -1;
        } else {
            coordinates[0] = Integer.parseInt(vertex.substring(1)) - 1;
            
            coordinates[1] = alphabet.indexOf(Character.toUpperCase(vertex.charAt(0)));
        }
        return coordinates;
    }
    
    
    private void switchPlayer() {
        joueurActif = (joueurActif == 'W') ? 'B' : 'W';
    }

   
    public char getActivePlayer() {
        return joueurActif;
    }
    
    private boolean isCorrectPlayer(char player) {
        return player == joueurActif;
    }
    

    private void endGame() {
    	if (getActivePlayer()=='W')
    		System.out.println("Game over. Black wins!");
    	else
    		System.out.println("Game over. White wins!");
        System.exit(0);
    }
    
    
    public String getAlphabet() {
        return alphabet;
    }
    
    public void clearBoard() {
        for (int i = taille - 1; i >= 0; i--) {
            for (int j = 0; j < taille; j++) {
            	plateau[i][j]='.';
            }
        }
        passesBlancs = 0;
        passesNoirs = 0;
    }
    
    // Méthode move mise à jour pour inclure la vérification des captures
    private boolean move(int row, int col) {
        if (row < 0 || row >= taille || col < 0 || col >= taille || plateau[row][col] != '.') {
            return false;
        }
        char player = getActivePlayer() == 'W' ? 'O' : 'X';
        plateau[row][col] = player;
 
        // Vérifiez les captures potentielles
        checkForGroupCaptures(row, col, player);
        checkForCaptures(row, col, player);
        return true;
    }
    
    private void checkForCaptures(int row, int col, char player) {
        char opponent = (player == 'O') ? 'X' : 'O';
        // Vérifiez chaque direction autour du pion
        captureIfSurrounded(row + 1, col, opponent);
        captureIfSurrounded(row - 1, col, opponent);
        captureIfSurrounded(row, col + 1, opponent);
        captureIfSurrounded(row, col - 1, opponent);
    }
    
    private void captureIfSurrounded(int row, int col, char opponent) {
        if (row >= 0 && row < taille && col >= 0 && col < taille && plateau[row][col] == opponent) {
            if (isSurrounded(row, col)) {
                // Capture le pion
                plateau[row][col] = '.';
                
            }
        }
    }
    
    private boolean isSurrounded(int row, int col) {
        char pion = plateau[row][col];
        char opponent = (pion == 'O') ? 'X' : 'O';

        // Vérifiez si le pion est entouré sur les quatre côtés
        return isOpponentOrBoundary(row + 1, col, opponent) &&
               isOpponentOrBoundary(row - 1, col, opponent) &&
               isOpponentOrBoundary(row, col + 1, opponent) &&
               isOpponentOrBoundary(row, col - 1, opponent);
    }
    
    private boolean isOpponentOrBoundary(int row, int col, char opponent) {
        return row < 0 || row >= taille || col < 0 || col >= taille || plateau[row][col] == opponent;
    }

    // Méthodes ajoutées pour la capture de groupes
    private void checkForGroupCaptures(int row, int col, char player) {
        char opponent = (player == 'O') ? 'X' : 'O';
        boolean[][] visited = new boolean[taille][taille];
        List<int[]> group = findGroup(row, col, player, visited);

        if (group.size() > 0 && isGroupSurrounded(group, opponent)) {
            captureGroup(group);
        }
    }

    private List<int[]> findGroup(int row, int col, char player, boolean[][] visited) {
        List<int[]> group = new ArrayList<>();
        findGroupDFS(row, col, player, visited, group);
        return group;
    }

    private void findGroupDFS(int row, int col, char player, boolean[][] visited, List<int[]> group) {
        if (row < 0 || row >= taille || col < 0 || col >= taille || visited[row][col] || plateau[row][col] != player) {
            return;
        }

        visited[row][col] = true;
        group.add(new int[]{row, col});

        findGroupDFS(row + 1, col, player, visited, group);
        findGroupDFS(row - 1, col, player, visited, group);
        findGroupDFS(row, col + 1, player, visited, group);
        findGroupDFS(row, col - 1, player, visited, group);
    }

    private boolean isGroupSurrounded(List<int[]> group, char opponent) {
        for (int[] coords : group) {
            int row = coords[0];
            int col = coords[1];

            if (hasLiberty(row + 1, col) || hasLiberty(row - 1, col) || 
                hasLiberty(row, col + 1) || hasLiberty(row, col - 1)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasLiberty(int row, int col) {
        return row >= 0 && row < taille && col >= 0 && col < taille && plateau[row][col] == '.';
    }

    private void captureGroup(List<int[]> group) {
        for (int[] coords : group) {
            plateau[coords[0]][coords[1]] = '.';
        }
    }
}