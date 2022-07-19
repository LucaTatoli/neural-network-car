package logic;

import drawable.Drawable;
import drawable.Line;
import drawable.Point2D;
import window.Window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String... args) {
        int width = 1200;
        int height = 900;
        Window window = new Window("neural-network", width, height);
        ArrayList<Line> track = new ArrayList<>();

        //OUTER LINES
        int trackBorder1 = 10;
        track.add(new Line(trackBorder1, trackBorder1, window.getWidth() - trackBorder1, trackBorder1));
        track.add(new Line(window.getWidth() - trackBorder1, trackBorder1, window.getWidth() - trackBorder1, window.getHeight() - trackBorder1));
        track.add(new Line(trackBorder1, trackBorder1, trackBorder1, window.getHeight() - trackBorder1));
        track.add(new Line(trackBorder1, window.getHeight() - trackBorder1, window.getWidth() - trackBorder1, window.getHeight() - trackBorder1));
        //INNER LINES
        int trackBorder2 = 160;
        track.add(new Line(trackBorder2, trackBorder2, window.getWidth() - trackBorder2, trackBorder2));
        track.add(new Line(window.getWidth() - trackBorder2, trackBorder2, window.getWidth() - trackBorder2, window.getHeight() - trackBorder2));
        track.add(new Line(trackBorder2, trackBorder2, trackBorder2, window.getHeight() - trackBorder2));
        track.add(new Line(trackBorder2, window.getHeight() - trackBorder2, window.getWidth() - trackBorder2, window.getHeight() - trackBorder2));
        for (Drawable l : track)
            window.addEntity(l);

        ArrayList<Line> checkPoints = new ArrayList<>();
        checkPoints.add(new Line(window.getWidth() / 2, trackBorder1, window.getWidth() / 2, trackBorder2));
        checkPoints.add(new Line(window.getWidth() - trackBorder1, window.getHeight() / 2, window.getWidth() - trackBorder2, window.getHeight() / 2));
        checkPoints.add(new Line(window.getWidth() / 2, window.getHeight() - trackBorder1, window.getWidth() / 2, window.getHeight() - trackBorder2));
        checkPoints.add(new Line(trackBorder1, window.getHeight() / 2, trackBorder2, window.getHeight() / 2));

        for (Drawable l : checkPoints)
            window.addEntity(l);

        ArrayList<Car> cars = new ArrayList<>();
        Car temp;
        int carNumber = 1000;
        int carX, carY;
        carX = 16;
        carY = 8;
        Point2D carSpawnPoint = new Point2D(250, 50);
        for (int i = 0; i < carNumber; i++) {
            temp = new Car(carX, carY, carSpawnPoint, track, checkPoints);
            cars.add(temp);
            window.addEntity(temp);
        }

        int cicli = 0;
        int generationNumber = 0;
        System.out.println("Generation number: " + generationNumber);
        try {
            while (true) {
                window.update();
                cicli += 1;

                boolean end = true;
                for (Car c : cars)
                    if (!c.isDead()) {
                        end = false;
                    }

                if (cicli == 1000 || end) {
                    cars.sort(Comparator.comparingDouble(Car::getScore));
                    System.out.println("Generation " + generationNumber + " ended with max fitness = " + cars.get(cars.size()-1).getScore());
                    generationNumber++;
                    System.out.println("Generation number: " + generationNumber);
                    float totalScore = 0, currentScore, selectedScore;
                    for (Car car : cars)
                        totalScore += car.getScore();

                    Random selector = new Random();

                    Car parent1 = null, parent2 = null;

                    ArrayList<Car> tempCars = new ArrayList<>();
                    for (int i = 0; i < carNumber / 2; i += 2) {
                        //RANDOM EXTRACT FIRST PARENT
                        selectedScore = selector.nextFloat() * totalScore;
                        currentScore = 0;
                        for (Car car : cars) {
                            currentScore += car.getScore();
                            if (currentScore > selectedScore) {
                                parent1 = car;
                                break;
                            }
                        }

                        //RANDOM EXTRACT SECOND PARENT
                        selectedScore = selector.nextFloat() * totalScore;
                        currentScore = 0;
                        for (Car car : cars) {
                            currentScore += car.getScore();
                            if (currentScore > selectedScore) {
                                parent2 = car;
                                break;
                            }
                        }
                        tempCars.add(new Car(carX, carY, carSpawnPoint, track, checkPoints, NeuralNetwork.reproduce(parent1.getAI(), parent2.getAI(), true)));
                        tempCars.add(new Car(carX, carY, carSpawnPoint, track, checkPoints, NeuralNetwork.reproduce(parent2.getAI(), parent1.getAI(), true)));
                    }
                    cars = tempCars;
                    cicli = 0;

                    window.emptyEntity();
                    for (Line l : track)
                        window.addEntity(l);
                    for (Line l : checkPoints)
                        window.addEntity(l);
                    for (Car c : cars)
                        window.addEntity(c);
                }
                TimeUnit.MILLISECONDS.sleep(1000 / 60);
            }
        } catch (Exception e) {
        }
    }

}
