package solution;

import functions.FirstFunction;
import functions.SecondFunction;
import interfaces.IFunction;
import modules.Menu;
import solution.Execution;

import java.util.Scanner;

public class Printer {
    public void go() {
        Execution execute = new Execution();
        IFunction f1 = new FirstFunction();
        IFunction f2 = new SecondFunction();
        Scanner in = new Scanner(System.in);

        Menu.printMenu();
        String userInput = in.nextLine().trim();

        while (!userInput.equals("0")) {

            if (userInput.equals("1")) {
                execute.execute(f1);
            }

            if (userInput.equals("2")) {
                execute.execute(f2);
            }

            Menu.printMenu();
            userInput = in.nextLine().trim();
        }
    }
}
