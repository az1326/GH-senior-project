import java.awt.Point;

public class Model {
    private final String[] TETROMINOS = {"O", "Z", "S", "L", "J", "I", "T"};

    //Game Control
    private GameState state;
    private int tickCount;
    private int tickRate;
    private int score;

    //Base Board
    private boolean[][] existingPieces;

    //Active Piece
    private String currentPiece;
    private Point currentLocation;
    private int currentOrientation;
    private int[][] active;

    //Next Piece
    private String nextPiece;
    

    /**
     * Constructor method. Instantiates and sets all fields to default.
     */
    public Model() {
        //Game State
        state = GameState.START_UP;
        score = 0;
        tickCount = 0;
        tickRate = 20;

        //Current Piece
        currentPiece = null;
        currentLocation = null;
        currentOrientation = 0;

        //Next Piece
        nextPiece = null;

        //Board
        existingPieces = new boolean[10][20];
        for (int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                existingPieces[i][j] = false;
            }
        }
    }

    /**
     * Returns the current score
     * @return the current score
     */
    public int getScore() {
        return score;
    }

    /** 
     * Returns the game state
     * @return the game state
     */
    public GameState getState() {
        return state;
    }

    /**
     * Returns a 2d array representing the status of each background cell
     * @return a 2d boolean array of status of each cell
     */
    public boolean[][] getBackgroundPieces() {
        return existingPieces;
    }

    /**
     * Returns a String representing the next piece
     * @return the next piece to spawn
     */
    public String getNextPiece() {
        return nextPiece;
    }

    /**
     * Returns a 2d array representing the location of the active piece
     * @return {@code null} if no current piece or invalid data,
     * a 2d int array of the location of the current piece otherwise
     */
    public int[][] getActivePiece() {
        if (currentPiece == null || currentLocation == null || currentOrientation < 0 
                || currentOrientation > 3) {
            return null; //return null if no current piece or invalid data
        }
        //Store current locations for ease of reference
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
                return new int[][] {{x,x+2,x+1,x+2},{y+1,y+2,y+1,y+1}};
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

    /**
     * Convenience method for ease of reference in validation methods.
     * Transfers fields representing current piece location to an array.
     */
    private void updateActiveArray() {
        active = getActivePiece();
    }

    /**
     * Rotates the active piece with validation
     * @param clockwise {@code true} if piece to spin clockwise, {code false} otherwise
     */
    public void rotate(boolean clockwise) {
        if (currentLocation != null) { //Only run if a current piece exists
            if (clockwise) {
                //Temporarily rotate piece pending validation
                currentOrientation = (currentOrientation + 1) % 4;
                int validation = validate(getActivePiece());
                currentOrientation = (currentOrientation + 3) % 4;

                //Implement rotation (with validation shifts) if validation successful
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
                //Temporarily rotate piece pending validation
                currentOrientation = (currentOrientation + 3) % 4;
                int validation = validate(getActivePiece());
                currentOrientation = (currentOrientation + 1) % 4;

                //Implement rotation (with validation shifts) if validation successful
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
     * @param shift the direction to move the piece
     */
    public void shift(Direction shift) {
        if (currentLocation != null) { //Only shift if a current piece exists
            updateActiveArray(); //Update for ease of reference
            /* For each direction:
             * 1) Iterate through each cell of the active piece
             * 2) Shift each cell in direction indicated
             * 3) Validate each cell is invalid
             * 4) If invalid, return
             * 5) If no cell is invalid, shift the piece
             * Separate method for dropping a piece as piece may need to lock
             */
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
     * Validates rotation attempt
     * @param candidate the result to validate
     * @return 2 if rotation is entirely invalid, otherwise returns the needed
     * number of cells to shift to be valid
     */
    private int validate(int[][] candidate) {
        //Do not attempt validation if candidate does not have valid y-position
        for (int i = 0; i < candidate[0].length; i++) {
            if (candidate[1][i] < 0 || candidate[1][i] > 19) {
                return 2;
            }
        }

        /*
         * For each validation attempt, in order of 0, 1, -1 (original, shift 1 right, shift 1 left)
         * 1) Iterate through each candidate cell
         * 2) Apply the shift
         * 3) If shifted cell is invalid, move on to next attempt
         * 4) If all cells are valid, return shift
         * If all attempts are invalid, return 2
         */

        boolean condition = true;
        int shift = 0;
        for (int i = 0; i < candidate[0].length; i++) {
            if (candidate[0][i] + shift < 0 || candidate[0][i] + shift > 9 
                    || existingPieces[candidate[0][i] + shift][candidate[1][i]]) {
                condition = false;
            }
        }
        if (condition) {return shift;}

        condition = true;
        shift = 1;
        for (int i = 0; i < candidate[0].length; i++) {
            if (candidate[0][i] + shift < 0 || candidate[0][i] + shift > 9 
                    || existingPieces[candidate[0][i] + shift][candidate[1][i]]) {
                condition = false;
            }
        }
        if (condition) {return shift;}

        condition = true;
        shift = -1;
        for (int i = 0; i < candidate[0].length; i++) {
            if (candidate[0][i] +shift < 0 || candidate[0][i] + shift > 9 
                    || existingPieces[candidate[0][i] + shift][candidate[1][i]]) {
                condition = false;
            }
        }
        if (condition) {return shift;}

        return 2;
    }

    /**
     * Spawns a new piece
     */
    private void generate() {
        int rng = (int) Math.floor(Math.random() * 7);
        //rng = 0; //[O,Z,S,L,J,I,T]

        //Generate a random piece if nextPiece does not yet exist
        if (nextPiece == null) {
            nextPiece = TETROMINOS[(int) Math.floor(Math.random() * 7)];
        }

        //Current piece is now next piece, randomly create next piece
        currentPiece = nextPiece;
        nextPiece = TETROMINOS[rng];

        //Each piece must spawn in a specific orientation and within a specific set of x-coordinates to be valid
        switch (currentPiece) {
            case "O":
                currentLocation = new Point((int) Math.floor(Math.random() * 8), 0);
                currentOrientation = 0;
                break;
            case "Z":
                currentLocation = new Point((int) Math.floor(Math.random() * 7), -1);
                currentOrientation = 0;
                break;
            case "S":
                currentLocation = new Point((int) Math.floor(Math.random() * 7), -1);
                currentOrientation = 0;
                break;
            case "L":
                currentLocation = new Point((int) Math.floor(Math.random() * 7), -1);
                currentOrientation = 2;
                break;
            case "J":
                currentLocation = new Point((int) Math.floor(Math.random() * 7), -1);
                currentOrientation = 2;
                break;
            case "I":
                currentLocation = new Point((int) Math.floor(Math.random() * 6), 0);
                currentOrientation = 1;
                break;
            case "T":
                currentLocation = new Point((int) Math.floor(Math.random() * 7), -1);
                currentOrientation = 2;
                break;
        }
        tickCount = 0; //Reset tick counter
    }

    /**
     * Drop active piece and lock if needed
     */
    private void drop() {
        if (currentLocation != null) { //Only run if current piece exists
            for (int i = 0; i < active[0].length; i++) {
                int shift = active[1][i] + 1;
                if (shift > 19 || existingPieces[active[0][i]][shift]) {
                    lock(); //Lock and return if piece would have fallen to an invalid location
                    return;
                }
            }
            currentLocation.y++; //Drop piece otherwise
        }
    }

    /**
     * Locks piece in, spawns new piece, and checks for row-clears/game over
     */
    private void lock() {
        //Each location the active piece occupied is transferred to the background cells
        for (int j = 0; j < active[0].length; j++) {
            existingPieces[active[0][j]][active[1][j]] = true;
        }
        generate();
        checkRows();
    }

    /**
     * Checks to see if any rows can be cleared or if there's a game over
     */
    private void checkRows() {
        /*
         * Check for row clearing by:
         * 1) Iterating through each row from bottom up
         * 2) If each cell in row is filled
         *    a) Shift every row above it one down
         *    b) Move down a row (so when code iterates up a row no row is skipped)
         *    c) Increase count of cleared rows  
         */
        int cleared = 0;
        for (int row = 19; row > 0; row--) {
            boolean full = true;
            for (int col = 0; col < existingPieces.length; col++) {
                if (!existingPieces[col][row]) {full = false;}
            }
            if (full) {
                for (int old = row; old > 0; old--) {
                    for (int col = 0; col < existingPieces.length; col++) {
                        existingPieces[col][old] = existingPieces[col][old-1];
                    }
                }
                cleared++;
                row++;
            }
        }

        //Adjust score
        if (cleared == 0)
            ;
        else if (cleared == 1)
            score += 100;
        else if (cleared == 2)
            score += 250;
        else if (cleared == 3)
            score += 450;
        else if (cleared == 4)
            score += 750;

        //Adjust tick rate
        tickRate = (int) Math.floor(30 - Math.log(score * score + Math.exp(10)));

        //Check for game over: game over if any cell is locked in top 2 rows
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < existingPieces.length; col++) {
                if (existingPieces[col][row]) {    
                    gameOver();
                    return;
                }
            }
        }
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

    /**
     * Resets the model. All fields set to default
     */
    public void reset() {
        state = GameState.START_UP;

        score = 0;
        tickCount = 0;
        tickRate = 20;

        currentPiece = null;
        currentLocation = null;
        currentOrientation = 0;

        nextPiece = null;

        existingPieces = new boolean[10][20];
        for (int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                existingPieces[i][j] = false;
            }
        }
    }

    /**
     * Starts the game.
     */
    public void start() {
        state = GameState.IN_PROGRESS;
        generate();
    }

    /**
     * Call when a game over state is reached.
     */
    private void gameOver() {
        //Do not set background cells to default so that loss condition can be displayed in view
        state = GameState.GAME_OVER;
        currentLocation = null;
        currentPiece = null;
        nextPiece = null;
    }

    /**
     * Represents the directions a piece can be shifted
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }

    /**
     * Represents the possible states of the game
     */
    public enum GameState {
        START_UP, IN_PROGRESS, GAME_OVER;
    }
}