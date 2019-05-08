package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Book;
import model.Manager;

import java.util.HashSet;
import java.util.Set;

public class ModifyBooksController {
    /* array conatins all book information needed for insertion in book table */
    private String[] bookInformation;
    @FXML /* Category Menu contents */
    private MenuButton category;
    @FXML /* Title text Field contents */
    private TextField bookTitle;
    @FXML /* ISBN text Field contents */
    private TextField ISBN;
    @FXML /* Publisher text Field contents */
    private TextField publisherName;
    @FXML /* Publication year text Field contents */
    private TextField publicationYear;
    @FXML /* Price text Field contents */
    private TextField price;
    @FXML /* Number of copies text Field contents */
    private TextField numberOFCopies;
    @FXML /* Minimum Number of copies text Field contents */
    private TextField threshold;
    /* Selected Category */
    String old_ISBN;

    private String selectedItem;
    public ModifyBooksController(){
        bookInformation = new String[8];
        selectedItem = "";
    }

    /* Add Books Button Handler */
    public void submit(ActionEvent event)throws Exception{
        if(!validate()){
            return;
        }else {
            String checkFK = "foreign key";
            String result = Manager.updateBooks(old_ISBN,bookInformation);
            if(result.indexOf(checkFK) != -1){
                showErrorAlert("Publisher information doesn't exist on the system.. please add it first");
            }else if (result.equals("Book updated successfully")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Book updated successfully");
                alert.showAndWait();
            }else {
                showErrorAlert(result);
            }
        }
    }

    public void modifyBooks(Book book){
        old_ISBN = book.getISBN();
        ISBN.setText(book.getISBN());
        publisherName.setText(book.getPublisher());
        category.setText(book.getCategory());
        selectedItem = book.getCategory();
        numberOFCopies.setText(book.getQuantity());
        threshold.setText(book.getMinimumQuantity());
        bookTitle.setText(book.getTitle());
        price.setText(book.getPrice());
        publicationYear.setText(book.getPublicationYear());
    }

    /* Go back to Manager Window */
    public void back(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Books.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    /* Check that all text fields are filled and store information */
    private boolean validate() {
        if(ISBN.getText().isEmpty()) {
            showErrorAlert("Please enter book ISBN");
            return false;
        }else if(bookTitle.getText().isEmpty()){
            showErrorAlert("Please enter book title");
            return false;
        }else if (publisherName.getText().isEmpty()){
            showErrorAlert("Please enter publisher name");
            return false;
        }else if(publicationYear.getText().isEmpty()){
            showErrorAlert("Please enter publication year");
            return false;
        }else if(price.getText().isEmpty()){
            showErrorAlert("Please enter the price");
            return false;
        }else if (selectedItem.isEmpty()){
            showErrorAlert("Please choose the category");
            return false;
        }else if(numberOFCopies.getText().isEmpty()){
            showErrorAlert("Please enter number of copies");
            return false;
        }else if(threshold.getText().isEmpty()){
            showErrorAlert("Please enter minimum number of copies");
            return false;
        }
        else{
            bookInformation[0] = ISBN.getText();
            bookInformation[1]=bookTitle.getText();
            bookInformation[2]=publisherName.getText();
            bookInformation[3]=publicationYear.getText();
            bookInformation[4]=price.getText();
            bookInformation[5] = selectedItem;
            bookInformation[6]=numberOFCopies.getText();
            bookInformation[7]=threshold.getText();
        }
        return true;
    }
    /* Shows error alert */
    private void showErrorAlert(String alertText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(alertText);
        alert.showAndWait();
    }
    /* Set Selected Item Handler for Art Menu Item */
    public void art(ActionEvent event){
        selectedItem = "art";
        category.setText("Art");
    }

    /* Set Selected Item Handler for Science Menu Item */
    public void science(ActionEvent event){
        selectedItem ="science";
        category.setText("Science");
    }

    /* Set Selected Item Handler for Religion Menu Item */
    public void religion(ActionEvent event){
        selectedItem = "religion";
        category.setText("Religion");
    }

    /* Set Selected Item Handler for Geography Menu Item */
    public void geography(ActionEvent event){
        selectedItem = "geography";
        category.setText("Geography");
    }

    /* Set Selected Item Handler for History Menu Item */
    public void history(ActionEvent event){
        selectedItem = "history";
        category.setText("History");
    }
}
