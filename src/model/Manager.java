package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.Date;

public class Manager {
    public static String addBooks(String[] bookInfo,Set<String> authorsNames) {
        try {
            java.sql.Connection conn = Connection.getInstance();
            String query = "insert into book_store." + "book" + " values (?, ?, ?, ?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, bookInfo[0]); //ISBN
            preparedStmt.setString(2, bookInfo[1]); //Title
            preparedStmt.setString(3, bookInfo[2]); //publisher
            preparedStmt.setString(4, bookInfo[3]); //publication year
            preparedStmt.setFloat(5, Float.parseFloat(bookInfo[4])); // price
            preparedStmt.setString(6, bookInfo[5]); // category
            preparedStmt.setInt(7, Integer.parseInt(bookInfo[6])); // number of copies
            preparedStmt.setInt(8, Integer.parseInt(bookInfo[7])); // threshold

            // execute the preparedstatement
            preparedStmt.execute();
            String authorStatus = addAuthors(bookInfo[0],authorsNames);
            if(authorStatus != "Authors added successfully"){
                return "Book added successfully but " + authorStatus;
            }
            return "Book added successfully";
        } catch (Exception e) {
            String error = e.getMessage();
            return error;
        }
    }


  public static String addPublisher(String[] publisherInfo){
      try {
          java.sql.Connection conn = Connection.getInstance();
          String query = "insert into book_store." + "publisher" + " values (?, ?, ?)";

          // create the mysql insert preparedstatement
          PreparedStatement preparedStmt = conn.prepareStatement(query);
          preparedStmt.setString(1, publisherInfo[0]); //name
          preparedStmt.setString(2, publisherInfo[1]); //address
          preparedStmt.setString(3, publisherInfo[2]); //phone

          // execute the preparedstatement
          preparedStmt.execute();

          return "Publisher added successfully";
      } catch (Exception e) {
          String error = e.getMessage();
          return error;
      }
  }
    public static String addOrder(String[] orderInfo){
        try {
            java.sql.Connection conn = Connection.getInstance();
            String query = "insert into book_store." + "book_order(ISBN,quantity,timestamp)" + " values (?, ?, ?)";
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, orderInfo[0]); //name
            preparedStmt.setString(2, orderInfo[1]); //address
            Date date= new Date();
            long time = date.getTime();
            Timestamp ts = new Timestamp(time);
            preparedStmt.setString(3,ts.toString()); //phone
            // execute the preparedstatement
            preparedStmt.execute();
            return "Order added successfully";
        } catch (Exception e) {
            String error = e.getMessage();
            return error;
        }
    }
    public static String addAuthors(String ISBN,Set<String> authorsNames){
        try {
            java.sql.Connection conn = Connection.getInstance();

                for (String author: authorsNames) {
                    String query = "insert into book_store." + "author" + " values (?, ?)";
                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setString(1, ISBN); //ISBN
                    preparedStmt.setString(2, author); //address
                    // execute the preparedstatement
                    preparedStmt.execute();
                }


            return "Authors added successfully";
        } catch (Exception e) {
            String error = e.getMessage();
            return error;
        }
    }

    public static ObservableList<Book> getBooks(){
        try {
            java.sql.Connection conn = Connection.getInstance();
            ObservableList<Book> books = FXCollections.observableArrayList();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from book");
            while (rs.next()) {
                String[] book_info = new String[8];
                /*String ISBN */ book_info[0]= rs.getString("ISBN");
                /*String title*/ book_info[1]=  rs.getString("title");
                /*String publisher*/ book_info[2] = rs.getString("publisher");
                /*String publicationYear*/ book_info[3]=  rs.getString("publication_Year");
                /*float price*/ book_info[4] = rs.getString("price");
                /*String category*/ book_info[5] = rs.getString("Category");
                /*int quantity*/ book_info[6] = rs.getString("Number_Of_Copies");
                /*int minimumQuantity*/ book_info[7] = rs.getString("Threshold");
                books.add(new Book(book_info));
            }
            return books;
        } catch (Exception e) {
            String error = e.getMessage();
            return null;
        }
    }

    public static ObservableList<Publisher> getPublishers(){
        try {
            java.sql.Connection conn = Connection.getInstance();
            ObservableList<Publisher> publishers = FXCollections.observableArrayList();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from publisher");
            while (rs.next()) {
                String name = rs.getString("name");
                String address=  rs.getString("address");
                String phone = rs.getString("phone");
                publishers.add(new Publisher(name,address,phone));
            }
            return publishers;
        } catch (Exception e) {
            String error = e.getMessage();
            return null;
        }
    }

    public static ObservableList<Author> getAuthors(){
        try {
            java.sql.Connection conn = Connection.getInstance();
            ObservableList<Author> authors = FXCollections.observableArrayList();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from Author");
            while (rs.next()) {
                String ISBN = rs.getString("ISBN");
                String name=  rs.getString("author_name");
                authors.add(new Author(ISBN,name));
            }
            return authors;
        } catch (Exception e) {
            String error = e.getMessage();
            return null;
        }
    }

    public static ObservableList<Order> getOrders(){
        try {
            java.sql.Connection conn = Connection.getInstance();
            ObservableList<Order> orders = FXCollections.observableArrayList();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from book_order");
            while (rs.next()) {
                String id = rs.getString("order_id");
                String ISBN=  rs.getString("ISBN");
                String quantity = rs.getString("quantity");
                String timestamp = rs.getString("timestamp");
                orders.add(new Order(id,ISBN,quantity,timestamp));
            }
            return orders;
        } catch (Exception e) {
            String error = e.getMessage();
            return null;
        }
    }

    public static String deleteOrder(String order_id){
        try {
            java.sql.Connection conn = Connection.getInstance();
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM book_Store.book_order WHERE order_id=" + order_id);

            return "Order deleted successfully";
        } catch (Exception e) {
            String error = e.getMessage();
            return error;
        }
    }

    public static String deleteAuthor(String ISBN,String name){
        try {
            java.sql.Connection conn = Connection.getInstance();
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM book_Store.author WHERE ISBN=" + ISBN + " AND author_name =\"" + name +"\"");
            return "Author deleted successfully";
        } catch (Exception e) {
            String error = e.getMessage();
            return error;
        }
    }
    public static String deletePublisher(String name){
        try {
            java.sql.Connection conn = Connection.getInstance();
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM book_Store.publisher WHERE name =\"" + name +"\"");
            return "Publisher deleted successfully";
        } catch (Exception e) {
            String error = e.getMessage();
            return error;
        }
    }
    public static String updateBooks(String ISBN,String[] book_info){
        try {
            java.sql.Connection conn = Connection.getInstance();
            Statement st = conn.createStatement();
            st.executeUpdate("update book set ISBN = \""+book_info[0]+
                    "\", Title = \""+ book_info[1]+"\" , Publisher = \""+ book_info[2]
                    +"\" , Publication_year = \""+ book_info[3]
                    +"\", price = \""+book_info[4]+"\", Category = \""+book_info[5]+
                    "\", Number_Of_Copies = "+book_info[6]+", Threshold = "+book_info[7] +" where ISBN = \""+ISBN+"\"");
            return "Book updated successfully";
        } catch (Exception e) {
            String error = e.getMessage();
            return error;
        }
    }

    public static String updatePublisher(String name,String[] publisher_info){
        try {
            java.sql.Connection conn = Connection.getInstance();
            Statement st = conn.createStatement();
            st.executeUpdate("update publisher set Name = \""+publisher_info[0]+
                    "\", address = \""+ publisher_info[1]+"\", Phone = \""+ publisher_info[2]
                    +"\" where name = \""+name+"\"");
            return "Publisher updated successfully";
        } catch (Exception e) {
            String error = e.getMessage();
            return error;
        }
    }
    public static String deleteUser(String userName){
        try {
            java.sql.Connection conn = Connection.getInstance();
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM book_Store.user WHERE username=\"" + userName +"\"");
            return "User deleted successfully";
        } catch (Exception e) {
            String error = e.getMessage();
            return error;
        }
    }

    public static String deleteBook(String ISBN){
        try {
            java.sql.Connection conn = Connection.getInstance();
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM book_Store.book WHERE ISBN=\"" + ISBN +"\"");
            return "Book deleted successfully";
        } catch (Exception e) {
            String error = e.getMessage();
            return error;
        }
    }

    public static String changeToManager(String userName){
        try {
            java.sql.Connection conn = Connection.getInstance();
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE user SET is_manager = 1 WHERE username = \"" + userName +"\"");
            return "customer updated successfully";
        } catch (Exception e) {
            String error = e.getMessage();
            return error;
        }
    }
    public static ObservableList<ObservableList<Users>> getUsers(){
        try {
            java.sql.Connection conn = Connection.getInstance();
            ObservableList<Users> customers = FXCollections.observableArrayList();
            ObservableList<Users> managers = FXCollections.observableArrayList();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from user");

            while (rs.next()) {
                String[] user_Info = new String[7];
                user_Info[0] = rs.getString("username");
                user_Info[2] = rs.getString("first_name");
                user_Info[3] = rs.getString("last_name");
                user_Info[1] = rs.getString("email");
                user_Info[4] = rs.getString("phone");
                user_Info[5] = rs.getString("address");
                user_Info[6] = rs.getString("is_manager");
                if(user_Info[6].equals("0")){
                    customers.add(new Users(user_Info));
                }else{
                    managers.add(new Users(user_Info));
                }
            }
            ObservableList<ObservableList<Users>> users = FXCollections.observableArrayList();
            users.add(customers);
            users.add(managers);
            return users;
        } catch (Exception e) {
            String error = e.getMessage();
            return null;
        }
    }
}
