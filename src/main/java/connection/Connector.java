package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class Connector {
    private static final String URL = "jdbc:mysql://localhost:3306/carsshop";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "1111";

    public static Optional<Connection> getConnection() {
        try {
            return Optional.of(DriverManager.getConnection(URL, LOGIN, PASSWORD));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
