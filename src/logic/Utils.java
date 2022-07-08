package logic;

import drawable.Point2D;
import drawable.Rectangle;

public class Utils {

    public static Point2D lineLineIntersection(Point2D start1, Point2D end1, Point2D start2, Point2D end2) {

        Point2D intersection;

        float m1, m2, q1, q2, det, detX, detY;

        float div1 = end1.getX() - start1.getX() == 0 ? 0.001f : end1.getX() - start1.getX();
        float div2 = end2.getX() - start2.getX() == 0 ? 0.001f : end2.getX() - start2.getX();

        m1 = (end1.getY() - start1.getY())/div1;
        m2 = (end2.getY() - start2.getY())/div2;

        q1 = -1 * (start1.getY() - m1 * start1.getX());
        q2 = -1 * (start2.getY() - m2 * start2.getX());

        det = -m1 + m2;

        if(det == 0)
            return null;

        detX = - q1 + q2;
        detY = m1 * q2 - m2 * q1;

        intersection = new Point2D(Math.round(detX / det), Math.round(detY / det));

        return intersection;
    }


    public static boolean isPointBetween(Point2D intersection, Point2D start, Point2D end) {
        boolean betweenX, betweenY;

        if(start.getX() < end.getX())
            betweenX = intersection.getX() >= start.getX() && intersection.getX() <= end.getX();
        else
            betweenX = intersection.getX() >= end.getX() && intersection.getX() <= start.getX();

        if(start.getY() < end.getY())
            betweenY = intersection.getY() >= start.getY() && intersection.getY() <= end.getY();
        else
            betweenY = intersection.getY() >= end.getY() && intersection.getY() <= start.getY();

        return betweenX && betweenY;
    }

    public static boolean lineRectangleIntersection(Rectangle rect, Point2D p1, Point2D p2) {

        Point2D intersection;

        Point2D v1, v2, v3, v4;
        v1 = rect.getP1();
        v2 = rect.getP2();
        v3 = rect.getP3();
        v4 = rect.getP4();

        intersection = lineLineIntersection(v1, v2, p1, p2);
        if(intersection != null && isPointBetween(intersection, v1, v2) && isPointBetween(intersection, p1, p2))
            return true;

        intersection = lineLineIntersection(v2, v3, p1, p2);
        if(intersection != null && isPointBetween(intersection, v2, v3) && isPointBetween(intersection, p1, p2))
            return true;

        intersection = lineLineIntersection(v3, v4, p1, p2);
        if(intersection != null && isPointBetween(intersection, v3, v4) && isPointBetween(intersection, p1, p2))
            return true;

        intersection = lineLineIntersection(v4, v1, p1, p2);
        if(intersection != null && isPointBetween(intersection, v4, v1) && isPointBetween(intersection, p1, p2))
            return true;

        return false;
    }

}
