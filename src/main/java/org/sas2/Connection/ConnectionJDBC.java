package org.sas2.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC {
    private static Connection connection;
    private static String USER="root";

    private static String PASS="aymane@123";

    private static String HOST="jdbc:mysql://localhost:3306/SAS2";

    private ConnectionJDBC(){
        try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(HOST, USER, PASS);
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if(connection == null)
            connection = new ConnectionJDBC().getConnection();
        return connection;
    }
}
