package Validation;

import java.util.Scanner;

public class Validation {

    public static boolean validateStock(int actualStock, int updateStock, String option) {
        boolean result = false;

        if (actualStock == 0) {
            System.out.println("Cannot " + option + " this product because actual stock is 0.");
        } else if (updateStock > actualStock) {
            System.out.println("Cannot " + option +" "+ updateStock + " items because actual stock is " + actualStock + " items.");
        } else {
            result = true;
        }
        return result;
    }

    public static int validateInt(String message) {
        boolean ok = false;
        Scanner sc = new Scanner(System.in);
        int outputInt = 0;

        do {
            System.out.println(message);
            String input = sc.nextLine();

            if (input.trim().isEmpty()) {
                System.out.println("Please choose a valid option.");
                continue;
            }

            try {
                outputInt = Integer.parseInt(input);

                if (outputInt <= 0){
                    System.err.println("Number entered has to be greater than 0.");
                    continue;
                }

                ok = true;
            } catch (NumberFormatException e) {
                System.err.println("Error: " + e);
                System.out.println("Please choose a valid option.");
            }
        } while (!ok);
        return outputInt;
    }

    public static float validateFloat(String message) {
        boolean ok = false;
        Scanner sc = new Scanner(System.in);
        float outputFloat = 0;

        do {
            System.out.println(message);
            String input = sc.nextLine();

            if (input.trim().isEmpty()) {
                System.out.println("Please choose a valid option.");
                continue;
            }

            try {
                outputFloat = Float.parseFloat(input);

                if (outputFloat <= 0){
                    System.err.println("Number entered has to be greater than 0.");
                    continue;
                }

                ok = true;
            } catch (NumberFormatException e) {
                System.err.println("Error: " + e);
                System.out.println("Please choose a valid option.");
            }
        } while (!ok);
        return outputFloat;
    }

    public static String validateColor(String message) {
        boolean ok = false;
        Scanner sc = new Scanner(System.in);
        String color = "";

        do {
            System.out.println(message);
            color = sc.nextLine();

            if (color.matches("[a-zA-Z]+(\\s+[a-zA-Z]+)*(\\s+\\d+)?")) {
                ok = true;
            } else {
                System.out.println("Expected color name or color name followed by a number.");
            }
        } while (!ok);

        return color;
    }
}