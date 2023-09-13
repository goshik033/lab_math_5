package interpolation;

import modules.Point;

import java.util.ArrayList;
import java.util.Comparator;

public class NewtonInterpolation {

    ArrayList<Point<Double>> pointValues;
    private int n;

    public NewtonInterpolation(ArrayList<Point<Double>> pointValues) {
        this.pointValues = pointValues;
        this.n = pointValues.size();
    }

    private double dividedDifference(int i, int j) {
        if (i == j) {

            return pointValues.get(i).getY();

        } else {
            double numerator = dividedDifference(i + 1, j) - dividedDifference(i, j - 1);
            double denominator = pointValues.get(j).getX() - pointValues.get(i).getX();
            return numerator / denominator;
        }
    }

    public double interpolate(double x) {
        double result = 0.0;
        for (int i = n - 1; i >= 0; i--) {
            result = dividedDifference(0, i) + (x - pointValues.get(i).getX()) * result;
        }
        return result;
    }

    public ArrayList<Point<Double>> interpolation(ArrayList<Point<Double>> pointValues, int steps) {
        ArrayList<Point<Double>> points = new ArrayList<>();
        pointValues.sort(Comparator.comparingDouble(Point::getX));

        for (int i = 1; i < pointValues.size(); i++) {
            double first = pointValues.get(i - 1).getX();
            double second = pointValues.get(i).getX();
            double step = (second - first) / (steps + 1);
            for (double j = first + step; j + 0.0000001 < second; j += step) {

                points.add(new Point<Double>(j, interpolate(j)));

            }
        }

        return points;
    }

    public ArrayList<Point<Double>> extrapolation(ArrayList<Point<Double>> pointValues) {
        ArrayList<Point<Double>> points = new ArrayList<>();
        int steps = 100;
        int xplux = 10;
        double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
        for (Point<Double> point : pointValues) {
            if (point.getX() >= max) {
                max = point.getX();
            }
            if (point.getX() <= min) {
                min = point.getX();
            }
        }
        for (double i = max; i < (max + xplux); i += (max + xplux) / steps) {
            points.add(new Point<Double>(i, interpolate(i)));
        }
        //
        for (double i = min; i > (min - xplux); i += (min - xplux) / steps) {
            points.add(new Point<Double>(i, interpolate(i)));
        }

        return points;
    }


}

