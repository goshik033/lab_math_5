package functions;

import interfaces.IFunction;

public class SecondFunction implements IFunction {
    public double solve(double x, double y) {
        return 3 * Math.pow(x, 2) - 2 * Math.pow(x, 4) + 2 * x * y;
    }

    public double solve2(double x, double y) {
        return Math.pow(x, 3);
    }
}
