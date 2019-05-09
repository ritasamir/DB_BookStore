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
        Statement statement = null;
        try {
            java.sql.Connection con = model.Connection.getInstance();
            statement = con.createStatement();
            startTransaction(statement);
            String sqlString="INSERT INTO book_store.credit_card " +
                    "SELECT * FROM (SELECT '"+username+"','"+cardNo+"','"+date+"') AS tmp " +
                    "WHERE NOT EXISTS ( SELECT * FROM book_store.credit_card WHERE username = '"+username+"' and cardNo='"
            +cardNo+"' and expiry_date = '"+date+"');";
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
    public HashMap<Book, StringProperty> compact(HashMap<Book, StringProperty> items){
        HashMap<Book, StringProperty> comp = new HashMap<Book, StringProperty>();
        for (Map.Entry<Book, StringProperty> entry : items.entrySet()) {
            Map.Entry<Book, StringProperty> en =contain(entry,comp);
            if(en != null){
                comp.replace(en.getKey(),comp.get(en.getKey()),new SimpleStringProperty(Integer.toString(Integer.parseInt(comp.get(en.getKey()).get())+
                        Integer.parseInt(entry.getValue().get()))));
            }else{
                comp.put(entry.getKey(),entry.getValue());
            }
        }
        return comp;
    }
    private Map.Entry<Book, StringProperty> contain(Map.Entry<Book, StringProperty> entry,HashMap<Book, StringProperty> comp){
        for (Map.Entry<Book, StringProperty> en : comp.entrySet()){
            if(en.getKey().getISBN().equals(entry.getKey().getISBN())){
                return en;
            }
        }
        return null;
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
