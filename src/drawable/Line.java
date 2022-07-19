package drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.ArrayList;

import logic.Car;
import logic.Vector;
import window.Window;

public class Line implements Drawable{

    private Point2D origin;
    private Vector direction;
    private Color c;

    public Line(Point2D origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Line(int x1, int y1, int x2, int y2) {
        this.origin = new Point2D(x1, y1);
        this.direction = new Vector(x2 - x1, y2 - y1);
    }


    @Override
    public void draw(Graphics2D g) {
        g.setColor(c);
        int x2, y2;
        x2 = Math.round(origin.getX() + direction.getX());
        y2 = Math.round(origin.getY() + direction.getY());
        g.drawLine(origin.getX(), origin.getY(), x2, y2);
    }

    @Override
    public void update() {

    }

    public void translate(Point2D t) {
        origin = origin.add(t);
    }

    public void rotate(float theta) {
        direction.rotate(theta);
    }

    public Point2D getOrigin() {
        return origin;
    }

    public Vector getDirection() {
        return direction;
    }

    public Point2D getEnd() {
        float x, y;
        x = origin.getX() + direction.getX();
        y = origin.getY() + direction.getY();
        return new Point2D(x, y);
    }

    public void setColor(Color c) {
        this.c = c;
    }
}
