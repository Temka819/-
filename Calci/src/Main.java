import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static void calc() {

        Converter converter = new Converter();
        String[] actions = {"+", "-", "/", "*"};
        String[] regexActions = {"\\+", "-", "/", "\\*"};
        Scanner scn = new Scanner(System.in);
        System.out.print("Ввод выражение: ");
        String exp1 = scn.nextLine();
        String exp0 = exp1.toUpperCase();
        String exp = exp0.replace(" ", "");
        int actionIndex = -1;
        for (int i = 0; i < actions.length; i++) {
            if (exp.contains(actions[i])) {
                actionIndex = i;
                break;
            }
        }
        if (actionIndex == -1) {
            System.err.println("throws Exception //т.к. строка не является математической операцией");
            System.exit(0 );
        }
        String[] data = exp.split(regexActions[actionIndex]);
        if (data.length + 1 > 3  ) {
            System.err.println("формат математической операции не удовлетворяет заданию - 2 операнда и 1 оператор (+, -, /, *)");
            System.exit(0);
        }

        if (converter.isRoman(data[0]) == converter.isRoman(data[1])) {
            int a, b;
            boolean isRoman = converter.isRoman(data[0]);
            if (isRoman) {

                a = converter.romanToInt(data[0]);
                b = converter.romanToInt(data[1]);

            } else {
                a = Integer.parseInt(data[0]);
                b = Integer.parseInt(data[1]);
            }
            if (a > 10 || b > 10) {
                System.err.println("throws Exception //Числа должны быть от 1 до 10.");
                System.exit(0);
            }
            if (a == 0 || b == 0) {
                System.err.println("throws Exception //Числа должны быть от 1 до 10.");
                System.exit(0);
            }
            int result = switch (actions[actionIndex]) {
                case "+" -> a + b;
                case "-" -> a - b;
                case "*" -> a * b;
                default -> a / b;
            };

            if (isRoman) {
                if (result == 0) {
                    System.err.println("throws Exception //В Римской системе счисления нет цифры ноль");

                } else if (result < 0) {
                    System.err.println("throws Exception //т.к. в римской системе нет отрицательных чисел");
                } else System.out.println("Ответ: " + converter.intToRoman(result));

            } else {
                System.out.println("Ответ: " + result);
            }
        } else {
            System.err.println("throws Exception //т.к. используются одновременно разные системы счисления");
        }


    }


    public static class  Converter{
        TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
        TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

        public Converter() {
            romanKeyMap.put('I', 1);
            romanKeyMap.put('V', 5);
            romanKeyMap.put('X', 10);
            romanKeyMap.put('L', 50);
            romanKeyMap.put('C', 100);



            arabianKeyMap.put(100, "C");
            arabianKeyMap.put(90, "XC");
            arabianKeyMap.put(50, "L");
            arabianKeyMap.put(40, "XL");
            arabianKeyMap.put(10, "X");
            arabianKeyMap.put(9, "IX");
            arabianKeyMap.put(5, "V");
            arabianKeyMap.put(4, "IV");
            arabianKeyMap.put(1, "I");
        }


        public boolean isRoman(String number){

            return romanKeyMap.containsKey(number.charAt(0));
        }

        public String intToRoman(int number) {
            StringBuilder roman = new StringBuilder();
            int arabianKey;
            do {
                arabianKey = arabianKeyMap.floorKey(number);
                roman.append(arabianKeyMap.get(arabianKey));
                number -= arabianKey;
            } while (number != 0);
            return roman.toString();


        }
        public int romanToInt(String s) {
            int end = s.length() - 1;
            char[] arr = s.toCharArray();
            int arabian;
            int result = romanKeyMap.get(arr[end]);
            for (int i = end - 1; i >= 0; i--) {
                arabian = romanKeyMap.get(arr[i]);

                if (arabian < romanKeyMap.get(arr[i + 1])) {
                    result -= arabian;
                } else {
                    result += arabian;
                }


            } return result;


        }
    }

    public static void main(String[] args){
        Main.calc();
    }

    }