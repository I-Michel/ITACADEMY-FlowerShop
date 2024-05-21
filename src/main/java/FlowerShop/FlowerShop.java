package FlowerShop;

import Connection.DataBase;
import Connection.MongoDB.MongoDB;
import Connection.MySQL.MySQLDB;
import Ticket.Ticket;

import java.io.*;

import static Validation.Validation.validateId;
import static Validation.Validation.validateInt;

public class FlowerShop {

    private static FlowerShop instance;
    private String name;
    private DataBase db;

    private FlowerShop(DataBase db) {
        this.name = "Pipiflower's";
        this.db = db;
    }

    public static FlowerShop getInstance(DataBase db) {
        if (instance == null) {
            instance = new FlowerShop(db);
        }
        return instance;
    }

    public String getName() {
        return this.name;
    }

    public DataBase getDb() {
        return this.db;
    }

    public static FlowerShop connectFlowerShop() {
        int dbOption = 0;
        DataBase db = null;
        System.out.println("Initializing...\nWhich database would you like to connect to the flower shop?");

        do {
            dbOption = validateInt("1. MySQL Data Base." + "\n2. MongoDB Data Base.");

            switch (dbOption) {
                case 1:
                    db = MySQLDB.getInstance();
                    break;
                case 2:
                    db = MongoDB.getInstance();
                    break;
                default:
                    System.out.println("Please choose a valid option:");
            }
        } while (dbOption != 1 && dbOption != 2);

        return new FlowerShop(db);
    }

    public static void addOptions(DataBase db) {
        int option = 0;
        int type = 0;

        do {
            option = validateInt("""
                    Is it an existing product or a new one?\s
                    1. Existing product.\s
                    2. New product.""");
            if (option < 1 || option > 2) {
                System.out.println("Please choose a valid option.");
            }
        } while (option != 1 && option != 2);

        switch (option) {
            case 1:
                db.addStock(db);
                break;
            case 2:
                db.generateNewProduct(db);
                break;
        }
    }

    public static void removeOptions(DataBase db) {
        int option;
        do {
            option = validateInt("""
                    Would you like to empty the stock of a product or some of it?\s
                    1. Empty the stock of a product.\s
                    2. Remove some stock of a product.""");
            if (option < 1 || option > 2) {
                System.out.println("Please choose a valid option.");
            }
        } while (option < 1 || option > 2);

        int ok = 0;
        int productID = 0;
        do {
            productID = validateInt("Which is the ID of the product you want to remove?");
            ok = validateId(db, productID);
        } while (ok == 2);

        if (ok == 1) {
            if (option == 1) {
                db.emptyProductStock(db, productID);
            } else {
                db.removeStock(db, productID);
            }
        }
    }

    public static void generateJSON(Ticket ticket) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("archivo.ser");
            ObjectOutputStream objectOutPutStream = new ObjectOutputStream(fileOutputStream);
            objectOutPutStream.writeObject(ticket);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void readJSON() {

        try {
            FileInputStream archivo = new FileInputStream("archivo.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(archivo);
            Ticket ticket = (Ticket) objectInputStream.readObject();

            System.out.println(ticket);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}