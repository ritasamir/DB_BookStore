package model;

import b.j.B;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class Book {
    public StringProperty  ISBN;
    public StringProperty bookTitle;
    public StringProperty category;
    public StringProperty price;
    public StringProperty publisher;
    public StringProperty threshold;
    public StringProperty numberOfCopies;
    public StringProperty publicationYear;
    public Button add_to_cart;


    public Book(String ISBN, String bookTitle, String publisher, String publicationYear, String price,
                String category, String numberOfCopies, String threshold) {
        this.ISBN=new SimpleStringProperty(ISBN);
        this.bookTitle=new SimpleStringProperty(bookTitle);
        this.category=new SimpleStringProperty(category);
        this.publisher=new SimpleStringProperty(publisher);
        this.price=new SimpleStringProperty(price);
        this.publicationYear=new SimpleStringProperty(publicationYear);
        this.threshold=new SimpleStringProperty(threshold);
        this.numberOfCopies=new SimpleStringProperty(numberOfCopies);
        this.add_to_cart= new Button("+");
    }
}
