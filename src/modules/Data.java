package modules;

import java.util.Scanner;

public class Data {
    private double eps;
    private double a;
    private double b;

    public static Double inputData() {
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                String str = in.nextLine();
                double num = Double.parseDouble(str);
                return num;
            } catch (NumberFormatException wrongForm) {
                System.out.println("Неверный формат, попробуйте еще раз");

            }
        }
    }

    public static Integer inputIntData() {
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                String str = in.nextLine();
                int num = Integer.parseInt(str);
                return num;
            } catch (NumberFormatException wrongForm) {
                System.out.println("Неверный формат, попробуйте еще раз");

            }
        }
    }


    public double getEps() {
        return eps;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }
}
