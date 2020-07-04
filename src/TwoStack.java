import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class TwoStack {
    public static double calculate (String calculation) {
        Stack<Double> values = new Stack<Double>();
        Stack<String> operators = new Stack<String>();

        StringTokenizer st = new StringTokenizer(calculation);
        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/"))
                operators.push(s);
            else if (isNumeric(s))
                values.push(Double.parseDouble(s));
            else if (s.equals(")")) {
                double num1 = values.pop();
                double num2 = values.pop();
                String operator = operators.pop();

                switch (operator) {
                    case "+":
                        values.push(num1 + num2);
                        break;
                    case "-":
                        values.push(num1 - num2);
                        break;
                    case "*":
                        values.push(num1 * num2);
                        break;
                    case "/":
                        values.push(num1 / num2);
                }
            }
        }
        return values.pop();
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


    public static void main(String[] args) {
        System.out.println(TwoStack.calculate("( ( 1 + 5 ) / 5 )"));
    }


}
