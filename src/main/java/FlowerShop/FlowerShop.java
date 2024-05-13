package FlowerShop;

import Connection.DataBase;
import Connection.MongoDB.MongoDB;
import Connection.MySQL.MySQLDB;
import Product.Flower;

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
        if(instance == null){
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
}