
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:assets.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS assets (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "device_name TEXT NOT NULL, " +
                "assigned_to TEXT NOT NULL, " +
                "serial_no TEXT NOT NULL UNIQUE, " +
                "condition TEXT NOT NULL CHECK " +
                "(condition IN ('NEW', 'GOOD', 'REPAIR_NEEDED'))" +
                ")";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
        }
    }
}
