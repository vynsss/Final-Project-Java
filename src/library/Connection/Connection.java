package library.Connection;

import java.sql.*;

public class Connection {
    private String path = "jdbc:mysql://localhost:3306/library1";
    private String username = "root";
    private String password = "085775643128";
    public java.sql.Connection myConn = null;

    //so the other class can get the connection from the same database
    public Connection() {
        try {
            myConn = DriverManager.getConnection(path, username, password);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //so the other class can directly use this method to get the prepared statement
    public PreparedStatement getPrepstat(String query) {
        try {
            PreparedStatement prepStat = myConn.prepareStatement(query);
            return prepStat;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}