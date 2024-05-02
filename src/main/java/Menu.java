import Product.*;
import Ticket.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static Validation.Validation.validateInt;

public class Menu {
    static HashMap<Flower, Integer> flowerList = new HashMap<>();
    static HashMap<Tree, Integer> treeList = new HashMap<>();
    static HashMap<Decoration, Integer> decorationList = new HashMap<>();

    static ArrayList<Ticket> ticketList = new ArrayList<Ticket>();

    public static void start() {
        int option = 0;
        System.out.println("Welcome! Please choose an option.");

        do {
            option = validateInt("0. Close app. \n1. Create flower shop. \n2. Add product. \n3. Remove product. \n" +
                    "4. Show stock details. \n5. Show product stock. \n6. Show total stock \n7. Show stock value. \n8. Generate ticket \n" +
                    "9. Show ticket.\n10. Display purchases. \n11. Show total profit \n12. Generate JSON from ticket \n13. Read ticket from JSON");

            switch (option) {
                case 1:
                    //createFlowerShop();
                    break;
                case 2:
                    addProductStock();
                    break;
                case 3:
                    //removeProductStock();
                    break;
                case 4:
                    //showStock();
                    break;
                case 5:
                    //calculateProductStock();
                    break;
                case 6:
                    //calculateTotalStock();
                    break;
                case 7:
                    //calculateTotalValue();
                    break;
                case 8:
                    //generateTicket();
                    break;
                case 9:
                    //showTicket();
                case 10:
                    //displayPurchases();
                    break;
                case 11:
                    //showProfit();
                    break;
                case 12:
                    //generateJSON();
                    break;
                case 13:
                    //readJSON();
                    break;
                default:
                    System.out.println("Please choose one of the options.");
            }
        } while (option != 0);
    }

    public static void addProductStock() {
        int option = 0;
        int type = 0;

        do {
            type = validateInt("What type of product would you like to add? Please choose an option:\n1. Flower.\n 2. Tree. \n3. Decoration");
            if (type < 1 || type > 3) {
                System.out.println("Invalid option.");
            }
        } while (type < 1 || type > 3);

        do {
            option = validateInt("Would you like to add an existing product or a new one? Please choose an option:\n1. Existing product.\n 2. New product.");
            switch (option) {
                case 1: // Existing

                    break;
                case 2: // New product
                    if (type == 1) {
                        flowerList.put(ProductFactory.createFlower(price, color), units);
                    } else if (type == 2) {
                        treeList.put(ProductFactory.createTree(price, height), units);
                    } else if (type == 3) {
                        decorationList.put(ProductFactory.createDecoration(price, material), units);
                    }
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 1 && option != 2);

        int units = validateInt("How many units do you want to add?");


        switch (option) {
            case 1:
                System.out.println("How many do you want to add?");
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                System.out.println("Please choose one of the options.");
        }
    }

    public static void generateTicket() {
        Ticket actualTicket=new Ticket(LocalDateTime.now());
        int option = 0;
        do {
            option = validateInt("\"0. Add product. \n1. Remove product. \n2. Show ticket. \n3.Generate Ticket");

            switch (option) {
                case 1:
                    //que tipo producto quieres añadir
                    System.out.println("How many do you want to add?");//que tipo de
                    break;
                case 2:
                    break;
                case 3:
                    System.out.println(actualTicket.toString());
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Please choose one of the options.");
            }
        }
    }
}

