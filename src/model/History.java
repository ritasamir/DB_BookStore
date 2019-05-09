package model;

import javafx.beans.property.SimpleStringProperty;

public class History {
    private final SimpleStringProperty id = new SimpleStringProperty();
    private final SimpleStringProperty ISBN = new SimpleStringProperty();
    private final SimpleStringProperty quantity = new SimpleStringProperty();
    private final SimpleStringProperty timeStamp = new SimpleStringProperty();
    private final SimpleStringProperty username = new SimpleStringProperty();

    public History(String username,String id,String ISBN,String quantity,String timeStamp){
        this.id.set(id);
        this.ISBN.set(ISBN);
        this.quantity.set(quantity);
        this.timeStamp.set(timeStamp);
        this.username.set(username);
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

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }
}
