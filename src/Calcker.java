import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Calcker {

    private ArrayList<PaintObject> centroids;
    private ArrayList<PaintObject> points;
    private HashMap<Color, Boolean> usedColors;

    private int MAX_WIDTH, MAX_HEIGHT;

    public Calcker(int width, int height, int countPoints, int countCentroids) {
        MAX_HEIGHT = height;
        MAX_WIDTH = width;
        usedColors = new HashMap<>();
        initColors();
        centroids = new ArrayList<>();
        points = new ArrayList<>();
        if (countCentroids > 7) {
            countCentroids = 7;
        }
        for (int i = 0; i < countCentroids; i++) {
            centroids.add(new PaintObject(getRandX(), getRandY(), getUnusedColor(), true));
        }
        for (int i = 0; i < countPoints; i++) {
            points.add(new PaintObject(getRandX(), getRandY(), Color.GRAY, false));
        }
    }

    public ArrayList<PaintObject> getAllPoint() {
        ArrayList<PaintObject> paintObjects = new ArrayList<>(points);
        paintObjects.addAll(centroids);
        return paintObjects;
    }

    private Color getUnusedColor() {
        Color ret = null;
        for (Color color : usedColors.keySet()) {
            if (!usedColors.get(color)) {
                ret = color;
                break;
            }
        }
        if (ret == null) {
            ret = getRandomColor();
        }
        usedColors.put(ret, true);
        return ret;
    }

    public Color getRandomColor() {
        return new Color(getRandF(), getRandF(), getRandF());
    }

    private float getRandF() {
        return ThreadLocalRandom.current().nextFloat();
    }

    public double calcDist() {
        double maxMove = Integer.MIN_VALUE;
        for (PaintObject point : points) {
            int x = point.X;
            int y = point.Y;
            HashMap<PaintObject, Double> dists = new HashMap<>();
            for (PaintObject centroid : centroids) {
                int cX = centroid.X;
                int cY = centroid.Y;
                double dist = Math.sqrt(Math.pow(x - cX, 2) + Math.pow(y - cY, 2));
                dists.put(centroid, dist);
            }
            Color newColor = Color.GRAY;
            Double min = Double.MAX_VALUE;
            for (PaintObject centroid : dists.keySet()) {
                Double dist = dists.get(centroid);
                if (dist < min) {
                    min = dist;
                    newColor = centroid.color;
                }
            }
            if (min > maxMove) {
                maxMove = min;
            }
            point.color = newColor;
        }
        return maxMove;
    }

    public void relocateCentroids() {
        for (PaintObject centroid : centroids) {
            int sumX = 0;
            int sumY = 0;
            int count = 0;
            for (PaintObject point : points) {
                if (centroid.color.equals(point.color)) {
                    sumX += point.X;
                    sumY += point.Y;
                    count++;
                }
            }
            if (count != 0) {
                centroid.X = sumX / count;
                centroid.Y = sumY / count;
            }
        }
    }

    public void setWidth(int width) {
        this.MAX_WIDTH = width;
    }

    public void setHeight(int height) {
        this.MAX_HEIGHT = height;
    }

    public void generateNewPoints(int count) {
        for (int i = 0; i < count; i++) {
            points.add(new PaintObject(getRandX(), getRandY(), Color.GRAY, false));
        }
    }

    private int getRandX() {
        return ThreadLocalRandom.current().nextInt(20, MAX_WIDTH - 100);
    }

    private int getRandY() {
        return ThreadLocalRandom.current().nextInt(20, MAX_HEIGHT - 100);
    }

    private void initColors() {
        usedColors.put(Color.BLUE, false);
        usedColors.put(Color.CYAN, false);
        usedColors.put(Color.GREEN, false);
        usedColors.put(Color.MAGENTA, false);
        usedColors.put(Color.ORANGE, false);
        usedColors.put(Color.PINK, false);
        usedColors.put(Color.RED, false);
        usedColors.put(Color.YELLOW, false);
    }

    public void addPoint(MouseEvent e) {
        points.add(new PaintObject(e.getX() - 16, e.getY() - 64, Color.GRAY, false));
    }

    public void addCentroid(MouseEvent e) {
        centroids.add(new PaintObject(e.getX() - 16, e.getY() - 64, getUnusedColor(), true));
    }

    public void CleanAll() {
        points = new ArrayList<>();
        centroids = new ArrayList<>();
        usedColors = new HashMap<>();
        initColors();
    }
}
