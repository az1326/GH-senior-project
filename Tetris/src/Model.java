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
                return new int[][] {{x+2,x+2,x+2,x+2},{y,y+1,y+2,y+3}};
            return new int[][] {{x,x+1,x+2,x+3},{y+2,y+2,y+2,y+2}};
        } 
        if (currentPiece.equals("S")) {
            if (currentOrientation ==0 || currentOrientation == 2)
                return new int[][] {{x,x+1,x+1,x+2},{y+2,y+2,y+1,y+1}};
            return new int[][] {{x+1,x+1,x+2,x+2},{y,y+1,y+1,y+2}};
        }
        if (currentPiece.equals("Z")) {
            if (currentOrientation == 0 || currentOrientation == 2)
                return new int[][] {{x,x+1,x+1,x+2},{y+1,y+1,y+2,y+2}};
            return new int[][] {{x+1,x+1,x+2,x+2},{y+2,y+1,y+1,y}};
        }
        if (currentPiece.equals("T")) {
            if (currentOrientation == 0)
                return new int[][] {{x,x+1,x+1,x+2},{y+1,y,y+1,y+1}};
            if (currentOrientation == 1)
                return new int[][] {{x+1,x+1,x+1,x+2},{y,y+1,y+2,y+1}};
            if (currentOrientation == 2)
                return new int[][] {{x,x+1,x+1,x+2},{y+1,y+1,y+2,y+1}};
            return new int[][] {{x,x+1,x+1,x+1},{y+1,y,y+1,y+2}};
        }
        if (currentPiece.equals("J")) {
            if (currentOrientation == 0)
                return new int[][] {{x,x,x+1,x+2},{y,y+1,y+1,y+1}};
            if (currentOrientation == 1)
                return new int[][] {{x+1,x+1,x+1,x+2},{y,y+1,y+2,y}};
            if (currentOrientation == 2)
                return new int[][] {{x,x,x+1,x+2},{y+1,y+2,y+1,y+1}};
            return new int[][] {{x,x+1,x+1,x+1},{y+2,y+2,y+1,y}};
        }  
        if (currentPiece.equals("L")) {
            if (currentOrientation == 0)
                return new int[][] {{x,x+1,x+2,x+2},{y+1,y+1,y+1,y}};
            if (currentOrientation == 1)
                return new int[][] {{x+1,x+1,x+1,x+2},{y,y+1,y+2,y+2}};
            if (currentOrientation == 2)
                return new int[][] {{x,x,x+1,x+2},{y+1,y+2,y+1,y+1}};
            return new int[][] {{x,x+1,x+1,x+1},{y,y,y+1,y+2}};
        }
        return null;
    }

    public void rotate(boolean clockwise) {
        if (clockwise) {
            currentOrientation = (currentOrientation + 1) % 4;
        } else {
            currentOrientation = (currentOrientation + 3) % 4;
        }
    }

    public void shift(Direction shift) {
        if (currentLocation != null) {
            if (shift == Direction.UP) {
                currentLocation.y--;
            } else if (shift == Direction.LEFT) {
                currentLocation.x--;
            } else if (shift == Direction.RIGHT) {
                currentLocation.x++;
            } else {
                currentLocation.y++;
            }
        }
    }

    public void generate() {
        currentPiece = TETROMINOS[(int) Math.floor(Math.random() * 7)];
        currentLocation = new Point(3 + (int) Math.floor(Math.random() * 2), 0);
        currentOrientation = (int) Math.floor(Math.random() * 4);
    }

    public void tick() {
        tickCount++;
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }
}