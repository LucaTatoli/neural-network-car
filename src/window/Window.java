package window;

import drawable.Drawable;
import drawable.Line;
import drawable.Point2D;
import logic.Car;
import logic.NeuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Window extends JPanel{

    private JFrame frame;
    private List<Drawable> entities = new ArrayList<>();

    public Window(String title, int width, int height) {
        frame = new JFrame();
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(frame.getSize());
        frame.setContentPane(this);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public Dimension getSize() {
        return frame.getSize();
    }

    public String getTitle() {
        return frame.getTitle();
    }

    public void clear() {
        //to do
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for(Drawable e : entities) {
            e.update();
            e.draw(g2d);
        }
    }

    public void addEntity(Drawable d) {
        entities.add(d);
    }

    public void addEntities(List<Drawable> d) { entities.addAll(d); }

    public void emptyEntity() { entities.clear(); }

    public void update() {
        repaint();
    }

    public static void main(String... args) {
        Window window = new Window("neural-network", 900, 600);
        ArrayList<Line> track = new ArrayList<>();
        //OUTER LINES
        int trackBorder = 10;
        track.add(new Line(trackBorder, trackBorder, window.getWidth() - trackBorder, trackBorder));
        track.add(new Line(window.getWidth() - trackBorder, trackBorder, window.getWidth() - trackBorder, window.getHeight() - trackBorder));
        track.add(new Line(trackBorder, trackBorder, trackBorder, window.getHeight() - trackBorder));
        track.add(new Line(trackBorder, window.getHeight() - trackBorder, window.getWidth() - trackBorder, window.getHeight() - trackBorder));
        //INNER LINES
        trackBorder += 70;
        track.add(new Line(trackBorder, trackBorder, window.getWidth() - trackBorder, trackBorder));
        track.add(new Line(window.getWidth() - trackBorder, trackBorder, window.getWidth() - trackBorder, window.getHeight() - trackBorder));
        track.add(new Line(trackBorder, trackBorder, trackBorder, window.getHeight() - trackBorder));
        track.add(new Line(trackBorder, window.getHeight() - trackBorder, window.getWidth() - trackBorder, window.getHeight() - trackBorder));
        for(Drawable l : track)
            window.addEntity(l);

        ArrayList<Car> cars = new ArrayList<>();
        Car temp;
        int carNumber = 100;
        for(int i = 0; i < carNumber; i++) {
            temp = new Car(32, 16, new Point2D(30, 40), track);
            cars.add(temp);
            window.addEntity(temp);
        }

        int cicli = 0;

        try {
            while(true) {
                window.update();
                cicli += 1;
                System.out.println("Ciclo numero: " + cicli);
                if(cicli == 1000) {
                    //System.out.println("New generation");
                    cars.sort(Comparator.comparingInt(Car::getScore));
                    //System.out.println("Generating new generation");
                    ArrayList<Car> tempCars = new ArrayList<>();
                    for(int i = 0; i < carNumber/2; i += 2) {
                        tempCars.add(new Car(32, 16, new Point2D(30, 40), track, NeuralNetwork.reproduce(cars.get(i).getAI(), cars.get(i+1).getAI(), true)));
                        tempCars.add(new Car(32, 16, new Point2D(30, 40), track, NeuralNetwork.reproduce(cars.get(i+1).getAI(), cars.get(i).getAI(), true)));
                    }
                    cars = tempCars;
                    cicli = 0;
                    //System.out.println("Reset entities");
                    window.emptyEntity();
                    for(Line l : track)
                        window.addEntity(l);
                    for(Car c : cars)
                        window.addEntity(c);
                System.out.println("Generated new generation");
                }
                TimeUnit.MILLISECONDS.sleep(1000/60);
            }
        } catch (Exception e) {}
    }

}
