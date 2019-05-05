package model;

import java.sql.*;

public class User {
    public boolean addNewCustomer(String[] userInfo){
        try {
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost/book_store";
            Class.forName(myDriver);
            Connection con = DriverManager.getConnection(myUrl, "root", "p@ssw0rD");
            Statement statement = con.createStatement();
            String sqlString="INSERT INTO book_store.User VALUES ('"+userInfo[0]+"' , '"+userInfo[1]+"' , '"+userInfo[2]+
                    "' , '"+userInfo[3]+"' , '"+userInfo[4]+"' , '"+userInfo[5]+"' , '"+userInfo[6]+ "' , FALSE );";
            statement.executeUpdate(sqlString);
            statement.close();
            con.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean logIn(String userName,String password) {
        try {
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost/book_store";
            Class.forName(myDriver);
            Connection con = DriverManager.getConnection(myUrl, "root", "p@ssw0rD");
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM user Where username = ? and password = ?";
            PreparedStatement  preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                System.err.println("Wrong Logins --///");
                return false;

            } else {
                System.out.println("Successfull Login");
                return true;
            }

        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            return false;
        }


    }
}
