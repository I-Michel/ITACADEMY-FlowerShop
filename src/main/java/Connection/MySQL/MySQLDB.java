package Connection.MySQL;

import Connection.DataBase;
import java.sql.*;

public class MySQLDB implements DataBase {

    private static MySQLDB instance;
    private static String url = "jdbc:mysql://localhost1:3306/flowershop";
    private static String user = "root";
    private static String password = "2307";

    private MySQLDB() {

    }

    public static MySQLDB getInstance() {
        if (instance == null) {
            instance = new MySQLDB();
        }
        return instance;
    }

    public static Connection connect() {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            return con;
        } catch (SQLException e) {
            System.err.println("Falta escribir mensaje error");
            System.err.printf(e.getMessage());
        }
        return null;
    }
}