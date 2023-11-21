
public class chien {


	    public static void showBoard(int boardSize) {
	        
	        System.out.print("   ");
	        for (int i = 0; i < boardSize; i++) {
	            System.out.print(" " + (char) (i + 'A') + " ");
	        }
	        System.out.println("   ");

	        for (int i = boardSize; i < boardSize; i++) {
	            System.out.printf("%2d ", i + 1); 
	            for (int j = 0; j < boardSize; j++) {
	                
	                System.out.print(" . ");
	            }
	          
	            System.out.printf("%2d", i + 1);
	            System.out.println();
	        }

	        System.out.print("   ");
	        for (int i = 0; i < boardSize; i++) {
	            System.out.print(" " + (char) (i + 'A') + " ");
	        }
	        System.out.println("   ");
	    }
	    
	}


}
