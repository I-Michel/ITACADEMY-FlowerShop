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

            }



    catch (SQLException e) {
        System.err.println("Falta escribir mensaje error");
        System.err.printf(e.getMessage());
    }}}
