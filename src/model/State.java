package model;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class State {
    public void doAction(int is_manager, ActionEvent event) throws IOException {
        if( is_manager == 1){
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/ManagerView.fxml")));
            stage.setScene(scene);
            stage.show();

        }else if(is_manager == 0){
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/userView.fxml")));
            stage.setScene(scene);
            stage.show();
        }
    }
}
