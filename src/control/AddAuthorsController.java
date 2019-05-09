package control;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Manager;
import java.util.HashSet;
import java.util.Set;

public class AddAuthorsController {

    @FXML /* ISBN TextField Contents */
    private TextField ISBN;
    /* Names Of Authors */
    private Set<String> author;
    @FXML /* Authors listView Contents */
    private ListView listview;
    @FXML /* author name TextField Contents */
    private TextField authorName;

    /* Constructor */
    public AddAuthorsController(){
        author = new HashSet<String>();
    }

    /* add author name to the list view and the authors set */
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

    /* delete author name to the list view and the authors set */
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

    /* Handler for add Author button */
    public void submitAuthor(ActionEvent event)throws Exception{
        if(ISBN.getText().isEmpty()) {
            showErrorAlert("Please enter book ISBN");
        }else if(author.size() == 0){
            showErrorAlert("Please enter at least one author name");
        }else{
            String result = Manager.addAuthors(ISBN.getText(),author);
            if(result.equals("Authors added successfully")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Authors added successfully");
                alert.showAndWait();
            }else{
                showErrorAlert(result);
            }
        }
    }

    /* Go back to add new book Window */
    public void back(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Books.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    /* Shows error alert */
    public void showErrorAlert(String alertText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(alertText);
        String style = getClass().getResource("/sample//sample.css").toExternalForm();
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().addAll(style);
        alert.showAndWait();
    }

}
