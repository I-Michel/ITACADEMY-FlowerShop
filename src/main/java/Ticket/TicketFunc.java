package Ticket;

import Connection.MySQL.QueriesMySQL;

import java.sql.*;
import java.util.*;
import java.util.Date;

import Product.Decoration.Material;
import Product.*;
import Connection.*;

import static Validation.Validation.validateInt;
import static Validation.Validation.validateStock;

public class TicketFunc {
    public static Ticket generateTicket(DataBase db) {
        Ticket actualTicket = new Ticket();
        System.out.println("Generating Ticket...");//
        int option = 0;
        Boolean ok = false;
        do {
            option = validateInt("""
                               
                    1. Add product ticket. \s
                    2. Remove product.\s
                    3. Show actual ticket\s
                    4. Finish Ticket\s""");

            switch (option) {
                case 1:
                    actualTicket = addProductTicket(actualTicket, db);
                    break;
                case 2:
                    actualTicket = removeProductTicket(actualTicket, db);
                    break;
                case 3:
                    System.out.println(actualTicket);
                    break;
                case 4:
                    createTicket(actualTicket, db);
                    ok = true;
                    break;

            }
        } while (!ok);
        return actualTicket;

    }

    public static void createTicket(Ticket ticket, DataBase db) {

        try (Connection con = db.connect();) {

            PreparedStatement stmt = con.prepareStatement(QueriesMySQL.INSERT_TICKET, Statement.RETURN_GENERATED_KEYS);

            stmt.setTimestamp(1, ticket.tsdate());
            stmt.setFloat(2, ticket.getPrice());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                int ticketId = rs.getInt(1);
                ticket.setTicketId(ticketId);

                HashMap<Product, Integer> productList = ticket.getProductList();

                for (HashMap.Entry<Product, Integer> entry : productList.entrySet()) {
                    Product prod = entry.getKey();
                    Integer quant = entry.getValue();

                    PreparedStatement stmtprodt = con.prepareStatement(QueriesMySQL.INSERT_PRODUCT_TICKET);
                    stmtprodt.setInt(1, ticketId);
                    stmtprodt.setInt(2, prod.getId());
                    stmtprodt.setInt(3, quant);
                    stmtprodt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.err.printf("Error creating ticket: " + e.getMessage());
        }
    }

    public static void addStockTicket(DataBase db, int productId, int quantity) {
        int productID = productId;
        int quantityToAdd = quantity;

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

    public static Ticket addProductTicket(Ticket actualTicket, DataBase db) {
        db.showStock(db);
        int idProdnew = validateInt("Which is the ID of the product you want to add?");
        boolean ok = false;
        int quantitytoadd = 0;

            for (Map.Entry<Product, Integer> entry : actualTicket.getProductList().entrySet()) {
                int prodId = entry.getKey().getId();
                Product product = entry.getKey();
                int value = entry.getValue();
                if (idProdnew == prodId) {

                System.out.println("The product ID " + idProdnew + " already exists in the ticket. \n");
                quantitytoadd = validateInt("How many additional units of the product do you want to add?\n");
                actualTicket.getProductList().replace(product, (quantitytoadd + value));

                ok = true;
                TicketFunc.removeStockTicket(db, idProdnew, quantitytoadd);
            }
        }
        if (!ok) {

            Product product = prodCreator(idProdnew, db);
            if (product != null) {
                quantitytoadd = validateInt("\nHow many units of the product do you want to add?\n");
                if (TicketFunc.removeStockTicket(db, idProdnew, quantitytoadd)) {
                    actualTicket.getProductList().put(product, quantitytoadd);
                }

            }

            actualTicket.calculateTotalPrice();

        }
        return actualTicket;
    }

    public static Product prodCreator(int idProd, DataBase db) {


        try (Connection con = db.connect();) {

            PreparedStatement stmt = con.prepareStatement(
                    QueriesMySQL.SELECT_PRODUCT);

            stmt.setInt(1, idProd);
            ResultSet rs = stmt.executeQuery();


            if (rs.next()) {
                String type = rs.getString("type");
                int price = rs.getInt("price");
                String attribute = rs.getString("attribute");
                switch (type) {
                    case "TREE":
                        return new Tree(price, Integer.parseInt(attribute), idProd);

                    case "FLOWER":
                        return new Flower(price, attribute, idProd);

                    case "DECORATION":
                        Material material = Material.valueOf(attribute.toUpperCase());
                        return new Decoration(price, material, idProd);

                }

            }
        } catch (SQLException e) {
            System.err.printf("Error creating product: " + e.getMessage());
        }


        System.out.println("ID incorrect");
        return null;
    }

    public static Ticket removeProductTicket(Ticket actualTicket, DataBase db) {
        System.out.println(actualTicket.toString());
        boolean ok = false;
        int iRemove = validateInt("Which is the ID of the product you want to remove?");
        for (HashMap.Entry<Product, Integer> entry : actualTicket.getProductList().entrySet()) {
            int prodId = entry.getKey().getId();
            Product product = entry.getKey();
            int quantity = entry.getValue();

            if (iRemove == prodId) {
                actualTicket.getProductList().remove(product);
                TicketFunc.addStockTicket(db, prodId, quantity);
                ok = true;
                System.out.println("Product deleted ");
            }
        }
        if (!ok) {
            System.out.println("ID not found ");
        }

        actualTicket.calculateTotalPrice();
        return actualTicket;
    }

    public static ArrayList<Ticket> getTickets(DataBase db) {
        ArrayList<Ticket> ticketList = new ArrayList<>();
        try (Connection con = db.connect()) {
            PreparedStatement statement = con.prepareStatement(QueriesMySQL.SELECT_TICKETS);
            ResultSet rs = statement.executeQuery();


            while (rs.next()) {
                int ticketId = rs.getInt("id_ticket");
                Timestamp timestamp = rs.getTimestamp("date");
                Date date = new Date(timestamp.getTime());
                float tPrice = rs.getFloat("total_price");
                HashMap<Product, Integer> productList = getProductsListFromTicketiD(ticketId, db);
                Ticket ticketActual = new Ticket(ticketId, date, productList, tPrice);
                ticketList.add(ticketActual);

            }


        } catch (SQLException e) {
            System.err.println("Error retrieving tickets: " + e.getMessage());
        }
        return ticketList;


    }

    public static boolean removeStockTicket(DataBase db, int productID, int quantityToRemove) {


        try (Connection con = db.connect()) {
            PreparedStatement stmt = con.prepareStatement("SELECT stock FROM product WHERE id_product = ?");
            stmt.setInt(1, productID);
            ResultSet rs = stmt.executeQuery();

            int actualQuantity = 0;
            if (rs.next()) {
                actualQuantity = rs.getInt("stock");
            }

            boolean ok = validateStock(actualQuantity, quantityToRemove, "add");

            if (ok) {
                int newQuantity = actualQuantity - quantityToRemove;

                stmt = con.prepareStatement("UPDATE product SET stock = ? WHERE id_product = ?");
                stmt.setInt(1, newQuantity);
                stmt.setInt(2, productID);
                stmt.executeUpdate();
                return ok;
            }
        } catch (SQLException e) {
            System.err.println("Error updating the product stock.\n" + e);
        }
        return false;
    }

    public static HashMap<Product, Integer> getProductsListFromTicketiD(int ticketId, DataBase db) throws SQLException {
        HashMap<Product, Integer> productList = new HashMap<>();
        try (Connection con = db.connect()) {
            PreparedStatement stmt = con.prepareStatement(QueriesMySQL.SELECT_PRODUCT_TICKET);

            stmt.setInt(1, ticketId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idProd = rs.getInt("id_product");
                String type = rs.getString("type");
                int price = rs.getInt("price");
                String attribute = rs.getString("attribute");
                int quantity = rs.getInt("quantity");
                switch (type) {
                    case "TREE":

                        Tree tree = new Tree(price, Integer.parseInt(attribute), idProd);
                        productList.put(tree, quantity);
                        break;

                    case "FLOWER":
                        Flower flower = new Flower(price, attribute, idProd);
                        productList.put(flower, quantity);
                        break;

                    case "DECORATION":
                        Material material = Material.valueOf(attribute.toUpperCase());
                        Decoration decoration = new Decoration(price, material, idProd);
                        productList.put(decoration, quantity);
                        break;
                }


            }
        } catch (SQLException e) {
            System.err.printf("Error retrieving products from the ticket with ID: " + ticketId);

        }


        return productList;
    }

    public static void showProfit(ArrayList<Ticket> ticketList) {
        float profit = 0;
        for (Ticket ticket : ticketList) {
            profit = profit + ticket.getPrice();
        }

        System.out.println("The Profit is " + profit + " €.");
    }

    public static void displayPurchases(ArrayList<Ticket> ticketList) {
        for (Ticket ticket : ticketList) {
            System.out.println(ticket.toString());
        }
    }
}





