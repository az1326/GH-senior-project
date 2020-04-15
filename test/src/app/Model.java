package app;

public class Model {
    private int direction;

    public Model() {
        direction = 0;
        System.out.println("start");
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int set) {
        direction = set;
    }

}