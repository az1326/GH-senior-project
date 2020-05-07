import java.awt.Point;

public class Model {
    private final String[] TETROMINOS = {"O", "Z", "S", "L", "J", "I", "T"};

    //Game Control
    private int tickCount;
    private int tickRate;

    //Base Board
    private boolean[][] existingPieces;

    //Active Piece
    private String currentPiece;
    private Point currentLocation;
    private int currentOrientation;
    private int[][] active;
    

    public Model() {
        tickCount = 0;
        tickRate = 2;

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

    /**
     * Returns a 2d array representing the location of the active piece
     */
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
                return new int[][] {{x+1,x+1,x+1,x+1},{y,y+1,y+2,y+3}};
            return new int[][] {{x,x+1,x+2,x+3},{y+1,y+1,y+1,y+1}};
        } 
        if (currentPiece.equals("S")) {
            if (currentOrientation == 0 || currentOrientation == 2)
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

    private void updateActiveArray() {
        active = getActivePiece();
    }

    /**
     * Rotates the active piece
     */
    public void rotate(boolean clockwise) {
        if (currentLocation != null) {
            if (clockwise) {
                currentOrientation = (currentOrientation + 1) % 4;
                int validation = validate(getActivePiece());
                currentOrientation = (currentOrientation + 3) % 4;

                if (validation == 0) {
                    currentOrientation = (currentOrientation + 1) % 4;
                } else if (validation == 1) {
                    currentOrientation = (currentOrientation + 1) % 4;
                    currentLocation.x++;
                } else if (validation == -1) {
                    currentOrientation = (currentOrientation + 1) % 4;
                    currentLocation.x--;
                }
            } else {
                currentOrientation = (currentOrientation + 3) % 4;
                int validation = validate(getActivePiece());
                currentOrientation = (currentOrientation + 1) % 4;

                if (validation == 0) {
                    currentOrientation = (currentOrientation + 3) % 4;
                } else if (validation == 1) {
                    currentOrientation = (currentOrientation + 3) % 4;
                    currentLocation.x++;
                } else if (validation == -1) {
                    currentOrientation = (currentOrientation + 3) % 4;
                    currentLocation.x--;
                }
            }
        }
    }

    /**
     * Shifts the active piece in a direction
     */
    public void shift(Direction shift) {
        if (currentLocation != null) {
            updateActiveArray();
            if (shift == Direction.UP) {
                for (int i = 0; i < active[0].length; i++) {
                    if (active[1][i] - 1 < 0 || existingPieces[active[0][i]][active[1][i] - 1]) {return;}
                }
                currentLocation.y--;
            } else if (shift == Direction.LEFT) {
                for (int i = 0; i < active[0].length; i++) {
                    if (active[0][i] - 1 < 0 || existingPieces[active[0][i] - 1][active[1][i]]) {return;}
                }
                currentLocation.x--;
            } else if (shift == Direction.RIGHT) {
                for (int i = 0; i < active[0].length; i++) {
                    if (active[0][i] + 1 > 9 || existingPieces[active[0][i] + 1][active[1][i]]) {return;}
                }
                currentLocation.x++;
            } else if (shift == Direction.DOWN) {
                drop();
            }
        }
    }

    /**
     * Validate rotation and adjust as needed
     */
    public int validate(int[][] candidate) {
        for (int i = 0; i < candidate[0].length; i++) {
            if (candidate[1][i] < 0 || candidate[1][i] > 19) {
                return 2;
            }
        }

        boolean condition = true;
        int shift = 0;
        for (int i = 0; i < candidate[0].length; i++) {
            if (candidate[0][i] + shift < 0 || candidate[0][i] + shift > 9 || existingPieces[candidate[0][i] + shift][candidate[1][i]]) {
                condition = false;
            }
        }
        if (condition) {return shift;}

        condition = true;
        shift = 1;
        for (int i = 0; i < candidate[0].length; i++) {
            if (candidate[0][i] + shift < 0 || candidate[0][i] + shift > 9 || existingPieces[candidate[0][i] + shift][candidate[1][i]]) {
                condition = false;
            }
        }
        if (condition) {return shift;}

        condition = true;
        shift = -1;
        for (int i = 0; i < candidate[0].length; i++) {
            if (candidate[0][i] +shift < 0 || candidate[0][i] + shift > 9 || existingPieces[candidate[0][i] + shift][candidate[1][i]]) {
                condition = false;
            }
        }
        if (condition) {return shift;}

        return 2;
    }

    /**
     * Spawns a new piece
     */
    public void generate() {
        currentPiece = TETROMINOS[(int) Math.floor(Math.random() * 7)];
        currentLocation = new Point(3 + (int) Math.floor(Math.random() * 2), 0);
        currentOrientation = (int) Math.floor(Math.random() * 4);
        tickCount = 0;
    }

    /**
     * Drop active piece
     */
    public void drop() {
        if (currentLocation != null) {
            for (int i = 0; i < active[0].length; i++) {
                int shift = active[1][i] + 1;
                if (shift > 19 || existingPieces[active[0][i]][shift]) {
                    lock();
                    return;
                }
            }
            currentLocation.y++;
        }
    }

    /**
     * Lock piece in and spawn new piece
     */
    public void lock() {
        for (int j = 0; j < active[0].length; j++) {
            existingPieces[active[0][j]][active[1][j]] = true;
        }
        generate();
    }

    /**
     * Handle each tick.
     * @return {@code true} if view needs to be updated
     */
    public boolean tick() {
        tickCount++;
        if (tickCount >= tickRate) {
            shift(Direction.DOWN);
            tickCount = 0;
            return true;
        }
        return false;
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }
}