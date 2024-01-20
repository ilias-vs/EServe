package com.eservice.controllers.menu;

import com.eservice.model.IMenu;
import com.eservice.model.IMenuCategory;
import com.eservice.model.IMenuItem;
import com.eservice.model.IStore;
import com.eservice.model.repository.UserSingleton;
import com.eservice.model.service.StoreService;
import com.eservice.view.menu.DialogAddCategory;
import com.eservice.view.menu.MenuContainer;
import com.eservice.view.menu.SeperatorMenu;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class MenuPage implements Initializable {
    @FXML
    public StackPane mainFrame;
    private int counter = 0;
    private ImageView imageView;
    private Image delete, add, edit, editWhite, deleteWhite;

    @FXML
    void createCategory(MouseEvent event) {
        DialogAddCategory.create();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        delete = new Image(Objects.requireNonNull(this.getClass().getResource("/com/eservice/Images/trashBlack.png")).toExternalForm());
        add = new Image(Objects.requireNonNull(this.getClass().getResource("/com/eservice/Images/addBlack.png")).toExternalForm());
        edit = new Image(Objects.requireNonNull(this.getClass().getResource("/com/eservice/Images/editBlack.png")).toExternalForm());
        editWhite = new Image(Objects.requireNonNull(this.getClass().getResource("/com/eservice/Images/ediWhite.png")).toExternalForm());
        deleteWhite = new Image(Objects.requireNonNull(this.getClass().getResource("/com/eservice/Images/trashWhite.png")).toExternalForm());

        imageView = new ImageView();

        Image img = new Image(Objects.requireNonNull(this.getClass().getResource("/com/eservice/Images/LoadingFix.gif")).toExternalForm());

        imageView.setImage(img);

        mainFrame.getChildren().add(imageView);//create the grid


        Service<Void> menuService = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        setMenu();
                        return null;
                    }
                };
            }
        };

        menuService.setOnSucceeded(event -> imageView.setVisible(false));

        menuService.start();
    }

    private void setMenu() {

        Firestore db = FirestoreClient.getFirestore();

        IStore store;
        try {
            store = StoreService.getStoresByOwner(db, UserSingleton.getInstance().getUser().getId());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        IMenu menu = store.getMenu();
        List<IMenuCategory> menuCategoryList = menu.getCategories();

        Platform.runLater(() -> {
            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(50));
            //center alignment
            gridPane.setAlignment(Pos.CENTER);
            //add spacing for each item
            gridPane.setHgap(50);
            gridPane.setVgap(50);

            //create a scroll pane
            ScrollPane scrollPane = new ScrollPane(gridPane);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            mainFrame.getChildren().add(scrollPane);
            //get all items for
            if (!menuCategoryList.isEmpty()) {
                // Create a list of buttons (replace with your own buttons)
                for (int i = 0; i < menuCategoryList.size(); i++) {
                    List<IMenuItem> menuItemList = menuCategoryList.get(i).getMenuItems();

                    if (counter % 3 == 1) {
                        counter += 2;
                        gridPane.add(SeperatorMenu.createSeparor(menuCategoryList.get(i).getCategoryName(), delete, edit, add, i), 0, counter / 3);
                        counter += 3;
                    } else if (counter % 3 == 2) {
                        counter += 1;
                        gridPane.add(SeperatorMenu.createSeparor(menuCategoryList.get(i).getCategoryName(), delete, edit, add, i), 0, counter / 3);
                        counter += 3;
                    } else {
                        gridPane.add(SeperatorMenu.createSeparor(menuCategoryList.get(i).getCategoryName(), delete, edit, add, i), 0, counter / 3);
                        counter += 3;
                    }
                    for (int j = 0; j < menuItemList.size(); j++) {
                        counter = MenuContainer.createMenuItem(counter, gridPane, menuItemList.get(j), editWhite, deleteWhite, j, menuCategoryList.get(i).getCategoryName());
                        counter++;
                    }

                }
            } else {
                mainFrame.getChildren().add(new Label("Their is no menu"));
            }
            imageView.setVisible(false);
        });
    }


}
