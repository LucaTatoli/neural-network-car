package drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import logic.Car;
import window.Window;

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

    public Line(Point2D p1, Point2D p2) {
        this.x1 = p1.getX();
        this.x2 = p2.getX();
        this.y1 = p1.getY();
        this.y2 = p2.getY();
        this.c = Color.black;
    }

    public Line(Point2D p1, Point2D p2, Color c) {
        this.x1 = p1.getX();
        this.x2 = p2.getX();
        this.y1 = p1.getY();
        this.y2 = p2.getY();
        this.c = c;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(c);
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void update() {

    }

    public void translate(Point2D t) {
        x1 += t.getX();
        x2 += t.getX();
        y1 += t.getY();
        y2 += t.getY();
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public void rotate(float theta) {
        Point2D p = new Point2D(x2 - x1, y2 - y1).turn(theta);
        x2 = x1 + p.getX();
        y2 = y1 + p.getY();
    }

    public static void main(String... args) {
        int width = 600;
        int height = 600;

        Window window = new Window("linea", width, height);

        Car car = new Car(32, 16, new Point2D(width/2, height/2), new ArrayList<>());

        window.addEntity(car);

        window.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                car.takeAction(Car.CarActions.TURNLEFT);
                System.out.println(car.getVelocity());
                window.update();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }
}
