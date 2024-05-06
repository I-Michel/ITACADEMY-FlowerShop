package Connection.MySQL;

import java.sql.*;

public class MySQLDB {

    private static MySQLDB instance;
    private static String url = "jdbc:mysql://localhost:3306/flowershop";
    private static String user = "root";
    private static String password = "";

    private MySQLDB() {

    }

    public static MySQLDB getInstance() {
        if (instance == null) {
            instance = new MySQLDB();
        }
        return instance;
    }

    public void connectDB() {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Falta escribir mensaje error");
            System.err.printf(e.getMessage());
        }
    }
}
