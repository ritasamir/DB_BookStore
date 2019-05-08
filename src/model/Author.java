package model;

import javafx.beans.property.SimpleStringProperty;

public class Author {
    private final SimpleStringProperty ISBN = new SimpleStringProperty();
    private final SimpleStringProperty name = new SimpleStringProperty();

    public Author(String ISBN,String name){
        this.ISBN.set(ISBN);
        this.name.set(name);
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
