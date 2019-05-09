package control;

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
import model.*;

public class UsersController {
    @FXML
    private TextField filteremailc;
    @FXML
    private TextField filterusernamec;
    @FXML
    private TextField filterfirstnamec;
    @FXML
    private TextField filtersecondnamec;
    @FXML
    private TableView<Users> usersTable;
    @FXML
    private TableColumn<Users,String> usernameccol;
    @FXML
    private TableColumn<Users,String> emailccol;
    @FXML
    private TableColumn<Users,String> firstccol;
    @FXML
    private TableColumn<Users,String> secondccol;
    @FXML
    private TableColumn<Users,String> phoneccol;
    @FXML
    private TableColumn<Users,String> addressccol;
    @FXML
    private TextField filteremailm;
    @FXML
    private TextField filterusernamem;
    @FXML
    private TextField filterfirstnamem;
    @FXML
    private TextField filtersecondnamem;
    @FXML
    private TableView<Users> managersTable;
    @FXML
    private TableColumn<Users,String> usernamemcol;
    @FXML
    private TableColumn<Users,String> emailmcol;
    @FXML
    private TableColumn<Users,String> firstmcol;
    @FXML
    private TableColumn<Users,String> secondmcol;
    @FXML
    private TableColumn<Users,String> phonemcol;
    @FXML
    private TableColumn<Users,String> addressmcol;
    private ObservableList<Users> managers;
    private ObservableList<Users> customers;
    public UsersController(){
        managers = FXCollections.observableArrayList();
        customers = FXCollections.observableArrayList();
    }
    @FXML
    private void initialize() {
        // 0. Initialize the columns.
        usernameccol.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        emailccol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        firstccol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        secondccol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        phoneccol.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        addressccol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        usernamemcol.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        emailmcol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        firstmcol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        secondmcol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        phonemcol.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        addressmcol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        ObservableList<ObservableList<Users>> users = Manager.getUsers();

        FilteredList<Users> filteredDatac = new FilteredList<>(users.get(0), p -> true);
        FilteredList<Users> filteredDatam = new FilteredList<>(users.get(1), p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        filteremailc.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDatac.setPredicate(customer -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (customer.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        filterusernamec.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDatac.setPredicate(customer -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (customer.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        filterfirstnamec.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDatac.setPredicate(customer -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (customer.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        filtersecondnamec.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDatac.setPredicate(customer -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (customer.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        filteremailm.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDatam.setPredicate(manager -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (manager.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        filterusernamem.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDatam.setPredicate(manager -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (manager.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        filterfirstnamem.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDatam.setPredicate(manager -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (manager.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        filtersecondnamem.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDatam.setPredicate(manager -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (manager.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Users> sortedDatac = new SortedList<>(filteredDatac);
        SortedList<Users> sortedDatam = new SortedList<>(filteredDatam);
        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedDatac.comparatorProperty().bind(usersTable.comparatorProperty());
        sortedDatam.comparatorProperty().bind(managersTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        usersTable.setItems(sortedDatac);
        managersTable.setItems(sortedDatam);
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

    public void changeCustomer(ActionEvent event)throws Exception{
        Users selectedOrder = usersTable.getSelectionModel().getSelectedItem();
        String result = Manager.changeToManager(selectedOrder.getUsername());
        if(result.equals("customer updated successfully")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Customer updated successfully");
            String style = getClass().getResource("/sample//sample.css").toExternalForm();
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().addAll(style);
            alert.showAndWait();
            initialize();
        }else{
            showErrorAlert(result);
        }
    }

    public void deleteManager(ActionEvent event)throws Exception{
        Users selectedOrder = managersTable.getSelectionModel().getSelectedItem();
        String result = Manager.deleteUser(selectedOrder.getUsername());
        if(result.equals("User deleted successfully")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("User deleted successfully");
            String style = getClass().getResource("/sample//sample.css").toExternalForm();
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().addAll(style);
            alert.showAndWait();
            initialize();
        }else{
            showErrorAlert(result);
        }
    }
    public void deleteUser(ActionEvent event)throws Exception{
        Users selectedOrder = usersTable.getSelectionModel().getSelectedItem();
        String result = Manager.deleteUser(selectedOrder.getUsername());
        if(result.equals("User deleted successfully")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("User deleted successfully");
            String style = getClass().getResource("/sample//sample.css").toExternalForm();
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().addAll(style);
            alert.showAndWait();
            initialize();
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


}
