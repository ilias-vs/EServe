package com.eservice.controllers.tables;

import com.eservice.controllers.tables.buttonController.Table;
import com.eservice.model.IStore;
import com.eservice.model.ITables;
import com.eservice.model.repository.UserSingleton;
import com.eservice.model.service.StoreService;
import com.eservice.view.table.DialogAddTable;
import com.eservice.view.table.TableContainer;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class TablePage implements Initializable {

    private ImageView imageView;
    @FXML
    private StackPane mainPanel;

    @FXML
    void createTable(MouseEvent event) {
        DialogAddTable.create();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        imageView = new ImageView();

        Image img = new Image(Objects.requireNonNull(this.getClass().getResource("/com/eservice/Images/LoadingFix.gif")).toExternalForm());

        imageView.setImage(img);

        mainPanel.getChildren().add(imageView);//create the grid

        Service<Void> tableService = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        setTables();
                        return null;
                    }
                };
            }
        };
        tableService.setOnSucceeded(event -> imageView.setVisible(false));

        tableService.start();

    }

    public void setTables(){
        Firestore db = FirestoreClient.getFirestore();
        Image image=new Image(this.getClass().getResource("/com/eservice/Images/trashWhite.png").toExternalForm());
        Image notReservedImage=new Image(this.getClass().getResource("/com/eservice/Images/notReserved.png").toExternalForm());
        Image reservedStatus=new Image(this.getClass().getResource("/com/eservice/Images/reserved.png").toExternalForm());


        Platform.runLater(() -> {
                    IStore store = null;
                    try {
                        store = StoreService.getStoresByOwner(db, UserSingleton.getInstance().getUser().getId());
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }


                    List<ITables> tablesList = store.getTablesList();
                    if (!tablesList.isEmpty()) {
                        VBox container = new VBox();
                        container.setAlignment(Pos.CENTER);

                        //create a scroll pane
                        ScrollPane scrollPane = new ScrollPane(container);
                        scrollPane.setFitToWidth(true);
                        scrollPane.setFitToHeight(true);

                        mainPanel.getChildren().add(scrollPane);
                        int count = 0;
                        for (ITables table : tablesList) {
                            VBox mainBox = TableContainer.create(table, image, reservedStatus, notReservedImage, count);
                            container.getChildren().add(mainBox);
                            count++;
                        }
                    }else {
                        mainPanel.getChildren().add(new Label("Their is no Tables"));
                    }
                }
        );
    }
}
