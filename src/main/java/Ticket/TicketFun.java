package Ticket;
import Connection.MySQL.MySQLDB;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Date;
public class TicketFun {
    public static void createTicket(Ticket ticket) {
    try {
        Connection con = MySQLDB.connect();
        PreparedStatement stmt = con.prepareStatement(Queries.INSERT_TICKET, Statement.RETURN_GENERATED_KEYS);

        stmt.setTimestamp(1, ticket.getDate());
        stmt.setFloat(2, ticket.getPrice());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        stmt.executeUpdate();
        if (rs.next()) {
            int ticketId = rs.getInt(1);
            ticket.setTicketID(ticketId);

            Map<Product, Integer> products = ticket.getProducts();

            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product product = entry.getKey();
                Integer quantity = entry.getValue();

                PreparedStatement statementProductTicket = connection.prepareStatement(QueriesSQL.SQL_INSERT_PRODUCT_TICKET);
                statementProductTicket.setInt(1, ticketId);
                statementProductTicket.setInt(2, product.getProductId());
                statementProductTicket.setInt(3, quantity);
                statementProductTicket.executeUpdate();
            }
        }
    }

    catch (SQLException e) {
        System.err.println("Falta escribir mensaje error");
        System.err.printf(e.getMessage());
    }}
    public static void pruebaConnection() {

        try {
            Connection con = MySQLDB.connect();
            float i=0;
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO product (price, stock, type ) " +
                            "VALUES ("+i+", 1, 'berenjena')");


            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Falta escribir mensaje error");
            System.err.printf(e.getMessage());
        }
        System.out.println("Se ha ejecutado");
}
