package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.History;
import model.Manager;


public class HistoryController {
    @FXML
    private TextField filterFieldQuantity;
    @FXML
    private TextField filterFieldID;
    @FXML
    private TextField filterFieldISBN;
    @FXML
    private TableView<History> tableView;
    @FXML
    private TableColumn<History, String> userCol;
    @FXML
    private TableColumn<History, String> idCol;
    @FXML
    private TableColumn<History, String> ISBNCol;
    @FXML
    private TableColumn<History, String> quantityCol;
    @FXML
    private TableColumn<History, String> timeStampCol;
    private ObservableList<History> history_Info;

    public HistoryController() {
        history_Info = FXCollections.observableArrayList();

    }

    @FXML
    private void initialize() {
        userCol.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        ISBNCol.setCellValueFactory(cellData -> cellData.getValue().ISBNProperty());
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        timeStampCol.setCellValueFactory(cellData -> cellData.getValue().timeStampProperty());
        history_Info = Manager.getHistory();
        FilteredList<History> filteredData = new FilteredList<>(history_Info, p -> true);
        filterFieldID.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(history -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (history.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        filterFieldISBN.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(history -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (history.getISBN().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        filterFieldQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(history -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (history.getQuantity().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });
        SortedList<History> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
}
