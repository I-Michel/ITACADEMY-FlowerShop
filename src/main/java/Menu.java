import Connection.MySQL.MySQLDB;
import Product.Decoration;
import Product.Flower;
import Product.ProductFactory;
import Product.Tree;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.SQLException;


import static Validation.Validation.validateInt;

public class Menu {
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
                    //addProduct();
                    break;
                case 3:
                    //removeProduct();
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

    public static void addProduct() {
        int option = 0;
        int type = 0;

        do {
            option = validateInt("Is it an existing product or a new one?" +
                    "\n1. Existing product.\n 2. New product.");
            if (option < 1 || option > 2) {
                System.out.println("Please choose a valid option.");
            }
        } while (option != 1 && option != 2);

        switch (option) {
            case 1:
                addStock();
                break;
            case 2:
                createNewProduct();
                break;
            default:
                System.out.println("Please choose a valid option.");
        }
    }

    public static void addStock() {
        int productID = validateInt("Which is the ID of the product you want to add?");
        int quantity = validateInt("How many do you want to add?");

        //Falta código con sql
    }

    public static void createNewProduct() {
        int type = 0;
        do {
            type = validateInt("What type of product would you like to add?" +
                    "\n1. Flower.\n 2. Tree. \n3. Decoration");
            if (type < 1 || type > 3) {
                System.out.println("Please choose a valid option.");
            }
        } while (type < 1 || type > 3);

        switch (type) {
            case 1:
                Flower newFlower = ProductFactory.createFlower();
                break;
            case 2:
                Tree newTree = ProductFactory.createTree();
                break;
            case 3:
                Decoration newDecoration = ProductFactory.createDecoration();
                break;
        }

        int quantity = validateInt("How many do you want to add?");

        // Falta código con sql
    }

    public static void removeStock() {
        int productID = validateInt("Which is the ID of the product you want to remove?");
        int quantity = validateInt("How many do you want to remove?");

        //Falta código con sql y revisar cuadre stock (validaciones)
    }

    public static void pruebaConnection() {

        try {
            Connection con = MySQLDB.connect();

            Statement stmt = con.createStatement();

            int rs = stmt.executeUpdate(
                    "INSERT INTO product (price, stock, type ) " +
                            "VALUES (12, 1, 'berenjena')");
            System.out.println(rs);

        } catch (SQLException e) {
            System.err.println("Falta escribir mensaje error");
            System.err.printf(e.getMessage());
        }
        System.out.println("Se ha ejecutado");
    }
}
