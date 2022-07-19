package logic;

import drawable.Drawable;
import drawable.Line;
import drawable.Point2D;
import drawable.Rectangle;

import java.awt.*;
import java.util.ArrayList;

public class Car implements Drawable {

    private Rectangle rectangle;
    private Point2D position;
    private Point2D velocity;
    private NeuralNetwork AI;
    private float inputs[];
    private ArrayList<Line> track;
    private ArrayList<Line> sensors;
    private int score = 0;
    private float steeringAmount = (float) Math.PI/8;

    public Car(int sizeX, int sizeY, Point2D position, ArrayList<Line> track) {
        rectangle = new Rectangle(position.getX(), position.getY(), sizeX, sizeY, Color.RED);
        this.position = position;
        rectangle.setPosition(position.getX(), position.getY());
        this.velocity = new Point2D(5, 0);
        AI = new NeuralNetwork(5, 4, 16);
        inputs = new float[5];
        this.track = track;
        sensors = new ArrayList<>();
        setSensors();
    }

    public Car(int sizeX, int sizeY, Point2D position, ArrayList<Line> track, NeuralNetwork AI) {
        rectangle = new Rectangle(position.getX(), position.getY(), sizeX, sizeY, Color.RED);
        this.position = position;
        rectangle.setPosition(position.getX(), position.getY());
        this.velocity = new Point2D(5, 0);
        inputs = new float[5];
        this.track = track;
        sensors = new ArrayList<>();
        setSensors();
        this.AI = AI;
    }

    public void setSensors() {
        sensors.add(new Line(rectangle.getCenter(), rectangle.getP2()));
        sensors.add(new Line(rectangle.getCenter(), rectangle.getP3()));
        sensors.add(new Line(rectangle.getCenter(), new Point2D(rectangle.getPx() + rectangle.getWidth()/2, rectangle.getPy())));
        sensors.add(new Line(rectangle.getCenter(), new Point2D(rectangle.getPx() + rectangle.getWidth()/2, rectangle.getPy() - rectangle.getHeight())));
        sensors.add(new Line(rectangle.getCenter(), new Point2D(rectangle.getPx() + rectangle.getWidth(), rectangle.getPy() - rectangle.getHeight()/2)));
    }

    public void rotateSensors(float theta) {
        sensors.forEach(s -> s.rotate(theta));
    }

    public void draw(Graphics2D g2d) {
        rectangle.draw(g2d);
        for(Line s : sensors)
            s.draw(g2d);
    }

    public void update() {
        evaluateInputs();
        AI.putInputs(inputs);
        boolean[] outputs = AI.getOutputsActive();
        if(outputs[0])
            takeAction(CarActions.ACCEL);
        if(outputs[1])
            takeAction(CarActions.BREAK);
        if(outputs[2])
            takeAction(CarActions.TURNLEFT);
        if(outputs[3])
            takeAction(CarActions.TURNRIGHT);
        position = position.add(velocity.getX(), velocity.getY());
        rectangle.setPosition(position.getX(), position.getY());
        for(Line s : sensors)
            s.translate(velocity);
        for(Line t : track)
            if(Utils.lineRectangleIntersection(rectangle, t)) {
                velocity = new Point2D(0, 0);
                rectangle.setC(Color.black);
            }

        if(velocity.getX() != 0 || velocity.getY() != 0)
            score += 1;

    }

    public void takeAction(CarActions action) {
        switch (action) {
            case ACCEL:
                velocity = velocity.mult(1.1f);
                break;
            case BREAK:
                velocity = velocity.mult(0.9f);
                break;
            case TURNLEFT:
                velocity = velocity.turn(steeringAmount);
                //rectangle.rotate(steeringAmount);
                //rotateSensors(steeringAmount);
                break;
            case TURNRIGHT:
                velocity = velocity.turn(-steeringAmount);
                //rectangle.rotate(-steeringAmount);
                //rotateSensors(-steeringAmount);
                break;
        }
    }

    public void evaluateInputs() {
        Point2D intersection;
        for(int i = 0; i < sensors.size(); i++) {
            for(Line t : track) {
                intersection = Utils.lineLineIntersection(sensors.get(i), t);
                if(intersection != null) {
                    int x = rectangle.getPx() - intersection.getX();
                    int y = rectangle.getPy() - intersection.getY();
                    inputs[i] = (float) (Math.atan(Math.sqrt(x*x + y*y)) + 1)/2;
                }
            }
        }
    }

    public int getScore() { return score; }

    public Point2D getVelocity() { return velocity; }

    public NeuralNetwork getAI() { return AI; }

    public enum CarActions {
        ACCEL,
        BREAK,
        TURNLEFT,
        TURNRIGHT
    }



}
