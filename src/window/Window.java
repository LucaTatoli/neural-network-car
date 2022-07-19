package window;

import drawable.Drawable;
import drawable.Line;
import drawable.Point2D;
import logic.Car;
import logic.NeuralNetwork;
import logic.Vector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
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
        try {
            for (Drawable e : entities) {
                e.update();
                e.draw(g2d);
            }
        } catch (Exception e) {}
    }

    public void addEntity(Drawable d) {
        entities.add(d);
    }

    public void addEntities(List<Drawable> d) { entities.addAll(d); }

    public void emptyEntity() { entities.clear(); }

    public void update() {
        repaint();
    }

}
