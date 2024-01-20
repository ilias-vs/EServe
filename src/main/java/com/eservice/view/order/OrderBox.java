package com.eservice.view.order;

import com.eservice.controllers.orders.buttonController.Details;
import com.eservice.model.IOrders;
import com.eservice.model.IStore;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class OrderBox {
    public static VBox create(IOrders order, Image image, IStore store) {
        VBox mainBox = new VBox();// main container of tables
        VBox.setMargin(mainBox ,new Insets(0, 20, 40, 20));
        //mainBox.setAlignment(Pos.TOP_CENTER);
        mainBox.getStyleClass().add("order-box");
        mainBox.setPrefWidth(1000); // Set the preferred width of the button
        mainBox.setMinHeight(150); // Set the preferred height of the button
        mainBox.setMaxHeight(150); // Set the preferred width of the button
        mainBox.setMaxWidth(1000); // Set the preferred height of the button


        HBox titleBox = new HBox();//title HBOX
        titleBox.setPrefWidth(1000); // Set the preferred width of the button
        titleBox.setPrefHeight(25); // Set the preferred height of the button
        titleBox.setMaxHeight(25); // Set the preferred width of the button
        titleBox.setMaxWidth(1000); // Set the preferred height of the button
        HBox.setMargin(titleBox, new Insets(0, 40, 40, 0));
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getStyleClass().add("order-title");


        Label title = new Label("Order ID : "+ order.getOrder_id().toString());
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        titleBox.getChildren().add(title);

        Label storeLabel = new Label("Store: "+store.getName());
        storeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        VBox.setMargin(storeLabel, new Insets(15, 0, 15, 10));
        Separator separator = new Separator();
        VBox.setMargin(separator, new Insets(0, 10, 0, 10));


        HBox costAndNext = new HBox();
        VBox.setMargin(costAndNext, new Insets(15, 10, 0, 10));
        costAndNext.maxHeight(33);
        costAndNext.maxWidth(1000);
        costAndNext.setAlignment(Pos.CENTER);
        Label cost = new Label("cost : "+ order.getCost()+"€");
        Region expand = new Region();
        HBox.setHgrow(expand, Priority.ALWAYS);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        imageView.prefHeight(25);
        imageView.prefWidth(25);
        JFXButton next = new JFXButton("View Details", imageView);
        next.maxHeight(30);
        next.setContentDisplay(ContentDisplay.RIGHT);
        cost.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        costAndNext.getChildren().addAll(cost, expand, next);
        next.setOnAction(actionEvent -> {
                Details.view(order,store);

        });

        mainBox.getChildren().addAll(titleBox, storeLabel, separator, costAndNext);
        return mainBox;
    }
}
