package com.eservice.view.menu;

import com.eservice.model.IMenuItem;
import com.jfoenix.controls.JFXToggleButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class MenuContainer {
    public static int createMenuItem(int i, GridPane gridPane, IMenuItem menuItem, Image edit, Image delete, int position,String categoryName) {
        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPrefWidth(200); // Set the preferred width of the button
        mainBox.setMinHeight(90); // Set the preferred height of the button
        mainBox.setMaxHeight(90); // Set the preferred width of the button
        mainBox.setMaxWidth(200); // Set the preferred height of the button

        HBox titleBox = new HBox();
        titleBox.getStyleClass().add("title-panel");
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPrefHeight(70);
        titleBox.setPrefWidth(200);
        Label title=new Label(menuItem.getName());

        titleBox.getChildren().add(title);
        HBox.setMargin(title,new Insets(0,10,0,10));

        Region expand =new Region();
        HBox.setHgrow(expand, Priority.ALWAYS);
        titleBox.getChildren().add(expand);


        ImageView editCategory=new ImageView(edit);
        editCategory.setFitHeight(15);
        editCategory.setFitWidth(15);
        HBox.setMargin(editCategory,new Insets(0,0,0,10));
        titleBox.getChildren().add(editCategory);
        editCategory.getStyleClass().add("edit-icon");
        editCategory.setOnMouseClicked(event -> {
            DialogEditMenuItem.create(categoryName,position,menuItem.getName(),menuItem.getCost());
        });

        ImageView deleteCategory=new ImageView(delete);
        deleteCategory.setFitHeight(15);
        deleteCategory.setFitWidth(15);
        HBox.setMargin(deleteCategory,new Insets(0,10,0,10));
        titleBox.getChildren().add(deleteCategory);
        deleteCategory.getStyleClass().add("delete-icon");
        deleteCategory.setOnMouseClicked(event -> {
            DialogDeleteMenuItem.create(position, menuItem.getName(),categoryName);
        });

        HBox costBox = new HBox();
        costBox.setAlignment(Pos.CENTER);
        costBox.setPrefHeight(46);
        costBox.setPrefWidth(200);
        Label labelCost=new Label("Cost : ");
        Label costLabel=new Label(menuItem.getCost()+"€");

        costBox.getChildren().add(labelCost);
        costBox.getChildren().add(costLabel);

        HBox activeBox= new HBox();
        activeBox.setAlignment(Pos.CENTER);
        activeBox.setPrefHeight(60);
        activeBox.setPrefWidth(200);

        JFXToggleButton active=new JFXToggleButton();
        active.setText("Active");
        active.setContentDisplay(ContentDisplay.RIGHT);
        activeBox.getChildren().add(active);
        active.setSize(7);

        mainBox.getChildren().addAll(titleBox,costBox,activeBox);

        mainBox.getStyleClass().add("grid-panel");
        gridPane.add(mainBox, i % 3, i / 3); // Place the button in the grid
        return i;
    }
}
