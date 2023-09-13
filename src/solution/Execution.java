package solution;

import interpolation.NewtonInterpolation;
import interfaces.IFunction;
import modules.Data;
import modules.GraphModule;
import modules.Point;
import solution.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Execution {
    public void execute(IFunction f) {
        System.out.println("Введите начало отрезка:");
        Double a = Data.inputData();

        System.out.println("Введите конец отрезка:");
        Double b = Data.inputData();

        System.out.println("Введите y0:");
        Double y0 = Data.inputData();

        System.out.println("Введите точность:");
        Double eps = Data.inputData();

        Map<String, ArrayList<IFunction>> map_func = new HashMap<>();
        Map<String, ArrayList<Point<Double>>> point_func = new HashMap<>();

        ArrayList<Point<Double>> xy = Method.solveByEuler(f, eps, a, y0, b);

//        for (Point<Double> p : xy) {
//            System.out.println("x: [" + p.getX() + "] y: [" + p.getY() + "]");
//        }
        System.out.println("x: [" + xy.get(xy.size()-1).getX() + "] y: [" + xy.get(xy.size()-1).getY() + "]");


//        point_func.put("Исходные данные", xy);
        point_func.put("Получившийся график", xy);
        NewtonInterpolation newtonInterpolation = new NewtonInterpolation(xy);
//        ArrayList<Point<Double>> interpolation = newtonInterpolation.interpolation(xy, 100);
        ArrayList<IFunction> funcs = new ArrayList<>();
        funcs.add(f);
        map_func.put("График функции", funcs);

//        point_func.put("Интерполяция", interpolation);
        new GraphModule(map_func, point_func,a,b,y0);
    }


}
