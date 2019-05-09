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
        Statement st = null;
        try {
            java.sql.Connection con = Connection.getInstance();
            st = con.createStatement();
            startTransaction(st);
            String sqlCommand ="SELECT * FROM book_store.user where username = '"+userName+"';";
            System.out.println(sqlCommand);
            ResultSet rs = st.executeQuery(sqlCommand);
            while (rs.next()) {
                for(int j=0;j<8;j++) {
                    userInfo[j] = rs.getString(j + 1);
                    System.out.println(userInfo[j]);
                }
            }
            commit(st);
            st.close();
            rs.close();
        } catch (Exception ex) {
            rollback(st);
            ex.printStackTrace();

        }
        return userInfo;
    }

    public String addNewCustomer(String[] userInfo){
        Statement statement = null;
        try {
            java.sql.Connection con = Connection.getInstance();
            statement = con.createStatement();
            startTransaction(statement);
            String sqlString="INSERT INTO book_store.User VALUES ('"+userInfo[0]+"' , '"+userInfo[1]+"' , '"+userInfo[2]+
                    "' , '"+userInfo[3]+"' , '"+userInfo[4]+"' , '"+userInfo[5]+"' , '"+userInfo[6]+ "' , FALSE );";
            statement.executeUpdate(sqlString);
            commit(statement);
            statement.close();
            return "done";
        } catch (Exception ex) {
            rollback(statement);
            String error = ex.getMessage();
            return error;
        }
    }

    public boolean logIn(String userName,String password) {
        Statement statement = null;
        try {
            java.sql.Connection con = Connection.getInstance();
            statement = con.createStatement();
            startTransaction(statement);
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
            rollback(statement);
            System.err.println(ex.getMessage());
            return false;
        }


    }

    public boolean updateProfile(String[] userInfo) {
        Statement statement = null;
        try {
            java.sql.Connection con = Connection.getInstance();
            statement = con.createStatement();
            startTransaction(statement);
            String sqlString="UPDATE book_store.User set username='"+userInfo[0]+"' ,first_name= '"+userInfo[1]+"' ,last_name= '"+userInfo[2]+
                    "' ,email= '"+userInfo[3]+"' ,phone= '"+userInfo[4]+"' ,address= '"+userInfo[5]+"',password= '"+userInfo[6]+"' where username='"+userName+"';";
            System.out.println(sqlString);
            statement.executeUpdate(sqlString);
            commit(statement);
            statement.close();
            return true;
        } catch (Exception ex) {
            rollback(statement);
            ex.printStackTrace();
            return false;
        }
    }
    public void startTransaction(Statement st){
        try {
            st.executeQuery("start transaction");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void commit(Statement st){
        try {
            st.executeQuery("commit");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void rollback(Statement st){
        try {
            st.executeQuery("rollback");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
