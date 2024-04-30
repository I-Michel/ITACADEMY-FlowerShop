package Validation;

import Product.Product;

import java.util.ArrayList;
import java.util.Scanner;

public class Validation {

    public static void isStockEmpty(ArrayList<Product> productList){
        if (productList.isEmpty()){
            System.out.println("Stock you are trying to use is empty.");
        }
    }

    public static void enoughStock(){
    }

    public static void validateInt(){
        boolean ok = false;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println(message);
            try {
                sc.nextInt();
                ok = true;
            } catch (Exception e) {
                System.out.println(e);
                sc.nextLine();
            }
        } while (!ok);
    }

    public static void validateText(String color){
        if (color.matches("[0-9]+")){
            System.out.println("Expected a color instead of numbers");
        }
    }
}
