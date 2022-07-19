package drawable;

import logic.Vector;

import java.awt.*;

public class Rectangle implements Drawable{

    private int width, height;
    private Point2D p0;
    private Vector p1, p2, p3, p4;
    private Color c;


    public Rectangle(int px, int py, int width, int height) {
        setRectangle(px, py, width, height);
    }

    public Rectangle(int px, int py, int width, int height, float theta) {
        setRectangle(px, py, width, height);
        rotate(theta);
    }

    private void setRectangle(int px, int py, int width, int height) {
        p0 = new Point2D(px, py);
        p1 = new Vector(-width/2, -height/2);
        p2 = new Vector( width/2, -height/2);
        p3 = new Vector( width/2,  height/2);
        p4 = new Vector(-width/2,  height/2);
        c = Color.black;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);

        int x1, x2, x3, x4, y1, y2, y3, y4;

        x1 = Math.round(p0.getX() + p1.getX());
        x2 = Math.round(p0.getX() + p2.getX());
        x3 = Math.round(p0.getX() + p3.getX());
        x4 = Math.round(p0.getX() + p4.getX());

        y1 = Math.round(p0.getY() + p1.getY());
        y2 = Math.round(p0.getY() + p2.getY());
        y3 = Math.round(p0.getY() + p3.getY());
        y4 = Math.round(p0.getY() + p4.getY());

        int[] xPoints = {x1, x2, x3, x4};
        int[] yPoints = {y1, y2, y3, y4};
        g.fillPolygon(xPoints, yPoints, 4);

        g.setColor(c);
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x2, y2, x3, y3);
        g.drawLine(x3, y3, x4, y4);
        g.drawLine(x4, y4, x1, y1);
    }

    @Override
    public void update() {
    }

    public void setPosition(int x, int y) {
        p0.setX(x);
        p0.setY(y);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getC() {
        return c;
    }

    public void setColor(Color c) {
        this.c = c;
    }

    public Point2D getP1() {
        return p0.add(p1.getX(), p1.getY());
    }

    public Point2D getP2() {
        return p0.add(p2.getX(), p2.getY());
    }

    public Point2D getP3() {
        return p0.add(p3.getX(), p3.getY());
    }

    public Point2D getP4() {
        return p0.add(p4.getX(), p4.getY());
    }

    public Point2D getCenter() { return p0; }

    public void rotate(float theta) {
        p1.rotate(theta);
        p2.rotate(theta);
        p3.rotate(theta);
        p4.rotate(theta);
    }
}
