/*
package core.utils;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DBUtils implements AutoCloseable {
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;
    private Connection connection;

    public DBUtils(ConfigReader configReader) {
        this.dbUrl = configReader.getDbUrl();
        this.dbUsername = configReader.getDbUsername();
        this.dbPassword = configReader.getDbPassword();
    }

    public ResultSet executeSelectQuery(String query) throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = connect();
        }
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeQuery(query);
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}*/
