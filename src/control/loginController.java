package control;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.State;
import model.User;

public class loginController {
    User user;
    @FXML
    private Label lblStatus;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    public loginController() {
        user = User.getInstance();
    }

    public void register(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/register.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    public void login(ActionEvent event) throws Exception{
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        if(user.logIn(userName,password)){
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Login Successful..Redirecting..");
            user.userName=userName;
            String[] userInfo = user.getUserInfo();
            State state = new State();
            state.doAction(Integer.parseInt(userInfo[7]),event);
        }else{
            lblStatus.setText("Enter Correct UserName/Password");
        }
    }

}
