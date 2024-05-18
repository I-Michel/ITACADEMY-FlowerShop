package Connection.MySQL;

public class QueriesMySQL {
    public static final String SELECT_TICKETS = "SELECT DISTINCT t.id_ticket, t.date, t.total_price " +
            "FROM ticket t " +
            "INNER JOIN product_in_ticket pt ON t.id_ticket = pt.ticket_id " +
            "INNER JOIN product p ON pt.product_id = p.id_product;";
    public static final String SELECT_PRODUCT_TICKET =  "SELECT DISTINCT pt.quantity, p.id_product, p.stock, p.price, p.type, pt.ticket_id, " +
            "COALESCE(f.color, tr.height, d.material) AS attribute " +
            "FROM product_in_ticket pt " +
            "INNER JOIN ticket t ON pt.ticket_id = t.id_ticket " +
            "INNER JOIN product p ON pt.product_id = p.id_product " +
            "LEFT JOIN flower f ON p.id_product = f.product_id " +
            "LEFT JOIN decoration d ON p.id_product = d.product_id " +
            "LEFT JOIN tree tr ON p.id_product = tr.product_id " +
            "WHERE t.id_ticket = ?";
    public static final String INSERT_TICKET = "INSERT INTO ticket(date, total_Price) VALUES(?, ?)";
    public static final String INSERT_PRODUCT_TICKET="INSERT INTO product_in_ticket(ticket_id, product_id,quantity ) VALUES(?, ?, ?)";

    public static final String SELECT_PRODUCT="SELECT p.id_product, p.price, p.stock, p.type, COALESCE(f.color, tr.height, d.material) AS attribute " +
            "FROM product p " +
            "LEFT JOIN flower f ON p.id_product = f.product_id " +
            "LEFT JOIN tree tr ON p.id_product = tr.product_id " +
            "LEFT JOIN decoration d ON p.id_product = d.product_id " +
            "WHERE p.id_product = ?";
}
