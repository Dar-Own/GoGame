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
	            if (i==1)
	            	System.out.print("     WHITE (O) has captured "+ nbPointBlanc + " stones");
	            
	            if (i==0)
	            	System.out.print("     BLACK (X) has captured "+ nbPointNoir  + " stones");
            	System.out.println();
        }
        System.out.print("   ");
        for (int i = 0; i < taille; i++) {
            System.out.print(alphabet.charAt(i) + " ");
        }
        System.out.println("    ");
    }
    
    public void gagnant() {
    	if (isFinished()) {
    		if (nbPointNoir>nbPointBlanc) {
    			System.out.println("Black player win");
    			System.out.println("Black : "+ nbPointNoir +" | White : "+ nbPointBlanc);
    		}
    		else {
    			System.out.println("White player win");
    			System.out.println("White : "+ nbPointBlanc + " | Black : "+ nbPointNoir );
    		}
    		
    	}
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
                passGame();
                return true;
            }
        }

        switchPlayer();
        return true;
    }

    

    public boolean isValidVertex(String vertex) {
        return vertex.matches("[a-zA-Z](1[0-9]|[1-9])|pass");
    }
    

    public int[] getCoordinates(String vertex) {
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
    
     
    public void switchPlayer() {
        joueurActif = (joueurActif == 'W') ? 'B' : 'W';
    } 

   
    public char getActivePlayer() {
        return joueurActif;
    }
    
    public boolean isCorrectPlayer(char player) {
        return player == joueurActif;
    }
    

    public void passGame() {
    	if (getActivePlayer()=='W')
    		System.out.println("Game over. Black wins!");
    	else
    		System.out.println("Game over. White wins!");
        System.exit(0);
    }
    
    public boolean isFinished() {
        for (int i = taille - 1; i >= 0; i--) {
            for (int j = 0; j < taille; j++) {
            	if (plateau[i][j]=='.')
            		return false;
            }
        }
        
        return true;
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
        nbPointNoir=0;
        nbPointBlanc=0;
    }
    
    public boolean move(int row, int col) {
        if (row < 0 || row >= taille || col < 0 || col >= taille || plateau[row][col] != '.') {
            return false;
        }
        char player = getActivePlayer() == 'W' ? 'O' : 'X';
        plateau[row][col] = player;

        checkForGroupCaptures(row, col, player);
        regardercapture(row, col, player);
        return true;
    }
    
    public void regardercapture(int row, int col, char player) {
        char chien = (player == 'O') ? 'X' : 'O';
        Remplace(row + 1, col, chien);
        Remplace(row - 1, col, chien);
        Remplace(row, col + 1, chien);
        Remplace(row, col - 1, chien);
    }
    
    public void Remplace(int row, int col, char opponent) {
        if (row >= 0 && row < taille && col >= 0 && col < taille && plateau[row][col] == opponent) {
            if (isSurrounded(row, col)) {
                // Capture le pion
                plateau[row][col] = '.';
                
            }
        }
    }
    
    public boolean isSurrounded(int row, int col) {
        char pion = plateau[row][col];
        char opponent = (pion == 'O') ? 'X' : 'O';

        return isOpponentOrBoundary(row + 1, col, opponent) &&
               isOpponentOrBoundary(row - 1, col, opponent) &&
               isOpponentOrBoundary(row, col + 1, opponent) &&
               isOpponentOrBoundary(row, col - 1, opponent);
    }
    
    public boolean isOpponentOrBoundary(int row, int col, char opponent) {
        return row < 0 || row >= taille || col < 0 || col >= taille || plateau[row][col] == opponent;
    }


    public void checkForGroupCaptures(int row, int col, char player) {
        char opponent = (player == 'O') ? 'X' : 'O';
        checkAndCaptureGroup(row + 1, col, opponent);
        checkAndCaptureGroup(row - 1, col, opponent);
        checkAndCaptureGroup(row, col + 1, opponent);
        checkAndCaptureGroup(row, col - 1, opponent);
    }
    
    public void checkAndCaptureGroup(int row, int col, char opponent) {
        if (row >= 0 && row < taille && col >= 0 && col < taille && plateau[row][col] == opponent) {
            boolean[][] visited = new boolean[taille][taille];
            List<int[]> group = findGroup(row, col, opponent, visited);
            if (isGroupSurrounded(group)) {
                captureGroup(group);
            }
        }
    }

    public List<int[]> findGroup(int row, int col, char player, boolean[][] visited) {
        List<int[]> group = new ArrayList<>();
        findGroupDFS(row, col, player, visited, group);
        return group;
    }

    public void findGroupDFS(int row, int col, char player, boolean[][] visited, List<int[]> group) {
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

    public boolean isGroupSurrounded(List<int[]> group) {
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

    public boolean hasLiberty(int row, int col) {
        return row >= 0 && row < taille && col >= 0 && col < taille && plateau[row][col] == '.';
    }

    public void captureGroup(List<int[]> group) {
        int capturedStones = group.size();
        for (int[] coords : group) {
            plateau[coords[0]][coords[1]] = '.';
        }
        
        if (joueurActif == 'W') {
        	nbPointBlanc += capturedStones;
        } else {
            nbPointNoir += capturedStones;
        } 
    }
    
    public int getTaille() {
    	return taille;
    }

	public char[][] getPlateau() {
		return plateau;
	}
}
