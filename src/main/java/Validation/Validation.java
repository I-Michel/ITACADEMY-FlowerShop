package Validation;

import Product.Product;
import Exception.EmptyStockException;
import Exception.NotEnoughStockException;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Validation {

    public static boolean validateStock(int actualStock, int updateStock) {
        boolean result = false;

        if (actualStock == 0) {
            System.out.println("Cannot remove this product because actual stock is 0.");
        } else if (updateStock > actualStock) {
            System.out.println("Cannot remove " + updateStock + " items because actual stock is " + actualStock + " items.");
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
            try {
                outputInt = sc.nextInt();
                ok = true;
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
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
            try {
                outputFloat = sc.nextFloat();
                ok = true;
            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
                sc.nextLine();
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