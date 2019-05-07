package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kotlin.Pair;

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
            total+=Float.valueOf(keyValue.price.get()).floatValue()*Integer.parseInt(value.get());
        }
        return Float.toString(total);
    }
}
