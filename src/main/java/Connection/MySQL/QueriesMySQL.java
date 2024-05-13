package Connection.MySQL;

public class QueriesMySQL {
    public static final String INSERT_TICKET = "INSERT INTO ticket(date, total_Price) VALUES(?, ?)";
    public static final String INSERT_PRODUCT_TICKET="INSERT INTO product_in_ticket(quantity, ticket_id, product_id, ) VALUES(?, ?, ?)";

    public static final String SELECT_PRODUCT="SELECT p.id_product, p.price, p.stock, p.type, COALESCE(f.color, tr.height, d.material) AS attribute " +
            "FROM product p " +
            "LEFT JOIN flower f ON p.id_product = f.product_id " +
            "LEFT JOIN tree tr ON p.id_product = tr.product_id " +
            "LEFT JOIN decoration d ON p.id_product = d.product_id " +
            "WHERE p.id_product = ?";
}
