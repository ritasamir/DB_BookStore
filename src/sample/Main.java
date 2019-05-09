package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        primaryStage.setTitle("Book Store");
        primaryStage.setScene(new Scene(root, 335, 352));
        primaryStage.show();
        Controller control = new Controller();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
