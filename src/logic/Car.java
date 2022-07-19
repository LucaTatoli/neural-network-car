package logic;

import drawable.Drawable;
import drawable.Line;
import drawable.Point2D;
import drawable.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Car implements Drawable {

    private Rectangle rectangle;
    private Point2D position;
    private Vector velocity;
    private NeuralNetwork AI;
    private float inputs[];
    private ArrayList<Line> track;
    private ArrayList<Line> sensors;
    private ArrayList<Line> checkPoints;
    private float score = 0;
    private float steeringAmount = (float) Math.PI/8;
    private boolean dead = false;
    private float maxDistance;
    int currentCheckpoint = 0;

    public Car(int sizeX, int sizeY, Point2D position, ArrayList<Line> track, ArrayList<Line> checkPoints) {
        initCar(sizeX, sizeY, position, track, checkPoints);
        setSensors();

        AI = new NeuralNetwork(sensors.size(), 4, 16);
        inputs = new float[sensors.size()];
    }

    public Car(int sizeX, int sizeY, Point2D position, ArrayList<Line> track, ArrayList<Line> checkPoints, NeuralNetwork AI) {
        initCar(sizeX, sizeY, position, track, checkPoints);
        setSensors();
        this.AI = AI;
        inputs = new float[sensors.size()];
    }

    private void initCar(int sizeX, int sizeY, Point2D position, ArrayList<Line> track, ArrayList<Line> checkPoints) {
        rectangle = new Rectangle(position.getX(), position.getY(), sizeX, sizeY);
        rectangle.setColor(Color.red);
        this.position = position;
        rectangle.setPosition(position.getX(), position.getY());
        this.velocity = new Vector(5, 0);
        this.track = track;
        this.checkPoints = checkPoints;

        maxDistance = 0;
        for(Line line : track)
            if(line.getDirection().getMagnitude() > maxDistance) {
                maxDistance = line.getDirection().getMagnitude();
            }
    }

    public void setSensors() {
        sensors = new ArrayList<>();
        Vector v1, v2, v3, v4, v5, v6;

        int multiplier = 50;

        //MEDIUM VIEW SENSORS
        v1 = Vector.getVectorFromPoints(rectangle.getCenter(), rectangle.getP2());
        v1.mult(multiplier);
        sensors.add(new Line(rectangle.getCenter(), v1));

        v2 = Vector.getVectorFromPoints(rectangle.getCenter(), rectangle.getP3());
        v2.mult(multiplier);
        sensors.add(new Line(rectangle.getCenter(), v2));

        //NARROW VIEW SENSORS
        v3 = Vector.getVectorFromPoints(rectangle.getCenter(), rectangle.getP2());
        v3.mult(multiplier);
        v3.rotate(0.5f);
        sensors.add(new Line(rectangle.getCenter(), v3));

        v4 = Vector.getVectorFromPoints(rectangle.getCenter(), rectangle.getP3());
        v4.mult(multiplier);
        v4.rotate(-0.5f);
        sensors.add(new Line(rectangle.getCenter(), v4));

        //ULTRA WIDE VIEW SENSORS
        v5 = Vector.getVectorFromPoints(rectangle.getCenter(), rectangle.getP2());
        v5.mult(multiplier);
        v5.rotate(-1f);
        sensors.add(new Line(rectangle.getCenter(), v5));

        v6 = Vector.getVectorFromPoints(rectangle.getCenter(), rectangle.getP3());
        v6.mult(multiplier);
        v6.rotate(1f);
        sensors.add(new Line(rectangle.getCenter(), v6));
    }

    public void rotateSensors(float theta) {
        sensors.forEach(s -> s.rotate(theta));
    }

    public void draw(Graphics2D g2d) {
        rectangle.draw(g2d);
//        for(Line s : sensors)
//            s.draw(g2d);
    }

    public void update() {
        if(!dead) {
            evaluateInputs();
            AI.putInputs(inputs);
            boolean[] outputs = AI.getOutputsActive();
            if (outputs[0])
                takeAction(CarActions.ACCEL);
            if (outputs[1])
                takeAction(CarActions.BREAK);
            if (outputs[2])
                takeAction(CarActions.TURNLEFT);
            if (outputs[3])
                takeAction(CarActions.TURNRIGHT);
            position = position.add(velocity.getX(), velocity.getY());
            rectangle.setPosition(position.getX(), position.getY());

            for (Line t : track)
                if (Utils.lineRectangleIntersection(rectangle, t)) {
                    velocity = new Vector(0, 0);
                    rectangle.setColor(Color.black);
                    dead = true;
                    score *= 0.5f;
                }

            if(Math.round(velocity.getX()) == 0 && Math.round(velocity.getY()) == 0) {
                rectangle.setColor(Color.black);
                dead = true;
                score *= 0.2f;
            }

            score += 0.2f;

//            System.out.println("=========================");
//            System.out.println(outputs[0]);
//            System.out.println(outputs[1]);
//            System.out.println(outputs[2]);
//            System.out.println(outputs[3]);
//            System.out.println(velocity);
//            System.out.println("=========================");
        }

    }

    public void takeAction(CarActions action) {
        switch (action) {
            case ACCEL:
                velocity.mult(1.1f);
                if(velocity.getMagnitude() > 15)
                    velocity.setMagnitude(15);
                break;
            case BREAK:
                velocity.mult(0.9f);
                break;
            case TURNLEFT:
                velocity.rotate(-steeringAmount);
                rectangle.rotate(-steeringAmount);
                rotateSensors(-steeringAmount);
                break;
            case TURNRIGHT:
                velocity.rotate(steeringAmount);
                rectangle.rotate(steeringAmount);
                rotateSensors(steeringAmount);
                break;
        }
    }

    public void evaluateInputs() {
        Point2D intersection;
        float distance;
        float tempDistance;
        for(int i = 0; i < sensors.size(); i++) {
            distance = maxDistance;
            sensors.get(i).setColor(Color.RED);
            for(Line t : track) {
                intersection = Utils.lineLineIntersection(sensors.get(i), t);
                if(intersection != null) {
                    if(Utils.isPointBetween(intersection, sensors.get(i).getOrigin(), sensors.get(i).getEnd()) &&
                       Utils.isPointBetween(intersection, t.getOrigin(), t.getEnd())) {
                        sensors.get(i).setColor(Color.BLUE);
                        int x = rectangle.getCenter().getX() - intersection.getX();
                        int y = rectangle.getCenter().getY() - intersection.getY();
                        tempDistance = (float) Math.sqrt(x * x + y * y);
                        if (tempDistance < distance)
                            distance = tempDistance;
                    }
                }
            }
            inputs[i] = 1 - distance / maxDistance;


            //check checkpoints
            Line nextCheckpoint = checkPoints.get(currentCheckpoint);
            if(Utils.lineRectangleIntersection(rectangle, nextCheckpoint.getOrigin(), nextCheckpoint.getEnd())) {
                    score += 250;
                    currentCheckpoint = (currentCheckpoint + 1) % checkPoints.size();
            }

        }
    }

    public float getScore() { return score; }

    public Vector getVelocity() { return velocity; }

    public NeuralNetwork getAI() { return AI; }

    public boolean isDead() { return dead; }

    public enum CarActions {
        ACCEL,
        BREAK,
        TURNLEFT,
        TURNRIGHT
    }



}
