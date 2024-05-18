package Connection.MySQL;

import Connection.DataBase;
import Product.*;
import java.sql.*;
import static Validation.Validation.validateInt;
import static Validation.Validation.validateStock;

public class MySQLDB implements DataBase {

    private static MySQLDB instance;
    private static String url = "jdbc:mysql://localhost:3306/flowershop";
    private static String user = "root";
    private static String password = "1203";

    private MySQLDB() {

    }

    public static MySQLDB getInstance() {
        if (instance == null) {
            instance = new MySQLDB();
        }
        return instance;
    }

    @Override
    public Connection connect() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Error establishing a database connection or selecting database 'flowershop'." + e);
        }
        return null;
    }

    @Override
    public void addStock(DataBase db) {
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
            System.err.println("Error updating the product stock.\n" + e);
        }
    }

    @Override
    public void generateNewProduct(DataBase db) {
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

    @Override
    public void injectNewProduct(DataBase db, Product newProduct, String typeString, int quantity) {
        try (Connection con = db.connect()) {

            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO product (price, stock, type) VALUES (?, ?, ?)",
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
                    sql = "INSERT INTO flower (color, product_id) VALUES (?, ?)";
                    stmt = con.prepareStatement(sql);
                    Flower newFlower = (Flower) newProduct;
                    stmt.setString(1, newFlower.getCOLOR());
                    stmt.setInt(2, newId);
                    break;
                case "TREE":
                    sql = "INSERT INTO tree (height, product_id) VALUES (?, ?)";
                    stmt = con.prepareStatement(sql);
                    Tree newTree = (Tree) newProduct;
                    stmt.setFloat(1, newTree.getHEIGHT());
                    stmt.setInt(2, newId);
                    break;
                case "DECORATION":
                    sql = "INSERT INTO decoration (material, product_id) VALUES (?, ?)";
                    stmt = con.prepareStatement(sql);
                    Decoration newDecoration = (Decoration) newProduct;
                    stmt.setString(1, newDecoration.getMATERIAL().name());
                    stmt.setInt(2, newId);
                    break;
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating the product stock.\n" + e);
        }
    }

    @Override
    public void removeStock(DataBase db, int productID) {
        int quantityToRemove = validateInt("How many do you want to remove?");

        try (Connection con = db.connect()) {
            PreparedStatement stmt = con.prepareStatement("SELECT stock FROM product WHERE id_product = ?");
            stmt.setInt(1, productID);
            ResultSet rs = stmt.executeQuery();

            int actualQuantity = 0;
            if (rs.next()) {
                actualQuantity = rs.getInt("stock");
            }

            boolean ok = validateStock(actualQuantity, quantityToRemove);

            if (ok) {
                int newQuantity = actualQuantity - quantityToRemove;

                stmt = con.prepareStatement("UPDATE product SET stock = ? WHERE id_product = ?");
                stmt.setInt(1, newQuantity);
                stmt.setInt(2, productID);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error updating the product stock.\n" + e);
        }
    }

    @Override
    public void emptyProductStock(DataBase db, int productID) {
        try (Connection con = db.connect()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE product SET stock = ? WHERE id_product = ?");
            stmt.setInt(1, 0);
            stmt.setInt(2, productID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating the product stock.\n" + e);
        }
    }

    @Override
    public void showStock(DataBase db) {
        // Pendiente ver si podemos optimizar, si da tiempo hacerlo en 3 queries sin el switch para que ordene las flores
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
                PreparedStatement stmt2;
                ResultSet rs2;

                switch (type) {
                    case "FLOWER":
                        stmt2 = con.prepareStatement("SELECT color FROM flower WHERE product_id = ?");
                        stmt2.setInt(1, productID);
                        rs2 = stmt2.executeQuery();

                        if (rs2.next()) {
                            String color = rs2.getString("color");
                            product = new Flower(price, color, productID);
                        }
                        break;
                    case "TREE":
                        stmt2 = con.prepareStatement("SELECT height FROM tree WHERE product_id = ?");
                        stmt2.setInt(1, productID);
                        rs2 = stmt2.executeQuery();

                        if (rs2.next()) {
                            float height = rs2.getFloat("height");
                            product = new Tree(price, height, productID);
                        }
                        break;
                    case "DECORATION":
                        stmt2 = con.prepareStatement("SELECT material FROM decoration WHERE product_id = ?");
                        stmt2.setInt(1, productID);
                        rs2 = stmt2.executeQuery();

                        if (rs2.next()) {
                            Decoration.Material material = Decoration.Material.valueOf(rs2.getString("material"));
                            product = new Decoration(price, material, productID);
                        }
                        break;
                }
                result.append(product).append(" - Stock: ").append(stock).append("\n");
            }
            System.out.println(result);

        } catch (SQLException e) {
            System.err.println("Error getting stock details.\n" + e);
        }
    }

    @Override
    public void calculateStock(DataBase db) {
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

            System.out.println("- Flower stock: " + flowerStock + " items. \n" +
                    "- Tree stock: " + treeStock + " items. \n" +
                    "- Decoration stock: " + decorationStock + " items.");

        } catch (SQLException e) {
            System.err.println("Error calculating stock quantity.\n" + e);
        }
    }

    @Override
    public void calculateTotalValue(DataBase db) {

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
            System.err.println("Error calculating stock value.\n" + e);
        }
    }
}