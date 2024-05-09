package Validation;

import Product.Product;
import Exception.EmptyStockException;
import Exception.NotEnoughStockException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Validation {

    public static void validateStock(HashMap <Product, Integer> productStock, Product key ) throws EmptyStockException {
       /* try{
            if (productStock.isEmpty()) {
                throw new EmptyStockException();
            } else if ()(
            throw new NotEnoughStockException();
            )
        }catch(Exception e){
            System.out.println(e.getMessage());
        }*/
    }

    public static int validateInt(String message) {
        boolean ok = false;
        Scanner sc = new Scanner(System.in);
        int outputInt = 0;

        do {
            System.out.println(message);
            try {
                outputInt = sc.nextInt();
                sc.nextLine();
                ok = true;
            } catch (InputMismatchException e) {

                //Falta escribir mensaje error
                System.err.println("Falta escribir mensaje error");
                sc.nextLine();
            }
        } while (!ok);
        return outputInt;
    }

    public static float validateFloat(String message){
        boolean ok = false;
        Scanner sc = new Scanner(System.in);
        float outputFloat = 0;

        do {
            System.out.println(message);
            try {
                outputFloat = sc.nextFloat();
                ok = true;
            } catch (InputMismatchException e) {

                //Falta escribir mensaje error
                System.err.println("");
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
                System.err.println("Expected color name or color name followed by a number.");
            }
        } while (!ok);

        return color;
    }
}