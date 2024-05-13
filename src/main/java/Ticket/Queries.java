package Ticket;

public class Queries {
    public static final String INSERT_TICKET = "INSERT INTO ticket(date, total_Price) VALUES(?, ?)";
    public static final String INSERT_PRODUCT_TICKET="INSERT INTO product_in_ticket(quantity, ticket_id, product_id, ) VALUES(?, ?, ?)";
}
