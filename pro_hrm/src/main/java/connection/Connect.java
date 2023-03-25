package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/hrm";
    public static final String DB_USER = "root";
    public static final String DB_PASS = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_CONNECTION_URL, DB_USER, DB_PASS);
    }
}
