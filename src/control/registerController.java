package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;
import javafx.stage.Window;


public class registerController {
    User user;
    String[] userInfo;
    @FXML
    private Label lblStatus;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField shippingAddressField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button btnSubmit;

    public registerController() {
        user = User.getInstance();
        userInfo = new String[7];
    }

    public void submitRegistration(ActionEvent event)throws Exception{
        if(!validate()){
            return;
        }else {
            //TODO check if the user is already registered
            if(user.addNewCustomer(userInfo)){
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/userView.fxml")));
                stage.setScene(scene);
                stage.show();
            }else{
                System.out.println("CANN'T INSERT THAT USER");
            }
        }
    }

    private boolean validate() {
        if(userNameField.getText().isEmpty()) {
            lblStatus.setText("Please enter your user name");
            return false;
        }else{
            userInfo[0]=userNameField.getText();
        }
        if(passwordField.getText().isEmpty()) {
            lblStatus.setText("Please enter a password");
            return false;
        }else{
            userInfo[1]=passwordField.getText();
        }
        if(firstNameField.getText().isEmpty()) {
            lblStatus.setText("Please enter your first name");
            return false;
        }else{
            userInfo[2]=firstNameField.getText();
        }
        if(lastNameField.getText().isEmpty()) {
            lblStatus.setText("Please enter your last name");
            return false;
        }else{
            userInfo[3]=lastNameField.getText();
        }
        if(emailField.getText().isEmpty()) {
            lblStatus.setText( "Please enter your email ");
            return false;
        }else{
            userInfo[4]=emailField.getText();
        }
        if(phoneField.getText().isEmpty()) {
            lblStatus.setText("Please enter your phone");
            return false;
        }else{
            userInfo[5]=phoneField.getText();
        }
        if(shippingAddressField.getText().isEmpty()) {
            lblStatus.setText("Please enter your shipping address");
            return false;
        }else{
            userInfo[6]=shippingAddressField.getText();
        }

        return true;
    }
    public void back(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/login.fxml")));
        stage.setScene(scene);
        stage.show();
    }
}
