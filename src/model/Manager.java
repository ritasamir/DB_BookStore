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
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            st = conn.createStatement();
            startTransaction(st);
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
                rollback(st);
                return "Book added successfully but " + authorStatus;
            }
            commit(st);
            return "Book added successfully";
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return error;
        }
    }


  public static String addPublisher(String[] publisherInfo){
          Statement st = null;
      try {
          java.sql.Connection conn = Connection.getInstance();
          st = conn.createStatement();
          startTransaction(st);
          String query = "insert into book_store." + "publisher" + " values (?, ?, ?)";

          // create the mysql insert preparedstatement
          PreparedStatement preparedStmt = conn.prepareStatement(query);
          preparedStmt.setString(1, publisherInfo[0]); //name
          preparedStmt.setString(2, publisherInfo[1]); //address
          preparedStmt.setString(3, publisherInfo[2]); //phone

          // execute the preparedstatement
          preparedStmt.execute();
          commit(st);
          return "Publisher added successfully";
      } catch (Exception e) {
          rollback(st);
          String error = e.getMessage();
          return error;
      }
  }
    public static String addOrder(String[] orderInfo){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            st = conn.createStatement();
            startTransaction(st);
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
            commit(st);
            return "Order added successfully";
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return error;
        }
    }
    public static String addAuthors(String ISBN,Set<String> authorsNames){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            st = conn.createStatement();
            startTransaction(st);
                for (String author: authorsNames) {
                    String query = "insert into book_store." + "author" + " values (?, ?)";
                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setString(1, ISBN); //ISBN
                    preparedStmt.setString(2, author); //address
                    // execute the preparedstatement
                    preparedStmt.execute();
                }

            commit(st);
            return "Authors added successfully";
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return error;
        }
    }

    public static ObservableList<Book> getBooks(){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            ObservableList<Book> books = FXCollections.observableArrayList();
            st = conn.createStatement();
            startTransaction(st);
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
            commit(st);
            return books;
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return null;
        }
    }

    public static ObservableList<Publisher> getPublishers(){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            ObservableList<Publisher> publishers = FXCollections.observableArrayList();
            st = conn.createStatement();
            startTransaction(st);
            ResultSet rs = st.executeQuery("Select * from publisher");
            while (rs.next()) {
                String name = rs.getString("name");
                String address=  rs.getString("address");
                String phone = rs.getString("phone");
                publishers.add(new Publisher(name,address,phone));
            }
            commit(st);
            return publishers;
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return null;
        }
    }

    public static ObservableList<Author> getAuthors(){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            ObservableList<Author> authors = FXCollections.observableArrayList();
            st = conn.createStatement();
            startTransaction(st);
            ResultSet rs = st.executeQuery("Select * from Author");
            while (rs.next()) {
                String ISBN = rs.getString("ISBN");
                String name=  rs.getString("author_name");
                authors.add(new Author(ISBN,name));
            }
            commit(st);
            return authors;
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return null;
        }
    }

    public static ObservableList<Order> getOrders(){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            ObservableList<Order> orders = FXCollections.observableArrayList();
            st = conn.createStatement();
            startTransaction(st);
            ResultSet rs = st.executeQuery("Select * from book_order");
            while (rs.next()) {
                String id = rs.getString("order_id");
                String ISBN=  rs.getString("ISBN");
                String quantity = rs.getString("quantity");
                String timestamp = rs.getString("timestamp");
                orders.add(new Order(id,ISBN,quantity,timestamp));
            }
            commit(st);
            return orders;
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return null;
        }
    }
    public static ObservableList<History> getHistory(){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            ObservableList<History> history = FXCollections.observableArrayList();
            st = conn.createStatement();
            startTransaction(st);
            ResultSet rs = st.executeQuery("Select * from user_history");
            while (rs.next()) {
                String username = rs.getString("username");
                String id = rs.getString("order_id");
                String ISBN=  rs.getString("ISBN");
                String quantity = rs.getString("quantity");
                String timestamp = rs.getString("timestamp");
                history.add(new History(username,id,ISBN,quantity,timestamp));
            }
            commit(st);
            return history;
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return null;
        }
    }
    public static String deleteOrder(String order_id){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            st = conn.createStatement();
            startTransaction(st);
            st.executeUpdate("DELETE FROM book_Store.book_order WHERE order_id=" + order_id);
            commit(st);
            return "Order deleted successfully";
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return error;
        }
    }

    public static String deleteAuthor(String ISBN,String name){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            st = conn.createStatement();
            startTransaction(st);
            st.executeUpdate("DELETE FROM book_Store.author WHERE ISBN=" + ISBN + " AND author_name =\"" + name +"\"");
            commit(st);
            return "Author deleted successfully";
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return error;
        }
    }
    public static String deletePublisher(String name){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            st = conn.createStatement();
            startTransaction(st);
            st.executeUpdate("DELETE FROM book_Store.publisher WHERE name =\"" + name +"\"");
            commit(st);
            return "Publisher deleted successfully";
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return error;
        }
    }
    public static String updateBooks(String ISBN,String[] book_info){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            st = conn.createStatement();
            startTransaction(st);
            st.executeUpdate("update book set ISBN = \""+book_info[0]+
                    "\", Title = \""+ book_info[1]+"\" , Publisher = \""+ book_info[2]
                    +"\" , Publication_year = \""+ book_info[3]
                    +"\", price = \""+book_info[4]+"\", Category = \""+book_info[5]+
                    "\", Number_Of_Copies = "+book_info[6]+", Threshold = "+book_info[7] +" where ISBN = \""+ISBN+"\"");
            commit(st);
            return "Book updated successfully";
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return error;
        }
    }

    public static String updatePublisher(String name,String[] publisher_info){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            st = conn.createStatement();
            startTransaction(st);
            st.executeUpdate("update publisher set Name = \""+publisher_info[0]+
                    "\", address = \""+ publisher_info[1]+"\", Phone = \""+ publisher_info[2]
                    +"\" where name = \""+name+"\"");
            commit(st);
            return "Publisher updated successfully";
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return error;
        }
    }
    public static String deleteUser(String userName){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            st = conn.createStatement();
            startTransaction(st);
            st.executeUpdate("DELETE FROM book_Store.user WHERE username=\"" + userName +"\"");
            commit(st);
            return "User deleted successfully";
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return error;
        }
    }

    public static String deleteBook(String ISBN){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            st = conn.createStatement();
            startTransaction(st);
            st.executeUpdate("DELETE FROM book_Store.book WHERE ISBN=\"" + ISBN +"\"");
            commit(st);
            return "Book deleted successfully";
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return error;
        }
    }

    public static String changeToManager(String userName){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            st = conn.createStatement();
            startTransaction(st);
            st.executeUpdate("UPDATE user SET is_manager = 1 WHERE username = \"" + userName +"\"");
            commit(st);
            return "customer updated successfully";
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return error;
        }
    }
    public static ObservableList<ObservableList<Users>> getUsers(){
        Statement st = null;
        try {
            java.sql.Connection conn = Connection.getInstance();
            ObservableList<Users> customers = FXCollections.observableArrayList();
            ObservableList<Users> managers = FXCollections.observableArrayList();
            st = conn.createStatement();
            startTransaction(st);
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
            commit(st);
            return users;
        } catch (Exception e) {
            rollback(st);
            String error = e.getMessage();
            return null;
        }
    }

    public static void startTransaction(Statement st){
        try {
            st.executeQuery("start transaction");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void commit(Statement st){
        try {
            st.executeQuery("commit");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void rollback(Statement st){
        try {
            st.executeQuery("rollback");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
