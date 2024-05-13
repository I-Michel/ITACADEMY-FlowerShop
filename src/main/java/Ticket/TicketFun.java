package Ticket;
import Connection.MySQL.MySQLDB;
import Connection.MySQL.QueriesMySQL;
import Product.Product;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import Product.Tree;
public class TicketFun {
    public static void createTicket(Ticket ticket) {
    try {
        //inserta valores a ticket
        Connection con = MySQLDB.connect();
        PreparedStatement stmt = con.prepareStatement(QueriesMySQL.INSERT_TICKET, Statement.RETURN_GENERATED_KEYS);

        stmt.setTimestamp(1, ticket.tsdate());
        stmt.setFloat(2, ticket.getPrice());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        int ticketId = rs.getInt(1);
        ticket.setTicketId(ticketId);
        //Inserta valores a product_ticket
        PreparedStatement stmtprodid = con.prepareStatement("select * from product",Statement.RETURN_GENERATED_KEYS);
        HashMap<Product, Integer> productList = ticket.getProductList();

        for (Map.Entry<Product, Integer> entry : productList.entrySet()) {
            Product prod = entry.getKey();
            Integer quant = entry.getValue();

            PreparedStatement stmtprodt = con.prepareStatement(QueriesMySQL.INSERT_PRODUCT_TICKET);
            stmtprodt.setInt(1, ticketId);
            //stmtprodt.setInt(2, prod.getProductId());
            stmtprodt.setInt(3, quant);
            stmtprodt.executeUpdate();

        }
    }

    catch (SQLException e) {
        System.err.println("Falta escribir mensaje error");
        System.err.printf(e.getMessage());
    }}
    public static Product prodCreator(int idProd) {

        try {
            Connection con = MySQLDB.connect();
            float i=0;
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT type FROM product WHERE id="+idProd);
            stmt.setString(4,"type");
            ResultSet rs=stmt.executeQuery();

            if (rs.equals("TREE")) {


                Tree tree=new tree();
                return tree;
            } else if (rs.equals("FLOWER")) {
            } else if (rs.equals("DECORATION")) {
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Falta escribir mensaje error");
            System.err.printf(e.getMessage());
        }
        System.out.println("Se ha ejecutado");
}}
