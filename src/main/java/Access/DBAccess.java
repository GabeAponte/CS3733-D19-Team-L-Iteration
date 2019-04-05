package Access;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

@SuppressWarnings({"UnnecessaryCallToStringValueOf", "SqlResolve"})
public abstract class DBAccess {
    /**
     * Connect to the db database
     * Andrew made this
     * @return the Connection object
     */
    public Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:myDB/MasterDB";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}