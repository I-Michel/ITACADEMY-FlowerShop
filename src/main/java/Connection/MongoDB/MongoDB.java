package Connection.MongoDB;

import Connection.DataBase;
import java.sql.Connection;
import java.sql.DriverManager;

public class MongoDB implements DataBase {

    private static MongoDB instance;

    public MongoDB() {
    }

    public static MongoDB getInstance() {
        if (instance == null) {
            instance = new MongoDB();
        }
        return instance;
    }

    /* public static Connection connect() {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            return con;
        } catch (SQLException e) {
            System.err.println("Falta escribir mensaje error");
            System.err.printf(e.getMessage());
        }
        return null;
    } */
}
