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
import model.Book;
import model.Manager;
import model.Publisher;

public class ModifyPublisherController {
    @FXML /* Name text field contents */
    private TextField name;
    @FXML /* Address text field contents */
    private TextField address;
    @FXML /* Phone text field contents */
    private TextField phone;
    /* Contains Publisher inforamtion needed to add to publisher table */
    private String[] publisherInfo;
    String old_Name;
    /*Constructor */
    public ModifyPublisherController(){
        publisherInfo = new String[3];
    }

    public void modifyPublisher(Publisher publisher){
        old_Name = publisher.getName();
        name.setText(publisher.getName());
        address.setText(publisher.getAddress());
        phone.setText(publisher.getPhone());
    }

    /* Add Publisher button Handler */
    public void submit(ActionEvent event)throws Exception{
        if(!validate()){
            return;
        }else {
            String result = Manager.updatePublisher(old_Name,publisherInfo);
            if(result.equals("Publisher updated successfully")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Publisher updated successfully");
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
        if(name.getText().isEmpty()) {
            showErrorAlert("Please enter publisher's name");
            return false;
        }else if(address.getText().isEmpty()){
            showErrorAlert("Please enter Publisher's Address");
            return false;
        }else if(phone.getText().isEmpty()){
            showErrorAlert("Please enter Publisher's Phone");
            return false;
        }
        else{
            publisherInfo[0] = name.getText();
            publisherInfo[1]=address.getText();
            publisherInfo[2]=phone.getText();
        }
        return true;
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

    /* Go back to Manager Window */
    public void back(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/ManagerView.fxml")));
        stage.setScene(scene);
        stage.show();
    }
}
