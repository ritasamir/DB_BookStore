package control;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import model.Book;
import model.Cart;
import model.State;
import model.User;
import sample.Controller;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class CartController {
    @FXML
    private TableView<Pair<Book,StringProperty>> cartTable;
    @FXML
    private TableColumn<Pair<Book,StringProperty>, String> ISBNColumn;
    @FXML
    private TableColumn<Pair<Book,StringProperty>, String> titleColumn;
    @FXML
    private TableColumn<Pair<Book,StringProperty>, String> quantityColumn;
    @FXML
    private TableColumn<Pair<Book,StringProperty>, String> categoryColumn;
    @FXML
    private TableColumn<Pair<Book,StringProperty>, String> priceColumn;
    @FXML
    private Button checkOutBtn;
    @FXML
    private Label lblTotalPrice;
    Label error;
    private ObservableList<Pair<Book,StringProperty>> masterData;
    Cart cart;
    Controller control;

    public CartController(){
        control= new Controller();
        cart=Cart.getInstance();

        masterData = getMasterData(cart.getCartItems());


    }
    public ObservableList<Pair<Book,StringProperty>> getMasterData(HashMap<Book, StringProperty> cartItems){
        masterData =FXCollections.observableArrayList();
        Set set = cartItems.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            Book keyValue = (Book) mapEntry.getKey();
            StringProperty value = (StringProperty) mapEntry.getValue();
            masterData.add(new Pair<>(keyValue,value));
            //System.out.println("Key : " + keyValue + "= Value : " + value);
        }
        return masterData;
    }
    @FXML
    public void initialize() {
        // 0. Initialize the columns.
        ISBNColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().ISBNProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().titleProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().getValue());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().categoryProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().getKey().priceProperty());
        TableColumn col_action = new TableColumn<>("Action");
        col_action.setSortable(false);
        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Pair<Book,StringProperty>, Boolean>,
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Pair<Book,StringProperty>, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });
        col_action.setCellFactory(
                new Callback<TableColumn<Pair<Book,StringProperty>, Boolean>, TableCell<Pair<Book,StringProperty>, Boolean>>() {

                    @Override
                    public TableCell<Pair<Book,StringProperty>, Boolean> call(TableColumn<Pair<Book,StringProperty>, Boolean> p) {
                        return new removeFCartBtn(cartTable,checkOutBtn,lblTotalPrice);
                    }

                });

        cartTable.getColumns().add(col_action);
        cartTable.setItems(masterData);
       lblTotalPrice.setText(cart.getTotalPrice());
        if(cart.getCartItems().isEmpty()){
            checkOutBtn.setDisable(true);
        }else{
            checkOutBtn.setDisable(false);
        }
    }
    public void back(ActionEvent event)throws Exception{
        State state = new State();
        User user = User.getInstance();
        state.doAction(Integer.parseInt(user.getUserInfo()[7]),event);
    }
    public void checkOut(ActionEvent event){
            if (getCreditCardInfo()) {
                for (Map.Entry<Book, StringProperty> entry : Cart.getInstance().compact(Cart.getInstance().getCartItems()).entrySet()) {
                    int newQuantity = Integer.parseInt(entry.getKey().getQuantity()) - Integer.parseInt(entry.getValue().get());
                    control.sellBook(entry.getKey().getISBN(), newQuantity, Integer.parseInt(entry.getValue().get()), User.getInstance().userName);
                }
                cart.setCartItems(new HashMap<Book, StringProperty>());
                cartTable.setItems(getMasterData(cart.getCartItems()));
                checkOutBtn.setDisable(true);
                lblTotalPrice.setText(cart.getTotalPrice());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Invalid Credit Card Information !");
                String style = getClass().getResource("/sample//sample.css").toExternalForm();
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().addAll(style);
                alert.showAndWait();
            }

    }
    private boolean isInvalid(String text)
    {
        try {
            double d = Double.parseDouble(text);
        } catch (NumberFormatException | NullPointerException nfe) {
            return true;
        }
        return text.length() != 16;
    }

    private boolean getCreditCardInfo() {
        try {
            String style = getClass().getResource("/sample/sample.css").toExternalForm();
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getStylesheets().addAll(style);
            dialog.setTitle("Credit Card Dialog");
            dialog.setHeaderText("Enter Your Credit Card Information");
            ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            TextField creditNo = new TextField();
            creditNo.setPromptText("Credit Card Number");
            String pattern = "dd/MM/yyyy";
            final DatePicker datePicker = new DatePicker();
            datePicker.setEditable(false);
            datePicker.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    LocalDate date = datePicker.getValue();
                    Date currdate = new Date();
                    if (datePicker.getEditor() != null && currdate.after(java.sql.Date.valueOf(date))) {
                        System.out.println("invalid date");
                        error.setText("Invalid Date");
                    } else {
                        error.setText("");
                    }
                }
            });
            datePicker.setPromptText(pattern.toLowerCase());
            Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            DayOfWeek day = DayOfWeek.from(item);
                            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                                this.setTextFill(Color.BLUE);
                            }
                        }
                    };
                }
            };

            datePicker.setDayCellFactory(dayCellFactory);
            Label selection = new Label("Expiry Date:");
            error = new Label("");
            error.setPrefSize(100, 50);
            error.setTextFill(Color.RED);
            grid.add(new Label("Credit Card Number:"), 0, 0);
            grid.add(creditNo, 1, 0);
            grid.add(selection, 0, 1);
            grid.add(datePicker, 1, 1);
            grid.add(error, 2, 1);
            Node confirmButton = dialog.getDialogPane().lookupButton(confirmButtonType);
            confirmButton.setDisable(true);
            creditNo.textProperty().addListener((observable, oldValue, newValue) -> {
                confirmButton.setDisable(newValue.trim().isEmpty());
            });
            BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> isInvalid(creditNo.getText()), creditNo.textProperty());
            confirmButton.disableProperty().bind(isInvalid);
            dialog.getDialogPane().setContent(grid);
            Optional<Pair<String, String>> result = dialog.showAndWait();
            grid.getStylesheets().addAll(style);
            if (result.isPresent()) {
                // System.out.println("creditNo=" + creditNo.getText() + ", Expiry Date=" + datePicker.getValue());
                User user = User.getInstance();
                if (datePicker.getValue() != null && cart.insertCreditCard(user.userName, creditNo.getText(), datePicker.getValue().toString()))
                    return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }
}
 class removeFCartBtn extends TableCell<Pair<Book,StringProperty>, Boolean>  {
    final Button cellButton = new Button("Remove");
    Cart cart = Cart.getInstance();
     HashMap<Book, StringProperty> cartItems;
    CartController cartController;
    public removeFCartBtn(final TableView tblView,Button checkOut,Label totalPrice) {
        cartItems= cart.getCartItems();
        cartController= new CartController();
        cellButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t){
                int selectdIndex = getTableRow().getIndex();
                Pair<Book,StringProperty> selectedItem = (Pair<Book,StringProperty>)tblView.getItems().get(selectdIndex);
                String quantity= cartItems.get(selectedItem.getKey()).get();
                String newQuantity = Integer.toString(Integer.parseInt(quantity)-1);
                if(Integer.parseInt(quantity)-1==0){
                    cartItems.remove(selectedItem.getKey(),selectedItem.getValue());
                    if(cartItems.isEmpty()){
                       checkOut.setDisable(true);
                    }
                }else {
                    cartItems.replace(selectedItem.getKey(), selectedItem.getValue(), new SimpleStringProperty(newQuantity));
                }
        System.out.println(cartItems);
                cart.setCartItems(cartItems);
                tblView.setItems(cartController.getMasterData(cartItems));
                totalPrice.setText(cart.getTotalPrice());
            }
        });
    }

     private boolean isInvalid(String text)
     {
         try {
             double d = Double.parseDouble(text);
         } catch (NumberFormatException | NullPointerException nfe) {
             return true;
         }
         return false;
     }


    //Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if(!empty){
            setGraphic(cellButton);
        }
        if (t == null) {
            setGraphic(null);
            return;
        }
    }
}
