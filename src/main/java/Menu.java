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
            option = validateInt("""
                    0. Close app.\s
                    1. Add product.\s
                    2. Remove product.\s
                    3. Show stock details.\s
                    4. Calculate total stock.\s
                    5. Calculate stock value.\s
                    6. Generate ticket.\s
                    7. Show ticket.\s
                    8. Display purchases.\s
                    9. Show total profit.\s
                    10. Generate JSON from ticket.\s
                    11. Read ticket from JSON.""");

            switch (option) {
                case 1:
                    addOptions(db);
                    break;
                case 2:
                    removeOptions(db);
                    break;
                case 3:
                    showStock(db);
                    break;
                case 4:
                    calculateStock(db);
                    break;
                case 5:
                    calculateTotalValue(db);
                    break;
                case 6:
                    //generateTicket();
                    break;
                case 7:
                    //showTicket();
                case 8:
                    //displayPurchases();
                    break;
                case 9:
                    //showProfit();
                    break;
                case 10:
                    //generateJSON(serializar ultimo ticket/Sc del nombre para el archivo);
                    break;
                case 11:
                    //readJSON(Sc nombre del ticket);
                    break;
                default:
                    System.out.println("Please choose one of the options.");
            }
        } while (option != 0);
    }

    public static void addOptions(DataBase db) {
        // Va a Flowershop
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
                addStock(db);
                break;
            case 2:
                generateNewProduct(db);
                break;
        }
    }

    public static void addStock(DataBase db) {
        // Va a MySQLDB
        int productID = validateInt("Which is the ID of the product you want to add?");
        int quantityToAdd = validateInt("How many do you want to add?");

        try (Connection con = db.connect()) {
            PreparedStatement stmt = con.prepareStatement("SELECT stock FROM product WHERE id_product = ?");
            stmt.setInt(1, productID);
            ResultSet rs = stmt.executeQuery();

            int actualQuantity = 0;
            if (rs.next()) {
                actualQuantity = rs.getInt("stock");
            }
            int newQuantity = actualQuantity + quantityToAdd;

            stmt = con.prepareStatement("UPDATE product SET stock = ? WHERE id_product = ?");
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, productID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating the product stock." + e);
        }
    }

    public static void generateNewProduct(DataBase db) {
        // Va a MYSQLDB
        // FALTA INSERTAR EN TABLA TYPE
        int type = 0;

        do {
            type = validateInt("""
                    What type of product would you like to add?\s
                    1. Flower.\s
                    2. Tree.\s
                    3. Decoration""");
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
                    "INSERT INTO product (price, stock, type ) VALUES ( ? , ? , '?')",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setFloat(1, newProduct.getPrice());
            stmt.setInt(2, quantity);
            stmt.setString(3, typeString);

            int affectedRows = stmt.executeUpdate();
            int newId = 0;

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        newId = rs.getInt(1);
                    }
                }
            }

            String sql = "";

            switch (typeString) {
                case "FLOWER":
                    sql = "INSERT INTO flower (color) VALUES (?) WHERE product_id = ?";
                    stmt = con.prepareStatement(sql);
                    Flower newFlower = (Flower) newProduct;
                    stmt.setString(1, newFlower.getCOLOR());
                    stmt.setInt(2, newId);
                    break;
                case "TREE":
                    sql = "INSERT INTO tree (height) VALUES (?) WHERE product_id = ?";
                    stmt = con.prepareStatement(sql);
                    Tree newTree = (Tree) newProduct;
                    stmt.setFloat(1, newTree.getHEIGHT());
                    stmt.setInt(2, newId);
                    break;
                case "DECORATION":
                    sql = "INSERT INTO decoration (material) VALUES (?) WHERE product_id = ?";
                    stmt = con.prepareStatement(sql);
                    Decoration newDecoration = (Decoration) newProduct;
                    stmt.setString(1, newDecoration.getMATERIAL().name());
                    stmt.setInt(2, newId);
                    break;
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating the product stock." + e);
        }
    }

    public static void removeOptions(DataBase db) {
        // Este método va a Flowershop

        int option;
        do {
            option = validateInt("""
                    Would you like to empty the stock of a product or some of it?\

                    1. Empty the stock of a product.
                    2. Remove some stock of a product.""");
            if (option < 1 || option > 2) {
                System.out.println("Please choose a valid option.");
            }
        } while (option < 1 || option > 2);

        int productID = validateInt("Which is the ID of the product you want to remove?");
        // Falta revisar que el producto exista

        if (option == 1) {
            emptyProductStock(db, productID);
        } else {
            removeStock(db, productID);
        }
    }

    public static void removeStock(DataBase db, int productID) {
        // Este método va a MySQLDB
        //Revisar cuadre stock (validaciones)

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

    public static void emptyProductStock(DataBase db, int productID) {
        // Este método va a MySQLDB

        try (Connection con = db.connect()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE product SET stock = ? WHERE id_product = ?");
            stmt.setInt(1, 0);
            stmt.setInt(2, productID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating the product stock." + e);
        }
    }

    public static void showStock(DataBase db) {
        // Cambiar mensaje de error
        // Esto va a MySQL
        // Pendiente ver si podemos optimizar
        // Falta testear

        try (Connection con = db.connect()) {

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM product WHERE stock != 0;");
            ResultSet rs = stmt.executeQuery();

            StringBuilder result = new StringBuilder();

            while (rs.next()) {
                int productID = rs.getInt("id_product");
                float price = rs.getFloat("price");
                int stock = rs.getInt("stock");
                String type = rs.getString("type");

                Product product = null;
                String sql = "";

                switch (type) {
                    case "FLOWER":
                        sql = "SELECT color FROM flower WHERE product_id = ?";
                        break;
                    case "TREE":
                        sql = "SELECT height FROM tree WHERE product_id = ?";
                        break;
                    case "DECORATION":
                        sql = "SELECT material FROM decoration WHERE product_id = ?";
                        break;
                }

                PreparedStatement stmt2 = con.prepareStatement(sql);
                stmt.setInt(1, productID);
                ResultSet rs2 = stmt2.executeQuery();

                switch (type) {
                    case "FLOWER":
                        String color = rs.getString("color");
                        product = new Flower(price, color, productID);
                        break;
                    case "TREE":
                        sql = "SELECT height FROM tree WHERE product_id = ?";
                        float height = rs.getFloat("height");
                        product = new Tree(price, height, productID);
                        break;
                    case "DECORATION":
                        sql = "SELECT material FROM decoration WHERE product_id = ?";
                        Decoration.Material material = Decoration.Material.valueOf(rs.getString("material"));
                        product = new Decoration(price, material, productID);
                        break;
                }
                result.append(product).append(" - Stock: ").append(stock).append("\n");
            }

            System.out.println(result);

        } catch (SQLException e) {
            System.err.println("." + e);
        }
    }

    public static void calculateStock(DataBase db) {

        //Esto va a MySQL
        //Falta testear queries
        //Falta escribir mensaje error

        try (Connection con = db.connect()) {

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM product WHERE stock != 0 AND type = 'flower';");
            ResultSet rs = stmt.executeQuery();

            int flowerStock = 0;
            while (rs.next()) {
                flowerStock += rs.getInt("stock");
            }

            stmt = con.prepareStatement("SELECT * FROM product WHERE stock != 0 AND type = 'tree';");
            rs = stmt.executeQuery();

            int treeStock = 0;
            while (rs.next()) {
                treeStock += rs.getInt("stock");
            }

            stmt = con.prepareStatement("SELECT * FROM product WHERE stock != 0 AND type = 'decoration';");
            rs = stmt.executeQuery();

            int decorationStock = 0;
            while (rs.next()) {
                decorationStock += rs.getInt("stock");
            }

            System.out.println("Flower stock: " + flowerStock + " items. \n" +
                    "Tree stock: " + treeStock + " items. \n" +
                    "Decoration stock: " + decorationStock + " items.");

        } catch (SQLException e) {
            System.err.println("" + e);
        }
    }


    public static void calculateTotalValue(DataBase db) {
        // Esto va a MySQL
        // Falta testear queries
        // Falta escribir mensaje error

        try (Connection con = db.connect()) {

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM product WHERE stock != 0;");
            ResultSet rs = stmt.executeQuery();

            float totalValue = 0;

            while (rs.next()) {
                float price = rs.getFloat("price");
                int stock = rs.getInt("stock");
                totalValue += price * stock;
            }

            System.out.println("Total value of the flower shop is " + totalValue + " euros.");

        } catch (SQLException e) {
            System.err.println("" + e);
        }
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