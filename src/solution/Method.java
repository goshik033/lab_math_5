package solution;

import interfaces.IFunction;
import modules.Point;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Method {
    public static ArrayList<Point<Double>> solveByEuler(IFunction f, double epsilon, double a, double y_a, double b) {
        double h = Math.sqrt(epsilon);
        double y = y_a;
        double x = a;

        ArrayList<Point<Double>> points = new ArrayList<>();
        points.add(new Point<>(x, y));

        while (x < b) {
            double k1 = f.solve(x, y);
            y = y + h * k1;
            x += h;
            x = (new BigDecimal(Double.toString(x))).setScale(7, RoundingMode.HALF_UP).doubleValue();

            points.add(new Point<>(x, y));
        }
        return points;

    }
}
