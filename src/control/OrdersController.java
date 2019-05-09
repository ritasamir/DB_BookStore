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
import model.Manager;
import model.Order;


public class OrdersController {
    @FXML
    private TextField filterFieldQuantity;
    @FXML
    private TextField filterFieldID;
    @FXML
    private TextField filterFieldISBN;
    @FXML
    private TableView<Order> tableView;
    @FXML
    private TableColumn<Order, String> idCol;
    @FXML
    private TableColumn<Order, String> ISBNCol;
    @FXML
    private TableColumn<Order, String> quantityCol;
    @FXML
    private TableColumn<Order, String> timeStampCol;
    private ObservableList<Order> orders;

    public OrdersController() {
        orders = FXCollections.observableArrayList();

    }

    @FXML
    private void initialize() {
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        ISBNCol.setCellValueFactory(cellData -> cellData.getValue().ISBNProperty());
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        timeStampCol.setCellValueFactory(cellData -> cellData.getValue().timeStampProperty());
        orders = Manager.getOrders();
        FilteredList<Order> filteredData = new FilteredList<>(orders, p -> true);
        filterFieldID.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(order -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (order.getId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        filterFieldISBN.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(order -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (order.getISBN().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        filterFieldQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(order -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (order.getQuantity().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Order> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
    public void addOrder(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/AddOrders.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    public void confirmOrder(ActionEvent event)throws Exception{
        Order selectedOrder = tableView.getSelectionModel().getSelectedItem();
        String result = Manager.deleteOrder(selectedOrder.getId());
        if(result.equals("Order deleted successfully")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Order Confirmed successfully");
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

    /* Go back to Add New Books Window */
    public void back(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/ManagerView.fxml")));
        stage.setScene(scene);
        stage.show();
    }
}
