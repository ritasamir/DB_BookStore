package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Manager;

public class ManagerViewController {
    @FXML
    private Button addBooksBtn;

    Manager manager;

    public ManagerViewController() {
        manager= new Manager();
    }


    public void modifyBooks(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Books.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public void modifyPublishers(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Publishers.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public void orders(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Orders.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    public void users(ActionEvent event)throws Exception{
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Users.fxml")));
        stage.setScene(scene);
        stage.show();
    }
}
