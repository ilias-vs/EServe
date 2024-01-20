package com.eservice.controllers.orders;

import com.eservice.model.IOrders;
import com.eservice.model.IStore;
import com.eservice.model.repository.UserSingleton;
import com.eservice.model.service.OrderService;
import com.eservice.model.service.StoreService;
import com.eservice.view.order.OrderBox;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class OrdersPage implements Initializable {
    private ImageView imageView;
    @FXML
    public StackPane mainFrame;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView = new ImageView();

        Image img = new Image(Objects.requireNonNull(this.getClass().getResource("/com/eservice/Images/LoadingFix.gif")).toExternalForm());

        imageView.setImage(img);

        mainFrame.getChildren().add(imageView);//create the grid

        Service<Void> orderService = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        setOrderPage();
                        return null;
                    }
                };
            }
        };
        orderService.setOnSucceeded(event -> imageView.setVisible(false));

        orderService.start();

    }

    private void setOrderPage() {
        Image image = new Image(this.getClass().getResource("/com/eservice/Images/next.png").toExternalForm());
        VBox box=new VBox();
        box.setAlignment(Pos.CENTER);

        Firestore db = FirestoreClient.getFirestore();
        List<IOrders> orders;
        IStore store= null;

        try {
            store = StoreService.getStoresByOwner(db,UserSingleton.getInstance().getUser().getId());
            orders = OrderService.getOrderByStore(UserSingleton.getInstance().getUser().getStoreID(), db);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }


        for (IOrders order : orders
        ) {
            VBox mainBox = OrderBox.create(order, image,store);
            box.getChildren().add(mainBox);
        }

        Platform.runLater(() -> {
            ScrollPane scrollPane = new ScrollPane(box);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            mainFrame.getChildren().add(scrollPane);
        });
    }

}
