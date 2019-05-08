package control;

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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Book;
import model.Manager;
import model.Publisher;

public class PublishersController {
    @FXML
    private TextField filterField;
    @FXML
    private TableView<Publisher> tableView;
    @FXML
    private TableColumn<Publisher,String> nameCol;
    @FXML
    private TableColumn<Publisher,String> addressCol;
    @FXML
    private TableColumn<Publisher,String> phoneCol;
    private ObservableList<Publisher> publishers;

    public PublishersController(){
        publishers = FXCollections.observableArrayList();

    }
    /* Change to Add Publisher Window */
    public void addPublisherWindow(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/AddPublishers.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public void deletePublisher(ActionEvent event)throws Exception{
        Publisher selectedPublisher = tableView.getSelectionModel().getSelectedItem();
        String result = Manager.deletePublisher(selectedPublisher.getName());
        if(result.equals("Publisher deleted successfully")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Publisher deleted successfully");
            alert.showAndWait();
            initialize();
        }else{
            showErrorAlert(result);
        }
    }
    @FXML
    private void initialize() {
        // 0. Initialize the columns.
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        addressCol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        phoneCol.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        publishers = Manager.getPublishers();

        FilteredList<Publisher> filteredData = new FilteredList<>(publishers, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(publisher -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (publisher.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (publisher.getAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }else if (publisher.getPhone().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Publisher> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableView.setItems(sortedData);
    }
    public void modifyPublisher(ActionEvent event)throws Exception{
        Publisher selectedPublisher = tableView.getSelectionModel().getSelectedItem();
        if(selectedPublisher== null){
            showErrorAlert("Please choose a publisher");
        }else {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ModifyPublisher.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            ModifyPublisherController controller = fxmlLoader.<ModifyPublisherController>getController();
            controller.modifyPublisher(selectedPublisher);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    /* Go back to Add New Books Window */
    public void back(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/ManagerView.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    /* Shows error alert */
    private void showErrorAlert(String alertText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(alertText);
        alert.showAndWait();
    }
}
