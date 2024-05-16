package Connection;

import java.sql.Connection;

public interface DataBase {

    Connection connect();
    void disconnect(Connection con);
}