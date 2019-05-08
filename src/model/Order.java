package model;

import javafx.beans.property.SimpleStringProperty;

public class Order {
    private final SimpleStringProperty id = new SimpleStringProperty();
    private final SimpleStringProperty ISBN = new SimpleStringProperty();
    private final SimpleStringProperty quantity = new SimpleStringProperty();
    private final SimpleStringProperty timeStamp = new SimpleStringProperty();

    public Order(String id,String ISBN,String quantity,String timeStamp){
        this.id.set(id);
        this.ISBN.set(ISBN);
        this.quantity.set(quantity);
        this.timeStamp.set(timeStamp);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getISBN() {
        return ISBN.get();
    }

    public SimpleStringProperty ISBNProperty() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN.set(ISBN);
    }

    public String getQuantity() {
        return quantity.get();
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public String getTimeStamp() {
        return timeStamp.get();
    }

    public SimpleStringProperty timeStampProperty() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp.set(timeStamp);
    }
}
