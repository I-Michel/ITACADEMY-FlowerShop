package Validation;

import Product.Product;
import Exception.EmptyStockException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Validation {

    public static void validateStock(ArrayList<Product> productList) throws EmptyStockException {
        try{
            if (productList.isEmpty()) {
                throw new EmptyStockException();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static int validateInt(String message) {
        boolean ok = false;
        Scanner sc = new Scanner(System.in);
        int resultado = 0;

        do {
            System.out.println(message);
            try {
                resultado = sc.nextInt();
                ok = true;
            } catch (InputMismatchException e) {
                System.out.println(e);
                sc.nextLine();
            }
        } while (!ok);
        return resultado;
    }

    public static String validateText(String message) {
        boolean ok = false;
        Scanner sc = new Scanner(System.in);

        String color = sc.nextLine();
        do {
            System.out.println(message);
            if (color.matches("[a-zA-Z]+(\\s+[a-zA-Z]+)*(\\s+\\d+)?")) {
                ok = true;
            } else {
                System.out.println("Expected color name or color name followed by a number.");
            }
        } while (!ok);

        return color;
    }
}
