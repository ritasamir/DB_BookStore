package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class Book {
    private final SimpleStringProperty ISBN = new SimpleStringProperty();
    private final SimpleStringProperty title = new SimpleStringProperty();
    private final SimpleStringProperty publisher = new SimpleStringProperty();
    private final SimpleStringProperty publicationYear = new SimpleStringProperty();
    private final SimpleStringProperty price = new SimpleStringProperty();
    private final SimpleStringProperty quantity = new SimpleStringProperty();
    private final SimpleStringProperty minimumQuantity = new SimpleStringProperty();
    private final SimpleStringProperty category = new SimpleStringProperty();

    public Button add_to_cart;

    public Book(String[] book_Info){
        ISBN.set(book_Info[0]);
        title.set(book_Info[1]);
        publisher.set(book_Info[2]);
        publicationYear.set(book_Info[3]);
        price.set(book_Info[4]);
        category.set(book_Info[5]);
        quantity.set(book_Info[6]);
        minimumQuantity.set(book_Info[7]);
        this.add_to_cart= new Button("+");
    }

public String getPublisher() {
    return publisher.get();
}

    public SimpleStringProperty publisherProperty() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher.set(publisher);
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

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getPublicationYear() {
        return publicationYear.get();
    }

    public SimpleStringProperty publicationYearProperty() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear.set(publicationYear);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
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

    public String getMinimumQuantity() {
        return minimumQuantity.get();
    }

    public SimpleStringProperty minimumQuantityProperty() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(String minimumQuantity) {
        this.minimumQuantity.set(minimumQuantity);
    }
}
