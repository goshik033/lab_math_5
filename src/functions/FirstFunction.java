package functions;

import interfaces.IFunction;

public class FirstFunction implements IFunction {
    public double solve(double x, double y) {
        return Math.pow(Math.E, (-Math.sin(x))) - y * Math.cos(x);
    }

    public double solve2(double x, double y) {
        return x*Math.pow(Math.E, (-Math.sin(x)));
    }
}
