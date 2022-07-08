package drawable;

import java.awt.*;

public class Line implements Drawable{

    private int x1, x2, y1, y2;
    private Color c;

    public Line(int x1, int y1, int x2, int y2, Color c) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.c = c;
    }

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.c = Color.black;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(c);
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void update() {

    }
}
