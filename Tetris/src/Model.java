import java.awt.Point;

public class Model {
    private int tickCount;
    private boolean[][] existingPieces;
    private final String[] TETROMINOS = {"O", "Z", "S", "L", "J", "I", "T"};
    private String currentPiece;
    private Point currentLocation;
    private int currentOrientation;


    private int[][] activePiece;

    public Model() {
        tickCount = 0;

        currentPiece = null;
        currentLocation = null;
        currentOrientation = 0;

        existingPieces = new boolean[10][20];
        for (int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                existingPieces[i][j] = false;
            }
        }
    }

    public boolean[][] getBackgroundPieces() {
        return existingPieces;
    }

    public int[][] getActivePiece() {
        if (currentPiece == null || currentLocation == null || currentOrientation < 0 || currentOrientation > 3) {
            return null;
        }
        int x = currentLocation.x;
        int y = currentLocation.y;
        if (currentPiece.equals("O")) 
            return new int[][] {{x,x,x+1,x+1},{y,y+1,y+1,y}};
        if (currentPiece.equals("I")) {
            if (currentOrientation == 0 || currentOrientation == 2) 
                return new int[][] {{x,x,x,x},{y,y+1,y+2,y+3}};
            return new int[][] {{x,x+1,x+2,x+3},{y,y,y,y}};
        } 
        if (currentPiece.equals("S")) {
            if (currentOrientation ==0 || currentOrientation == 2)
                return new int[][] {{x,x+1,x+1,x+2},{y+1,y+1,y,y}};
            return new int[][] {{x,x,x+1,x+1},{y,y+1,y+1,y+2}};
        }
        if (currentPiece.equals("Z")) {
            if (currentOrientation == 0 || currentOrientation == 2)
                return new int[][] {{x,x+1,x+1,x+2},{y,y,y+1,y+2}};
            return new int[][] {{x,x,x+1,x+1},{y+2,y+1,y+1,y}};
        }
        if (currentPiece.equals("T")) {
            if (currentOrientation == 0)
                return new int[][] {{x,x+1,x+1,x+2},{y+1,y,y+1,y+1}};
            if (currentOrientation == 1)
                return new int[][] {{x,x,x,x+1},{y,y+1,y+2,y+1}};
            if (currentOrientation == 2)
                return new int[][] {{x,x+1,x+1,x+2},{y,y,y+1,y}};
            return new int[][] {{x,x+1,x+1,x+1},{y+1,y,y+1,y+2}};
        }
        if (currentPiece.equals("J")) {
            if (currentOrientation == 0)
                return new int[][] {{x,x,x+1,x+2},{y,y+1,y+1,y+1}};
            if (currentOrientation == 1)
                return new int[][] {{x,x,x,x+1},{y,y+1,y+2,y}};
            if (currentOrientation == 2)
                return new int[][] {{x,x,x+1,x+2},{y,y+1,y,y}};
            return new int[][] {{x,x+1,x+1,x+1},{y+2,y+2,y+2,y}};
        }  
        if (currentPiece.equals("L")) {
            if (currentOrientation == 0)
                return new int[][] {{x,x+1,x+2,x+2},{y+1,y+1,y+1,y}};
            if (currentOrientation == 1)
                return new int[][] {{x,x,x,x+1},{y,y+1,y+1,y+2}};
            if (currentOrientation == 2)
                return new int[][] {{x,x,x+1,x+2},{y,y+1,y,y}};
            return new int[][] {{x,x+1,x+1,x+1},{y,y,y+1,y+2}};
        }
        return null;
    }

    public void generate() {
        currentPiece = TETROMINOS[(int) Math.floor(Math.random() * 7)];
        currentLocation = new Point(0, 5);
    }

    public void tick() {
        tickCount++;
    }
}