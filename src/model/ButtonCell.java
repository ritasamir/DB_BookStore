package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;

public class ButtonCell extends TableCell<Book, Boolean> {
    final  Button cellButton = new Button("+");

    public ButtonCell(final TableView tblView){

        cellButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                int selectdIndex = getTableRow().getIndex();
                Book selectedRecord = (Book)tblView.getItems().get(selectdIndex);
                System.out.println(selectedRecord.bookTitle);
            }
        });
    }

    //Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if(!empty){
            setGraphic(cellButton);
        }
    }
}
