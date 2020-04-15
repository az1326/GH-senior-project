package app;

public class Model {
    private Dir direction;
    public enum Dir {
        UP(38), DOWN(40), LEFT(37), RIGHT(39);
        private int keyCode;

        private Dir(int keyCode) {
            this.keyCode = keyCode;
        }

        public int keyCode() {return keyCode;}
    }

    public Model() {
        direction = Dir.UP;
        System.out.println("start");
    }

    public Dir getDirection() {
        return direction;
    }

    public void setDirection(int keyCode) {
        if (keyCode == Dir.UP.keyCode()) direction = Dir.UP;
        else if (keyCode == Dir.LEFT.keyCode()) direction = Dir.LEFT;
        else if (keyCode == Dir.RIGHT.keyCode()) direction = Dir.RIGHT;
        else if (keyCode == Dir.DOWN.keyCode()) direction = Dir.DOWN;
    }

}