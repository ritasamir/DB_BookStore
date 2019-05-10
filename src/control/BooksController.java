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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;

import java.io.IOException;
import java.util.HashMap;


public class BooksController {
    @FXML
    private TableView<Author> authorstable;
    @FXML
    private TableColumn<Author,String> ISBNaCol;
    @FXML
    private TableColumn<Author,String> authorCol;
    @FXML
    private TextField ISBNaFilter;
    @FXML
    private TextField authorFilter;
    @FXML
    private TextField filterField;
    @FXML
    private TextField filterTitle;
    @FXML
    private TextField filterPublisher;
    @FXML
    private TextField filterCategory;
    @FXML
    private TableView<Book> tableViewBooks;
    @FXML
    private TableColumn<Book,String> ISBNCol;
    @FXML
    private TableColumn<Book,String> titleCol;
    @FXML
    private TableColumn<Book,String> publisherCol;
    @FXML
    private TableColumn<Book,String> yearCol;
    @FXML
    private TableColumn<Book,String> categoryCol;
    @FXML
    private TableColumn<Book,String> priceCol;
    @FXML
    private TableColumn<Book,String> quantityCol;
    @FXML
    private TableColumn<Book,String> minQuantityCol;
    private ObservableList<Book> books;
    private ObservableList<Author> authors;

    public BooksController(){
        books = FXCollections.observableArrayList();
        authors = FXCollections.observableArrayList();

    }

    @FXML
    private void initialize() {
        // 0. Initialize the columns.
        ISBNCol.setCellValueFactory(cellData -> cellData.getValue().ISBNProperty());
        titleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        publisherCol.setCellValueFactory(cellData -> cellData.getValue().publisherProperty());
        yearCol.setCellValueFactory(cellData -> cellData.getValue().publicationYearProperty());
        categoryCol.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        minQuantityCol.setCellValueFactory(cellData -> cellData.getValue().minimumQuantityProperty());
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
                        return new CartBtnCell(tableViewBooks);
                    }

                });
        tableViewBooks.getColumns().add(col_action);
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        setTable();

    }
    public void addNewBooks(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/AddBooks.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    /* Change to Add Authors Window */
    public void addAuthorWindow(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/AddAuthors.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public void deleteBook(ActionEvent event)throws Exception{
        Book selectedBook = tableViewBooks.getSelectionModel().getSelectedItem();
        String result = Manager.deleteBook(selectedBook.getISBN());
        if(result.equals("Book deleted successfully")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Book deleted successfully");
            String style = getClass().getResource("/sample//sample.css").toExternalForm();
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().addAll(style);
            alert.showAndWait();
            setTable();
        }else{
            showErrorAlert(result);
        }
    }
    public void modifyBook(ActionEvent event)throws Exception{
        Book selectedBook = tableViewBooks.getSelectionModel().getSelectedItem();
        if(selectedBook== null){
            showErrorAlert("Please choose a book");
        }else {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ModifyBooks.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            ModifyBooksController controller = fxmlLoader.<ModifyBooksController>getController();
            controller.modifyBooks(selectedBook);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    public void deleteAuthor(ActionEvent event)throws Exception{
        Author selectedAuthor = authorstable.getSelectionModel().getSelectedItem();
        String result = Manager.deleteAuthor(selectedAuthor.getISBN(),selectedAuthor.getName());
        if(result.equals("Author deleted successfully")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Author deleted successfully");
            String style = getClass().getResource("/sample//sample.css").toExternalForm();
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().addAll(style);
            alert.showAndWait();
            setTable();
        }else{
            showErrorAlert(result);
        }
    }
    /* Shows error alert */
    private void showErrorAlert(String alertText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(alertText);
        String style = getClass().getResource("/sample//sample.css").toExternalForm();
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().addAll(style);
        alert.showAndWait();
    }
    public void logOut(ActionEvent event) throws IOException {
        Cart cart = Cart.getInstance();
        cart.setCartItems(new HashMap<Book, StringProperty>());
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/login.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public void editProfile(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/editProfile.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public void viewCart(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/cart.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public void setTable(){
        books = Manager.getBooks();
        FilteredList<Book> filteredData = new FilteredList<>(books, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (book.getISBN().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        filterTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (book.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        filterPublisher.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (book.getPublisher().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        filterCategory.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (book.getCategory().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Book> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tableViewBooks.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableViewBooks.setItems(sortedData);


        ISBNaCol.setCellValueFactory(cellData -> cellData.getValue().ISBNProperty());
        authorCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        authors = Manager.getAuthors();

        FilteredList<Author> filteredauthorsData = new FilteredList<>(authors, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        ISBNaFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredauthorsData.setPredicate(author -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (author.getISBN().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        authorFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredauthorsData.setPredicate(author -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (author.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Author> sortedAuthorData = new SortedList<>(filteredauthorsData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedAuthorData.comparatorProperty().bind(authorstable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        authorstable.setItems(sortedAuthorData);
    }
}

