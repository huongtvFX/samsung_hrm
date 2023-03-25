package connection;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConnectTest {
    @Test
    public void getConnectionTest() throws SQLException {
        Connection dbConnection = Connect.getConnection();
        assertNotNull(dbConnection, "connection should be successfull.");
    }
}