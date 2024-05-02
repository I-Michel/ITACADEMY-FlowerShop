import Product.*;
import Product.Decoration.Material;

import java.util.HashMap;

import static Validation.Validation.validateInt;
import static Validation.Validation.validateText;

public class Menu {
    static HashMap<Flower, Integer> flowerStock = new HashMap<>();
    static HashMap<Tree, Integer> treeStock = new HashMap<>();
    static HashMap<Decoration, Integer> decorationStock = new HashMap<>();


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
                    addProduct();
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
                    System.out.println("Please choose a valid option.");
            }
        } while (option != 0);
    }

    public static void addProduct() {
        int option = 0;
        int type = 0;

        do {
            type = validateInt("What type of product would you like to add?" +
                    "\n1. Flower.\n 2. Tree. \n3. Decoration");
            if (type < 1 || type > 3) {
                System.out.println("Please choose a valid option.");
            }
        } while (type < 1 || type > 3);

        do {
            option = validateInt("Is it an existing product or a new one?" +
                    "\n1. Existing product.\n 2. New product.");
            if (option < 1 || option > 2) {
                System.out.println("Please choose a valid option.");
            }
        } while (option != 1 && option != 2);

        int units = validateInt("How many do you want to add?");

        switch (option) {
            case 1:
                addStock(type, units);
                break;
            case 2:
                createNewProduct(type, units);
                break;
            default:
                System.out.println("Please choose a valid option.");
        }
    }

    public static void addStock(int type, int units) {
        switch (type) {
            case 1: // Flower

                break;
            case 2: // Tree

                break;
            case 3: // Decoration

                break;
        }
    }

    public static void createNewProduct(int type, int units) {
        float price = validateFloat("Which is the price of the new product?");

        switch (type) {
            case 1:
                String color = validateText("Which is the color of the new flower?");
                flowerStock.put(ProductFactory.createFlower(price, color), units);
                break;
            case 2:
                float height = validateFloat("Which is the height of the new tree?");
                treeStock.put(ProductFactory.createTree(price, height), units);
                break;
            case 3:
                int numMaterial = 0;
                Material material = null;
                do {
                    numMaterial = validateInt("Which is the material of the new decoration? Please choose an option:" +
                            "\n1. Wood.\n 2. Plastic");
                    if (numMaterial == 1) {
                        material = Material.WOOD;
                    } else if (numMaterial == 2) {
                        material = Material.PLASTIC;
                    } else {
                        System.out.println("Invalid option.");
                    }
                } while (numMaterial != 1 && numMaterial != 2);
                decorationStock.put(ProductFactory.createDecoration(price, material), units);
                break;
        }
    }
}