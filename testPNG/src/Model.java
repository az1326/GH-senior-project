import java.awt.Point;

public class Model {
    private Point p;
    private boolean toggle;

    public Model() {
        p = new Point(0,0);
        toggle = false;
    }

    public void toggle() {
        toggle = !toggle;
    }

    public void updateMouseLocation(Point p) {
        this.p.x = p.x;
        this.p.y = p.y;
    }

    public boolean getToggle() {
        return toggle;
    }

    public Point getMouseLocation() {
        return p;
    }
}