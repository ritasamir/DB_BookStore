package control;
import model.Book;
import model.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class AddBooksController {

    /* array conatins all book information needed for insertion in book table */
    private String[] bookInformation;
    /* Set conatins author names */
    private Set<String> author;
    @FXML /* Authors list view contents */
    private ListView listview;
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
    @FXML /* Author text Field contents */
    private TextField authorName;
    @FXML /* Price text Field contents */
    private TextField price;
    @FXML /* Number of copies text Field contents */
    private TextField numberOFCopies;
    @FXML /* Minimum Number of copies text Field contents */
    private TextField threshold;
    /* Selected Category */
    private String selectedItem;


    /* constructor */
    public AddBooksController(){
        bookInformation = new String[8];
        author = new HashSet<String>();
        selectedItem = "";
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

    /* Add Books Button Handler */
    public void submit(ActionEvent event)throws Exception{
        if(!validate()){
            return;
        }else {
            String checkFK = "foreign key";
            String result = Manager.addBooks(bookInformation,author);
            if(result.indexOf(checkFK) != -1){
                showErrorAlert("Publisher information doesn't exist on the system.. please add it first");
            }else if (result.equals("Book added successfully")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Book added successfully");
                String style = getClass().getResource("/sample//sample.css").toExternalForm();
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().addAll(style);
                alert.showAndWait();
            }else {
                showErrorAlert(result);
            }
        }
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
        }else if (author.size() == 0){
            showErrorAlert("Please enter at least one author name");
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

    /* Add author to authors list view and authors set */
    public void addAuthor(){
        if(authorName.getText().isEmpty()) {
            showErrorAlert("Author name is empty");
        }else if(author.contains(authorName.getText())) {
            showErrorAlert("Author already entered");
        }else {
            String authorNewName = authorName.getText();
            author.add(authorNewName);
            listview.getItems().add(authorNewName);
        }
    }

    /* delete author to authors list view and authors set */
    public void deleteAuthor(){
        String selectedItem;
        try {
            selectedItem =listview.getSelectionModel().getSelectedItem().toString();
            author.remove(selectedItem);
            listview.getItems().remove(selectedItem);
        }catch(Exception exception){
            showErrorAlert("No author Selected");
        }
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
}
