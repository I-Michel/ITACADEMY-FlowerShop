package Connection.MySQL;

import Connection.DataBase;

import java.sql.*;

public class MySQLDB implements DataBase {

    private static MySQLDB instance;
    private static String url = "jdbc:mysql://localhost:3306/flowershop";
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

    @Override
    public Connection connect() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Error establishing a database connection or selecting database 'flowershop'." + e);
        }
        return null;
    }

   /* @Override
    public void disconnect(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            System.err.println("Error closing the database connection." + e);
        }
    }*/
}