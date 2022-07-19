package drawable;

import java.awt.*;

public class Rectangle implements Drawable{

    private int width, height;
    private Point2D p0, p1, p2, p3, p4;
    private float theta = 0, alpha, diag;
    private Color c;


    public Rectangle(int px, int py, int width, int height) {
        p0 = new Point2D(px + width/2, py + height/2);
        p1 = new Point2D(px, py);
        p2 = p1.add(width, 0);
        p3 = p1.add(width, height);
        p4 = p1.add(0, height);
        this.width = width;
        this.height = height;
        c = Color.black;
        diag = (int)Math.round(Math.sqrt(width*width+height*height)/2);
        alpha = (float)Math.acos(width/2 / diag);
    }

    public Rectangle(int px, int py, int width, int height, Color c) {
        p0 = new Point2D(px + width/2, py + height/2);
        p1 = new Point2D(px, py);
        p2 = p1.add(width, 0);
        p3 = p1.add(width, height);
        p4 = p1.add(0, height);
        this.width = width;
        this.height = height;
        this.c = c;
        diag = (int)Math.round(Math.sqrt(width*width+height*height)/2);
        alpha = (float)Math.acos(width/2 / diag);
    }

    public Rectangle(int px, int py, int width, int height, float theta, Color c) {
        p0 = new Point2D(px + width/2, py + height/2);
        this.width = width;
        this.height = height;
        this.c = c;
        this.theta = theta;
        diag = (int)Math.round(Math.sqrt(width*width+height*height)/2);
        alpha = (float)Math.acos(width/2 / diag);
        calculatePoints();
    }

    public Rectangle(int px, int py, int width, int height, float theta) {
        p0 = new Point2D(px + width/2, py + height/2);
        this.width = width;
        this.height = height;
        c = Color.black;
        this.theta = theta;
        diag = (float)Math.sqrt(width*width+height*height)/2;
        alpha = (float)Math.acos(width/2 / diag);
        calculatePoints();
    }

    private void calculatePoints() {
        p1 = p0.add((int)Math.round(diag * Math.cos(Math.PI - alpha + theta)), (int)Math.round(diag * Math.sin(Math.PI - alpha + theta)));
        p2 = p0.add((int)Math.round(diag * Math.cos(alpha + theta)), (int)Math.round(diag * Math.sin(alpha + theta)));
        p3 = p0.add((int)Math.round(diag * Math.cos(-alpha + theta)), (int)Math.round(diag * Math.sin(-alpha + theta)));
        p4 = p0.add((int)Math.round(diag * Math.cos(Math.PI + alpha + theta)), (int)Math.round(diag * Math.sin(Math.PI + alpha + theta)));
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        int[] xPoints = {p1.getX(), p2.getX(), p3.getX(), p4.getX()};
        int[] yPoints = {p1.getY(), p2.getY(), p3.getY(), p4.getY()};
        g.fillPolygon(xPoints, yPoints, 4);

        g.setColor(c);
        g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        g.drawLine(p2.getX(), p2.getY(), p3.getX(), p3.getY());
        g.drawLine(p3.getX(), p3.getY(), p4.getX(), p4.getY());
        g.drawLine(p4.getX(), p4.getY(), p1.getX(), p1.getY());
    }

    @Override
    public void update() {
        theta += 0.01f;
        calculatePoints();
    }

    public void setPosition(int x, int y) {
        p0.setX(x);
        p0.setY(y);
        calculatePoints();
    }

    public int getPx() {
        return p1.getX();
    }

    public int getPy() {
        return p1.getY();
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

    public void setC(Color c) {
        this.c = c;
    }

    public Point2D getP1() {
        return p1;
    }

    public Point2D getP2() {
        return p2;
    }

    public Point2D getP3() {
        return p3;
    }

    public Point2D getP4() {
        return p4;
    }

    public Point2D getCenter() { return p0; }

    public void rotate(float theta) {
        this.theta += theta;
    }
}
