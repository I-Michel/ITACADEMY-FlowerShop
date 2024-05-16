package Connection.MongoDB;

import Connection.DataBase;

import javax.xml.crypto.Data;
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

    @Override
    public Connection connect() {
        return null;
    }

    @Override
    public void disconnect(Connection con) {

    }
}
