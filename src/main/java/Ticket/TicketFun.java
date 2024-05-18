package Ticket;
import Connection.MySQL.MySQLDB;
import Connection.MySQL.QueriesMySQL;
import FlowerShop.FlowerShop;
import Product.Product;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Product.Tree;
import Product.Flower;
import Product.Decoration;
import Product.Decoration.Material;
import Connection.*;

import static Validation.Validation.validateInt;

public class TicketFun {
//
    public static Ticket generateTicket(DataBase db){
        Ticket actualTicket=new Ticket();
        System.out.println("Se ha generado un ticket que deseas hacer");//
        int option = 0;
        Boolean ok=false;
        do {
            option = validateInt(" \n1. Add product ticket.  \n2. Remove product. \n" +
                    "3. Show actual ticket \n4.Finish Ticket ");

            switch (option) {
                case 1:
                    actualTicket=addProductTicket(actualTicket,db);
                    break;
                case 2:
                    actualTicket=removeProductTicket(actualTicket);
                    break;
                case 3:
                    System.out.println(actualTicket.toString());
                    break;
                case 4:
                    createTicket(actualTicket,db);
                    ok=true;
                    break;

            }
        } while (!ok);
    return actualTicket;

    }

    public static void createTicket(Ticket ticket, DataBase db) {
        try (Connection con = db.connect()) {

            PreparedStatement stmt = con.prepareStatement(QueriesMySQL.INSERT_TICKET, Statement.RETURN_GENERATED_KEYS);

            stmt.setTimestamp(1, ticket.tsdate());
            stmt.setFloat(2, ticket.getPrice());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int ticketId = rs.getInt(1);
            ticket.setTicketId(ticketId);
            //Inserta valores a product_ticket
            //PreparedStatement stmtprodid = con.prepareStatement("select * from product",Statement.RETURN_GENERATED_KEYS);
            HashMap<Product, Integer> productList = ticket.getProductList();

            for (Map.Entry<Product, Integer> entry : productList.entrySet()) {
                Product prod = entry.getKey();
                Integer quant = entry.getValue();

                PreparedStatement stmtprodt = con.prepareStatement(QueriesMySQL.INSERT_PRODUCT_TICKET);
                stmtprodt.setInt(1, ticketId);
                stmtprodt.setInt(2, prod.getId());
                stmtprodt.setInt(3, quant);
                stmtprodt.executeUpdate();

            }
        } catch (SQLException e) {
            System.err.println("Falta escribir mensaje error");
            System.err.printf(e.getMessage());
        }
    }

    public static Ticket addProductTicket(Ticket actualTicket, DataBase db) {
        //Show Stock
        int idProdnew = validateInt("Which is the ID of the product you want to add?");
        boolean ok= false;


        for (Map.Entry<Product, Integer> entry : actualTicket.getProductList().entrySet()) {
            int prodId = entry.getKey().getId();
            Product product = entry.getKey();
            int value = entry.getValue();
            if (idProdnew == prodId) {

                System.out.println("The product ID " + idProdnew + " already exists in the ticket. \n");
                int quantitytoadd = validateInt("How many additional units of the product do you want to add?\n");
                actualTicket.getProductList().replace(product, (quantitytoadd + value));//si no provar con .put

                ok = true;

            }
        }
        if (ok == false) {

            Product product =prodCreator(idProdnew, db);
            if (product!=null){
            actualTicket.getProductList().put(product, validateInt("\nHow many units of the product do you want to add?\n"));}

        }
        actualTicket.calculateTotalPrice();

        //falta restar al stock


        //Modificar Stock
        return actualTicket;
    }

    public static Product prodCreator(int idProd, DataBase db) {


        try {
            Connection con = db.connect();
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

                        Tree tree = new Tree(price, Integer.parseInt(attribute), idProd);
                        System.out.print(tree);
                        return tree;

                    case "FLOWER":
                        Flower flower = new Flower(price, attribute, idProd);
                        return flower;

                    case "DECORATION":
                        Material material = Material.valueOf(attribute.toUpperCase());
                        Decoration decoration = new Decoration(price, material, idProd);
                        return decoration;
                }


            }
        } catch (SQLException e) {
            System.err.println("Falta escribir mensaje error");
            System.err.printf(e.getMessage());
            e.printStackTrace();
        }


        System.out.println("ID incorrect");
        return null;
    }

    public static Ticket removeProductTicket(Ticket actualTicket) {
        System.out.println(actualTicket.toString());
        boolean ok = false;
        int iRemove = validateInt("Which is the ID of the product you want to add?");
        for (HashMap.Entry<Product, Integer> entry : actualTicket.getProductList().entrySet()) {
            int prodId = entry.getKey().getId();
            Product product = entry.getKey();

            if (iRemove == prodId) {
                actualTicket.getProductList().remove(product);
                //add a stock
                ok=true;
                System.out.println("Producto eliminado ");
            }
        }
        if (ok == false) {
            System.out.println("No corresponde con ningun núm del indice ");
        }//traducir


        return actualTicket;
    }
    public static ArrayList<Ticket> getTickets(DataBase db)  {
        ArrayList<Ticket> ticketList = new ArrayList<>();
        try {
            Connection con = db.connect();
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
            e.printStackTrace(System.out);
        }
        return ticketList;



    }

    public static HashMap<Product, Integer> getProductsListFromTicketiD(int ticketId, DataBase db) throws SQLException {
        HashMap<Product, Integer> productList = new HashMap<>();
        try (Connection con = db.connect()) {
            PreparedStatement stmt = con.prepareStatement(QueriesMySQL.SELECT_PRODUCT);

                stmt.setInt(1, ticketId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int idProd = rs.getInt("product_id");
                    String type = rs.getString("type");
                    int price = rs.getInt("price");
                    String attribute = rs.getString("attribute");
                    int quantity=rs.getInt(("quantity"));
                    switch (type) {
                        case "TREE":

                            Tree tree = new Tree(price, Integer.parseInt(attribute), idProd);
                            System.out.print(tree);
                            productList.put(tree, quantity);

                        case "FLOWER":
                            Flower flower = new Flower(price, attribute, idProd);
                            productList.put(flower, quantity);

                        case "DECORATION":
                            Material material = Material.valueOf(attribute.toUpperCase());
                            Decoration decoration = new Decoration(price, material, idProd);
                            productList.put(decoration, quantity);
                    }


                }}catch(SQLException e){
                System.err.println("Falta escribir mensaje error");
                System.err.printf(e.getMessage());
                e.printStackTrace();
            }


                System.out.println("Se ha ejecutado");

        return productList;
        }
    public static void showProfit(ArrayList<Ticket> ticketList) {
        float profit = 0;
        for (Ticket ticket : ticketList) {
            profit = profit + ticket.getPrice();
        }

        System.out.println("The Profit is "+profit);
    }
    public static void displayPurchases(ArrayList<Ticket> ticketList) {
        for (Ticket ticket : ticketList) {
           System.out.println( ticket.toString());
    }
}}





