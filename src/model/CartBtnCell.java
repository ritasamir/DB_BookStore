package model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import java.util.Optional;

public class CartBtnCell extends TableCell<Book, Boolean> {
    final  Button cellButton = new Button("Add To Cart");
    Cart cart = Cart.getInstance();

    public CartBtnCell(final TableView tblView){

        cellButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                int selectdIndex = getTableRow().getIndex();
                Book selectedBook = (Book)tblView.getItems().get(selectdIndex);
                String orderQuantity = getOrderQuantity();
                if(orderQuantity!= null)
                    cart.addToCart(selectedBook,orderQuantity);
            }
        });
    }
    private String getOrderQuantity(){
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Order Quantity");
        dialog.setHeaderText("Please enter your needed quantity");
        dialog.setContentText("Quantity:");
        String style = getClass().getResource("/sample/sample.css").toExternalForm();
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().addAll(style);
        Optional<String> result = dialog.showAndWait();
              Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        TextField inputField = dialog.getEditor();
        BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> isInvalid(inputField.getText()), inputField.textProperty());
        okButton.disableProperty().bind(isInvalid);
        if (result.isPresent()){
            return result.get();
        }
        return null;
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
