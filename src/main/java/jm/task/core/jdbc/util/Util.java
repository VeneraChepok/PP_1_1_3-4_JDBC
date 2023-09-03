package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String PASSWORD = "bestuser";
    private static final String USERNAME = "bestuser";
    private static final String URL = "jdbc:mysql://localhost:3306/my_kata_db";

    public Connection getConnection() {
        Connection connection = null;
        Driver driver;

        {
            try {
                driver = new com.mysql.cj.jdbc.Driver();
                DriverManager.registerDriver(driver);

                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                if (!connection.isClosed()) {
                    System.out.println("Connect!");
                }

            } catch (SQLException e) {
                System.err.println("Not Connect!");

            }
            return connection;
        }
    }
}
