package Connection.MongoDB;

import Connection.DataBase;
import Product.Product;
import java.sql.Connection;

public class MongoDB implements DataBase {

    private static MongoDB instance;

    public MongoDB() {
    }

    public static MongoDB getInstance() {
        if (instance == null) {
            instance = new MongoDB();
        }
        return instance;
    }

    @Override
    public Connection connect() {
        return null;
    }

    @Override
    public void addStock(DataBase db) {

    }

    @Override
    public void generateNewProduct(DataBase db) {

    }

    @Override
    public void injectNewProduct(DataBase db, Product newProduct, String typeString, int quantity) {

    }

    @Override
    public void removeStock(DataBase db, int productID) {

    }

    @Override
    public void emptyProductStock(DataBase db, int productID) {

    }

    @Override
    public void showStock(DataBase db) {

    }

    @Override
    public void calculateStock(DataBase db) {

    }

    @Override
    public void calculateTotalValue(DataBase db) {

    }

    @Override
    public boolean checkId(DataBase db, int id) {
        return false;
    }
}