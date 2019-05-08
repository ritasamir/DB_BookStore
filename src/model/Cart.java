package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Cart {
    private static Cart cart = null;
    private HashMap<Book, StringProperty> cartItems;


    public Cart(){
        cartItems = new HashMap<Book,StringProperty>();
    }
    public static Cart getInstance()
    {
        if (cart == null)
            cart = new Cart();

        return cart;
    }
    public HashMap<Book,StringProperty> getCartItems() {
        return cartItems;
    }
    public void addToCart(Book book,String order_quantity){
        cartItems.put(book,new SimpleStringProperty(order_quantity));
    }
    public void setCartItems(HashMap<Book,StringProperty> cartItems) {
        this.cartItems=cartItems;
    }

    public String getTotalPrice() {
        float total=0;
        Set set = cartItems.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            Book keyValue = (Book) mapEntry.getKey();
            StringProperty value = (StringProperty) mapEntry.getValue();
            total+=Float.valueOf(keyValue.getPrice()).floatValue()*Integer.parseInt(value.get());
        }
        return Float.toString(total);
    }

    public boolean insertCreditCard(String username,String cardNo,String date){
        try {
            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost/book_store";
            Class.forName(myDriver);
            Connection con = DriverManager.getConnection(myUrl, "root", "p@ssw0rD");
            Statement statement = con.createStatement();
            String sqlString="INSERT INTO book_store.credit_card " +
                    "SELECT * FROM (SELECT '"+username+"','"+cardNo+"','"+date+"') AS tmp " +
                    "WHERE NOT EXISTS ( SELECT * FROM book_store.credit_card WHERE username = '"+username+"' and cardNo='"
            +cardNo+"' and expiry_date = '"+date+"');";
            System.out.println(sqlString);
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
