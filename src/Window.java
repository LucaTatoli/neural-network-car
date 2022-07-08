import drawable.Drawable;
import drawable.Line;
import drawable.Point2D;
import drawable.Rectangle;
import logic.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
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

    public void update() {
        repaint();
    }

    public static void main(String... args) {
        Window window = new Window("neural-network", 600, 400);
        Rectangle rect = new Rectangle(50, 50, 50, 35, 0);
        window.addEntity(rect);

        Point2D p1 = new Point2D(100, 100);
        Point2D p2 = new Point2D(400, 400);
        Point2D p3 = new Point2D(100, 400);
        Point2D p4 = new Point2D(400, 100);

        window.addEntity(new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY(), Color.BLUE));
        window.addEntity(new Line(p3.getX(), p3.getY(), p4.getX(), p4.getY(), Color.ORANGE));

        Point2D i = Utils.lineLineIntersection(p1, p2, p3, p4);
        System.out.println(i);

        Point2D p5 = new Point2D(35, 60);
        Point2D p6 = new Point2D(150, 75);

        window.addEntity(new Line(p5.getX(), p5.getY(), p6.getX(), p6.getY(), Color.RED));

        window.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rect.setPosition(e.getX(), e.getY());
                System.out.println("============");
                System.out.print("Linea rossa: ");
                System.out.println(Utils.lineRectangleIntersection(rect, p5, p6));
                System.out.print("Linea arancione: ");
                System.out.println(Utils.lineRectangleIntersection(rect, p3, p4));
                System.out.print("Linea blu: ");
                System.out.println(Utils.lineRectangleIntersection(rect, p1, p2));
                System.out.println("============\n\n");
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

        //try {
        //    while(true) {
        //        window.update();
        //        TimeUnit.MILLISECONDS.sleep(1000/144);
        //    }
        //} catch (Exception e) {}
    }

}
