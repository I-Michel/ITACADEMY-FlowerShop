import Connection.*;
import Connection.MySQL.*;
import Connection.MongoDB.*;
import FlowerShop.FlowerShop;
import Product.*;
import java.io.*;
import java.sql.*;
import Ticket.*;

import static Validation.Validation.*;

public class Menu {
    public static void start() {

        FlowerShop flowerShop = FlowerShop.connectFlowerShop();
        DataBase db = flowerShop.getDb();

        int option = 0;
        System.out.println("Welcome to " + flowerShop.getName() + " Flower Shop! Please choose an option:");

        do {
            option = validateInt("0. Close app. \n1. Add product. \n2. Remove product. \n" +
                    "3. Show stock details. \n4. Show product stock. \n5. Show total stock \n6. Show stock value. \n7. Generate ticket \n" +
                    "8. Show ticket.\n9. Display purchases. \n10. Show total profit \n11. Generate JSON from ticket \n12. Read ticket from JSON");

            switch (option) {
                case 1:
                    addOptions(db);
                    break;
                case 2:
                    removeOptions(db);
                    break;
                case 3:
                    //showStock();
                    break;
                case 4:
                    //calculateProductStock();
                    break;
                case 5:
                    //calculateTotalStock();
                    break;
                case 6:
                    //calculateTotalValue();
                    break;
                case 7:
                    //generateTicket();
                    break;
                case 8:
                    //showTicket();
                case 9:
                    //displayPurchases();
                    break;
                case 10:
                    //showProfit();
                    break;
                case 11:
                    //generateJSON(serializar ultimo ticket/Sc del nombre para el archivo);
                    break;
                case 12:
                    //readJSON(Sc nombre del ticket);
                    break;
                default:
                    System.out.println("Please choose one of the options.");
            }
        } while (option != 0);
    }

    public static void addOptions(DataBase db) {
        int option = 0;
        int type = 0;

        do {
            option = validateInt("Is it an existing product or a new one?" +
                    "\n1. Existing product.\n2. New product.");
            if (option < 1 || option > 2) {
                System.out.println("Please choose a valid option.");
            }
        } while (option != 1 && option != 2);

        switch (option) {
            case 1:
                addStock();
                break;
            case 2:
                generateNewProduct(db);
                break;
        }
    }

    public static void addStock() {
        int productID = validateInt("Which is the ID of the product you want to add?");
        int quantity = validateInt("How many do you want to add?");

        //Falta código con sql
    }

    public static void generateNewProduct(DataBase db) {

        //Esto va a la flowershop

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

        injectNewProduct(db, newProduct, typeString, quantity);
    }

    public static void injectNewProduct(DataBase db, Product newProduct, String typeString, int quantity){
        //Esto va a MySQL
        //Falta comprobar query

        try (Connection con = db.connect()){

            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO product (price, stock, type ) VALUES ( ? , ? , '?')");

            stmt.setFloat(1, newProduct.getPrice() );
            stmt.setInt(2, quantity);
            stmt.setString(3, typeString);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating the product stock." + e);
        }
    }

    public static void removeOptions(DataBase db){

    }

    public static void removeStock(DataBase db) {
        //Revisar cuadre stock (validaciones) y que producto exista

        int productID = validateInt("Which is the ID of the product you want to remove?");
        int quantityToRemove = validateInt("How many do you want to remove?");

        try (Connection con = db.connect()) {
            PreparedStatement stmt = con.prepareStatement("SELECT stock FROM product WHERE id_product = ?");
            stmt.setInt(1, productID);
            ResultSet rs = stmt.executeQuery();

            int actualQuantity = 0;
            if (rs.next()) {
                actualQuantity = rs.getInt("stock");
            }
            int newQuantity = actualQuantity - quantityToRemove;

            stmt = con.prepareStatement("UPDATE product SET stock = ? WHERE id_product = ?");
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, productID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating the product stock." + e);
        }
    }

    public static void emptyProductStock(DataBase db){

    }

    public static void calculateProductStock(DataBase db){

    }

    public static void calculateTotalStock(DataBase db){

    }

    public static void calculateTotalValue(DataBase db){

    }

    public static void generateJSON(Ticket ticket, String name) {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(name + ".ser");
            ObjectOutputStream objectOutPutStream = new ObjectOutputStream(fileOutputStream);
            objectOutPutStream.writeObject(ticket);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void readJSON(String name) {

        try {
            FileInputStream Archivo = new FileInputStream(name + ".ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(Archivo);
            Ticket ticket = (Ticket) objectInputStream.readObject();

            System.out.println(ticket);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}