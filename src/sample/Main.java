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
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 330, 330));
        primaryStage.show();
        Controller control = new Controller();
//        String searchAttr="Title";
//        String searchVal="rita";
//        String[][] result = control.search("book",searchAttr,searchVal);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
