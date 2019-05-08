package model;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {

    private static java.sql.Connection con = null;
    public static java.sql.Connection getInstance() throws ClassNotFoundException, SQLException {
        if (con == null) {
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost/book_store";
            Class.forName(myDriver);
            con =DriverManager.getConnection(myUrl, "root", "p@ssw0rD");
        }
        return con;
    }
}
