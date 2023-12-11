package Go;

public class Board {
    private char[][] plateau;
    private int taille;
    private static final String alphabet = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
    private char joueurActif;
    private int passesBlancs = 0;
    private int passesNoirs = 0;
  
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
    	System.out.println("WHITE (O) has captured 0 stones");
    	System.out.println("BLACK (X) has captured 0 stones");
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


    private boolean move(int row, int col) {
        if (row < 0 || row >= taille || col < 0 || col >= taille || plateau[row][col] != '.') {
            return false;
        }
        if (getActivePlayer()=='W')
        	plateau[row][col] = 'O';
        else
        	plateau[row][col] = 'X';
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
     
}