package control;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Book;
import model.Cart;
import sample.Controller;
import javafx.util.Callback;
import model.CartBtnCell;

import java.util.HashMap;

public class UserViewController  {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, String> ISBNColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> publisherColumn;
    @FXML
    private TableColumn<Book, String> categoryColumn;
    @FXML
    private TableColumn<Book, String> priceColumn;


    Controller control;
    String[][] books;


    private ObservableList<Book> masterData = FXCollections.observableArrayList();
    public UserViewController() {
        control = new Controller();
        books=control.search("book","null","null");
        for(int i=0;i<books.length;i++){
            masterData.add(new Book(books[i][0], books[i][1], books[i][2], books[i][3], books[i][4], books[i][5], books[i][6],
                    books[i][7]));
        }
    }

    @FXML
    private void initialize() {
        // 0. Initialize the columns.
        ISBNColumn.setCellValueFactory(cellData -> cellData.getValue().ISBN);
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().bookTitle);
        publisherColumn.setCellValueFactory(cellData -> cellData.getValue().publisher);
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().category);
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().price);
        TableColumn col_action = new TableColumn<>("Action");
        col_action.setSortable(false);
        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Book, Boolean>,
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Book, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });
        col_action.setCellFactory(
                new Callback<TableColumn<Book, Boolean>, TableCell<Book, Boolean>>() {

                    @Override
                    public TableCell<Book, Boolean> call(TableColumn<Book, Boolean> p) {
                        return new CartBtnCell(bookTable);
                    }

                });
        bookTable.getColumns().add(col_action);
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Book> filteredData = new FilteredList<>(masterData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (book.bookTitle.get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (book.category.get().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (book.ISBN.get().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else if(book.publisher.get().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(book.price.get().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(book.publicationYear.get().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Book> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(bookTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        bookTable.setItems(sortedData);
    }
    public void logOut(ActionEvent event)throws Exception{
        Cart cart = Cart.getInstance();
        cart.setCartItems(new HashMap<Book, StringProperty>());
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/login.fxml")));
        stage.setScene(scene);
        stage.show();

    }

    public void editProfile(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/editProfile.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    public void viewCart(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/cart.fxml")));
        stage.setScene(scene);
        stage.show();
    }
}