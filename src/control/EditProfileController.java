package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class EditProfileController {
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
    private PasswordField oldPasswordField;
    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Button btnConfirm;
    String[] oldUserInfo ;
    public EditProfileController() {
        user = User.getInstance();
        userInfo = new String[7];
        oldUserInfo = user.getUserInfo();

    }
    @FXML
    private void initialize() {
         userNameField.setText(oldUserInfo[0]);
         emailField.setText(oldUserInfo[2]);
         firstNameField.setText(oldUserInfo[3]);
         lastNameField.setText(oldUserInfo[4]);
         shippingAddressField.setText(oldUserInfo[5]);
         phoneField.setText(oldUserInfo[6]);
    }

    public void confirm(ActionEvent event)throws Exception{
        if(!validate()){
            System.out.println(userInfo[6]);
            return;
        }else {
            //TODO check if new user's info is already exist for other user

            if(user.updateProfile(userInfo)){
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
        if(firstNameField.getText().isEmpty()) {
            lblStatus.setText("Please enter your first name");
            return false;
        }else{
            userInfo[1]=firstNameField.getText();
        }
        if(lastNameField.getText().isEmpty()) {
            lblStatus.setText("Please enter your last name");
            return false;
        }else{
            userInfo[2]=lastNameField.getText();
        }
        if(emailField.getText().isEmpty()) {
            lblStatus.setText( "Please enter your email ");
            return false;
        }else{
            userInfo[3]=emailField.getText();
        }
        if(phoneField.getText().isEmpty()) {
            lblStatus.setText("Please enter your phone");
            return false;
        }else{
            userInfo[4]=phoneField.getText();
        }
        if(shippingAddressField.getText().isEmpty()) {
            lblStatus.setText("Please enter your shipping address");
            return false;
        }else{
            userInfo[5]=shippingAddressField.getText();
        }

        if(!oldPasswordField.getText().equals("")
                || !newPasswordField.getText().equals("")){
            System.out.println("m"+oldPasswordField.getText()+"m");
            if(oldPasswordField.getText().equals(oldUserInfo[1])&&!newPasswordField.getText().equals("")){
                userInfo[6]=newPasswordField.getText();
            }else{
                lblStatus.setText("Please enter your correct password");
                userInfo[6]=oldUserInfo[1];
                return false;
            }
        }else{
            userInfo[6]=oldUserInfo[1];
        }

        return true;
    }
}
