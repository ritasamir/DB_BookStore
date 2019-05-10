package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Manager;

public class AddOrdersController {
    @FXML /* Name text field contents */
    private TextField ISBN;
    @FXML /* Address text field contents */
    private TextField quantity;
    /* Contains Publisher inforamtion needed to add to publisher table */
    private String[] orderInfo;

    /*Constructor */
    public AddOrdersController(){
        orderInfo = new String[2];

    }

    /* Add Publisher button Handler */
    public void submit(ActionEvent event)throws Exception{
        if(!validate()){
            return;
        }else {
            String result = Manager.addOrder(orderInfo);
            if(result.equals("Order added successfully")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Order added successfully");
                String style = getClass().getResource("/sample//sample.css").toExternalForm();
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().addAll(style);
                alert.showAndWait();
            }else {
                showErrorAlert(result);
            }
        }
    }
    /* Check All text fields are filled and store information */
    private boolean validate() {
        if(ISBN.getText().isEmpty()) {
            showErrorAlert("Please fill ISBN field");
            return false;
        }else if(quantity.getText().isEmpty()){
            showErrorAlert("Please fill quantity field");
            return false;
        }
        else{
            orderInfo[0] = ISBN.getText();
            orderInfo[1]= quantity.getText();
        }
        return true;
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
        String style = getClass().getResource("/sample//sample.css").toExternalForm();
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().addAll(style);
        alert.showAndWait();
    }
}
