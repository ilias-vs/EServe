package com.eservice.view.menu;

import com.eservice.controllers.menu.ButtonController.Category;
import com.eservice.controllers.menu.ButtonController.MenuItem;
import com.eservice.controllers.menu.MenuPage;
import com.eservice.model.repository.MainFrame;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class DialogAddMenuItem {
    public static void create(String CategoryName){
        // Create the custom dialog
        Alert dialog = new Alert(Alert.AlertType.NONE);
        dialog.setTitle("Create Menu Item");
        dialog.setHeaderText("Category "+CategoryName);

        // Create a GridPane to layout the content of the dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Create labels and text fields
        Label label1 = new Label("Item Name:");
        Label label2 = new Label("Item Cost:");
        TextField textField1 = new TextField();
        TextField textField2 = new TextField();

        // Add labels and text fields to the GridPane
        grid.add(label1, 0, 0);
        grid.add(textField1, 1, 0);
        grid.add(label2, 0, 1);
        grid.add(textField2, 1, 1);

        // Set the content of the dialog to the GridPane
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(grid);

        // Add "OK" and "Cancel" buttons to the dialog
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Handle the "OK" button click event
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                // Get the input values from the text fields
                String value1 = textField1.getText();
                String value2 = textField2.getText();
                // You can process or use the input values here
                System.out.println("Value 1: " + value1);
                System.out.println("Value 2: " + value2);
                Firestore db = FirestoreClient.getFirestore();
                try {

                    MenuItem.add(db,CategoryName,value1,value2);

                    FXMLLoader loader = new FXMLLoader(DialogAddCategory.class.getResource("/com/eservice/controllers/MenuPage.fxml"));
                    MenuPage homeController=new MenuPage();
                    loader.setController(homeController);
                    GridPane view=loader.load();
                    MainFrame.getInstance().getMainFrame().setCenter(view);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });

        // Show the dialog and wait for user input
        dialog.showAndWait();
    }
}
