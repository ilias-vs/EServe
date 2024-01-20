package com.eservice.view.table;

import com.eservice.controllers.menu.MenuPage;
import com.eservice.controllers.tables.TablePage;
import com.eservice.controllers.tables.buttonController.Table;
import com.eservice.model.ITables;
import com.eservice.model.repository.MainFrame;
import com.eservice.view.menu.DialogAddCategory;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class DialogDeleteTable {
    public static void create(ITables table,int index){
        // Create the custom dialog
        Alert dialog = new Alert(Alert.AlertType.NONE);
        dialog.setTitle("Delete Item");
        dialog.setHeaderText(null);

        // Create a GridPane to layout the content of the dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Create labels and text fields
        Label label1 = new Label("Delete Table "+ table.getName() +" ?");

        // Add labels and text fields to the GridPane
        grid.add(label1, 0, 0);

        // Set the content of the dialog to the GridPane
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(grid);

        // Add "OK" and "Cancel" buttons to the dialog
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Handle the "OK" button click event
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    Table.delete(index);
                    FXMLLoader loader = new FXMLLoader(DialogDeleteTable.class.getResource("/com/eservice/controllers/TablePage.fxml"));
                    TablePage TableController=new TablePage();
                    loader.setController(TableController);
                    GridPane view=loader.load();
                    MainFrame.getInstance().getMainFrame().setCenter(view);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });

        // Show the dialog and wait for user input
        dialog.showAndWait();
    }
}
