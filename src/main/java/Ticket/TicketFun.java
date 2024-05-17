package Ticket;
import Connection.MySQL.MySQLDB;
import Connection.MySQL.QueriesMySQL;
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
    public static void generateTicket(DataBase db){
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
                    actualTicket.toString();
                    break;
                case 4:
                    createTicket(actualTicket,db);
                    ok=true;
                    break;

            }
        } while (!ok);


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
        boolean ok = false;


        for (Map.Entry<Product, Integer> entry : actualTicket.getProductList().entrySet()) {
            int prodId = entry.getKey().getId();
            Product product = entry.getKey();
            int value = entry.getValue();
            if (idProdnew == prodId) {

                System.out.println("The product ID " + idProdnew + " already exists in the ticket. ");
                int quantitytoadd = validateInt("How many additional units of the product do you want to add?");
                actualTicket.getProductList().replace(product, (quantitytoadd + value));//si no provar con .put
                ok = true;
            }
        }
        if (ok == false) {
            actualTicket.getProductList().put(prodCreator(idProdnew, db), validateInt("How many units of the product do you want to add?"));
        }


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


        System.out.println("Se ha ejecutado");
        return null;
    }

    public static Ticket removeProductTicket(Ticket actualTicket) {
        System.out.println(actualTicket.toString());
        boolean ok = false;
        int iRemove = validateInt("Which is the Index of the product you want to remove?");
        for (Map.Entry<Product, Integer> entry : actualTicket.getProductList().entrySet()) {
            int prodId = entry.getKey().getId();
            Product product = entry.getKey();

            if (iRemove == prodId) {
                actualTicket.getProductList().remove(product);
                //add a stock
            }
        }
        if (ok == false) {
            System.out.println("No corresponde con ningun núm del indice ");
        }//traducir


        return actualTicket;
    }
    public ArrayList<Ticket> getTickets(DataBase db)  {
        ArrayList<Ticket> ticketList = new ArrayList<>();
        try {
            Connection con = db.connect();
            PreparedStatement statement = con.prepareStatement(QueriesMySQL.SELECT_TICKETS);
            ResultSet rs = statement.executeQuery();


            while (rs.next()) {
                int ticketId = rs.getInt("id_ticket");
                Timestamp timestamp = rs.getTimestamp("date");
                Date date = new Date(timestamp.getTime());
                float price = rs.getFloat("price");
                HashMap<Product, Integer> productList = getProductsListFromTicketiD(ticketId, db);
                Ticket ticketActual = new Ticket(ticketId, date, productList, price);
                ticketList.add(ticketActual);

            }


        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return ticketList;



    }

    public  HashMap<Product, Integer> getProductsListFromTicketiD(int ticketId, DataBase db) throws SQLException {
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
        }}





