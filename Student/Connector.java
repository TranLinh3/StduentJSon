package Student;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    public static java.sql.Connection getMssql() throws SQLException {
        String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=fptaptech";
        String username ="sa";
        String password ="123";
        java.sql.Connection connection = DriverManager.getConnection(dbURL,username,password);
        return connection;
    }

}