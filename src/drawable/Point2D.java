package drawable;

public class Point2D {

    private float x, y;
    private float theta = 0;

    public Point2D(float x, float y) {
        this.x = x;
        this.y = y;
        double mag = Math.sqrt(x*x + y*y);
        theta = (float)Math.acos(x/mag);
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

    public Point2D add(int x, int y) {
        return new Point2D(this.x + x, this.y + y);
    }

    public Point2D mult(float amount) {
        x *= amount;
        y *= amount;
        return new Point2D(x, y);
    }

    public Point2D turn(float amount) {
        double mag = Math.sqrt(x*x + y*y);
        x = (float)(mag * Math.cos(theta + amount));
        y = (float)(mag * Math.sin(theta + amount));
        theta += amount;
        System.out.println(theta);
        return new Point2D(x, y);
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
}
