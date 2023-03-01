package Data;

public class Rect {
    private int x1, x2, y1, y2;
    private String tag, hoverLabel;
    private Frame gHover;

    public Rect(int x1, int x2, int y1, int y2, String tag) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.tag = tag;
        hoverLabel = "";
        gHover = null;
    }

    public Rect(int x1, int x2, int y1, int y2, String tag, String hoverLabel) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.tag = tag;
        this.hoverLabel = hoverLabel;
        gHover = null;
    }

    public Rect(int x1, int x2, int y1, int y2, String tag, Frame gHover) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.tag = tag;
        this.hoverLabel = "";
        this.gHover = gHover;
    }

    public Frame getGraphicalHover() {
        return gHover;
    }

    public String getTag() {
        return tag;
    }

    public void setX(int x) {
        x2 = x2 - x1 + x;
        x1 = x;
    }

    public void setY(int y) {
        y2 = y2 - y1 + y;
        y1 = y;
    }

    public int getX() {
        return x1;
    }

    public int getY() {
        return y1;
    }

    public boolean isCollision(int x, int y) {
        if (x >= x1 && x <= x2) {
            if (y >= y1 && y <= y2) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "x1: " + x1 + " x2: " + x2 + " y1: " + y1 + " y2: " + y2;
    }

    public boolean isClicked(Click c, int buttonComparator) {
        if (c.getButton() != buttonComparator) return false;
        return isCollision(c.getX(), c.getY());
    }
}
