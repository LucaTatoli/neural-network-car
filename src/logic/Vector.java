package logic;

import drawable.Line;
import drawable.Point2D;
import window.Window;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Vector {

    private float x, y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector v) {
        x += v.getX();
        y += v.getY();
    }

    public void rotate(float theta) {
        float xt;
        xt = (float)(x * Math.cos(theta) - y * Math.sin(theta));
        y = (float)(x * Math.sin(theta) + y * Math.cos(theta));
        x = xt;

    }

    public void mult(float value) {
        x *= value;
        y *= value;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getMagnitude() {
        return (float)Math.sqrt(x*x + y*y);
    }

    public void setMagnitude(float mag) {
        float oldMag = getMagnitude();
        x *= mag/oldMag;
        y *= mag/oldMag;
    }

    public static Vector getVectorFromPoints(Point2D p1, Point2D p2) {
        return new Vector(p2.getX() - p1.getX(), p2.getY() - p1.getY());
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }


}
