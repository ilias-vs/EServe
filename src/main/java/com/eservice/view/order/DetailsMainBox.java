package com.eservice.view.order;

import com.eservice.model.IOrders;
import com.eservice.model.IStore;
import com.eservice.model.service.OrderService;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.concurrent.ExecutionException;

public class DetailsMainBox {
    public static VBox create(IOrders order, IStore store) {
        Firestore db = FirestoreClient.getFirestore();
        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.TOP_LEFT);
        Label storeName = new Label(store.getName());
        VBox.setMargin(storeName ,new Insets(20, 0, 10, 20));
        storeName.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        Label locationName = new Label(store.getAddress());
        VBox.setMargin(locationName ,new Insets(0, 0, 10, 20));
        locationName.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        Label clientName;

        try {
            clientName = new Label(OrderService.getClientName(order.getUser_id(), db));
            VBox.setMargin(clientName ,new Insets(0, 0, 5, 20));
            clientName.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Separator separator = new Separator();
        mainBox.getChildren().addAll(storeName, locationName, clientName,separator);
        return mainBox;
    }
}
