package model;

import java.sql.*;

public class User {
    public String userName;
    private static User user = null;
    public static User getInstance()
    {
        if (user == null)
            user = new User();

        return user;
    }
    public String[]getUserInfo(){
        String[] userInfo = new String[8];
        try {
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost/book_store";
            Class.forName(myDriver);
            Connection con = DriverManager.getConnection(myUrl, "root", "p@ssw0rD");
            Statement st = con.createStatement();
            String sqlCommand ="SELECT * FROM book_store.user where username = '"+userName+"';";
            ResultSet rs = st.executeQuery(sqlCommand);
            while (rs.next()) {
                for(int j=0;j<8;j++) {
                    userInfo[j] = rs.getString(j + 1);
                    System.out.println(userInfo[j]);
                }
            }
            st.close();
            rs.close();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return userInfo;
    }

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

    public boolean updateProfile(String[] userInfo) {
        try {
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost/book_store";
            Class.forName(myDriver);
            Connection con = DriverManager.getConnection(myUrl, "root", "p@ssw0rD");
            Statement statement = con.createStatement();
            String sqlString="UPDATE book_store.User set username='"+userInfo[0]+"' ,first_name= '"+userInfo[1]+"' ,last_name= '"+userInfo[2]+
                    "' ,email= '"+userInfo[3]+"' ,phone= '"+userInfo[4]+"' ,address= '"+userInfo[5]+"' where username='"+userName+"';";
            statement.executeUpdate(sqlString);
            statement.close();
            con.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
