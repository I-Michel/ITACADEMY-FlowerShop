package Connection;

import Product.Product;
import java.sql.Connection;

public interface DataBase {

    Connection connect();
    void addStock(DataBase db);
    void generateNewProduct(DataBase db);
    void injectNewProduct(DataBase db, Product newProduct, String typeString, int quantity);
    void removeStock(DataBase db, int productID);
    void emptyProductStock(DataBase db, int productID);
    void showStock(DataBase db);
    void calculateStock(DataBase db);
    void calculateTotalValue(DataBase db);
    boolean checkId(DataBase db, int id);
}
