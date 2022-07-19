package drawable;

import logic.Vector;

import java.awt.*;

public class Point2D implements Drawable {

    private float x, y;
    private float theta = 0;

    public Point2D(float x, float y) {
        this.x = x;
        this.y = y;
        double mag = Math.sqrt(x*x + y*y);
        theta = (float)Math.acos(x/mag);
    }

    public Point2D(float x, float y, float theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public int getX() {
        return Math.round(x);
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return Math.round(y);
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point2D add(float x, float y) {
        return new Point2D(this.x + x, this.y + y);
    }

    public Point2D add(Point2D p) {
        return new Point2D(this.x + p.getX(), this.y + p.getY());
    }

    public Point2D sub(float x, float y) {
        return new Point2D(this.x - x, this.getY() - y);
    }

    public Point2D sub(Point2D p) {
        return new Point2D(this.x - p.getX(), this.getY() - p.getY());
    }

    @Override
    public int hashCode() {
        return (Math.round(x * 31) + Math.round(y * 71));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != getClass())
            return false;
        Point2D p = (Point2D)obj;
        if(p == this)
            return true;
        return x == p.getX() && y == p.getY();
    }

    @Override
    public String toString() {
        return "("+x+", " +y+")";
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawArc(Math.round(x), Math.round(y), 5, 5, 0, 360);
    }

    @Override
    public void update() {

    }
}
