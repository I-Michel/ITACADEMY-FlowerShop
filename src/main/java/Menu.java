import Connection.MySQL.MySQLDB;
import Product.Product;
import Product.ProductFactory;

import java.sql.ResultSet;
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
        }
    }

    public static void addStock() {
        int productID = validateInt("Which is the ID of the product you want to add?");
        int quantity = validateInt("How many do you want to add?");

        //Falta código con sql y revisar cuadre stock (validaciones)

        //Falta testear esta query
        try {
            Connection con = MySQLDB.connect();

            Statement stmt = con.createStatement();

            stmt.executeUpdate(
                    "\"UPDATE producto SET stock = " + quantity + " WHERE producto.id = " + productID);

        } catch (SQLException e) {
            System.err.println("Falta escribir mensaje error");
            System.err.printf(e.getMessage());
        }
        System.out.println("Se ha ejecutado");
    }

    public static void createNewProduct() {
        int type = 0;
        do {
            type = validateInt("What type of product would you like to add?" +
                    "\n1. Flower.\n2. Tree. \n3. Decoration");
            if (type < 1 || type > 3) {
                System.out.println("Please choose a valid option.");
            }
        } while (type < 1 || type > 3);

        Product newProduct = null;
        String typeString = "";

        switch (type) {
            case 1:
                newProduct = ProductFactory.createFlower();
                typeString = "FLOWER";
                break;
            case 2:
                newProduct = ProductFactory.createTree();
                typeString = "TREE";
                break;
            case 3:
                newProduct = ProductFactory.createDecoration();
                typeString = "DECORATION";
                break;
        }

        int quantity = validateInt("How many do you want to add?");

        try {
            Connection con = MySQLDB.connect();

            Statement stmt = con.createStatement();

            stmt.executeUpdate(
                    "INSERT INTO product (price, stock, type ) " +
                            "VALUES (" + newProduct.getPrice() + ", " + quantity + ", '" + typeString + "')");

        } catch (SQLException e) {
            System.err.println("Falta escribir mensaje error");
            System.err.printf(e.getMessage());
        }
        System.out.println("Se ha ejecutado");
    }

    public static void removeProduct() {

        int productID = validateInt("Which is the ID of the product you want to remove?");

        //Falta testear esta query
        try {
            Connection con = MySQLDB.connect();

            Statement stmt = con.createStatement();

            stmt.executeUpdate(
                    "\"DELETE FROM producto WHERE producto.id = " + productID);

        } catch (SQLException e) {
            System.err.println("Falta escribir mensaje error");
            System.err.printf(e.getMessage());
        }
        System.out.println("Se ha ejecutado");

    }

    public static void removeStock() {

        int productID = validateInt("Which is the ID of the product you want to remove?");
        int quantityToRemove = validateInt("How many do you want to remove?");
        int newQuantity = 0;
        int actualQuantity = 0;

        //Falta revisar cuadre stock (validaciones)

        //Falta testear esta query

        try {
            Connection con = MySQLDB.connect();
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT stock FROM product WHERE id_product = " + productID);

            actualQuantity = rs.getInt("stock");

            newQuantity = actualQuantity - quantityToRemove;

            int prueba = stmt.executeUpdate(
                    "\"UPDATE product SET stock = " + newQuantity + " WHERE id_product = " + productID);

        } catch (SQLException e) {
            System.err.println("Falta escribir mensaje error");
            System.err.printf(e.getMessage());
        }
        System.out.println("Se ha ejecutado");
    }
}
