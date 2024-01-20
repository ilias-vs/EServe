package com.eservice.view.menu;

import com.eservice.controllers.menu.ButtonController.Category;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.concurrent.ExecutionException;
public class SeperatorMenu {

    public static VBox createSeparor(String categoryName, Image delete, Image edit, Image add, int position){
        VBox seperatorBox = new VBox();

        HBox titleBox=new HBox();

        titleBox.setAlignment(Pos.CENTER);
        titleBox.getStyleClass().add("menu-seperator");


        Label title=new Label(categoryName);
        HBox.setMargin(title ,new Insets(0, 0, 0, 15));
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titleBox.getChildren().add(title);

        Region expand =new Region();
        HBox.setHgrow(expand, Priority.ALWAYS);
        titleBox.getChildren().add(expand);

        ImageView addMenuItem=new ImageView(add);
        addMenuItem.setFitHeight(15);
        addMenuItem.setFitWidth(15);
        HBox.setMargin(addMenuItem,new Insets(0,0,0,10));
        titleBox.getChildren().add(addMenuItem);
        addMenuItem.getStyleClass().add("menu-add-button");
        addMenuItem.setOnMouseClicked(event -> {
            DialogAddMenuItem.create(categoryName);
        });


        ImageView editCategory=new ImageView(edit);
        editCategory.setFitHeight(15);
        editCategory.setFitWidth(15);
        HBox.setMargin(editCategory,new Insets(0,0,0,10));
        titleBox.getChildren().add(editCategory);
        editCategory.getStyleClass().add("edit-icon");
        editCategory.setOnMouseClicked(event -> {
            DialogEditCategory.create(categoryName,position);
        });


        ImageView deleteCategory=new ImageView(delete);
        deleteCategory.setFitHeight(15);
        deleteCategory.setFitWidth(15);
        HBox.setMargin(deleteCategory,new Insets(0,10,0,10));
        titleBox.getChildren().add(deleteCategory);
        deleteCategory.getStyleClass().add("delete-icon");
        deleteCategory.setOnMouseClicked(event -> {
            DialogDeleteCategory.create(position);
        });

        Separator separator=new Separator();
        seperatorBox.getChildren().add(titleBox);
        seperatorBox.getChildren().add(separator);

        return seperatorBox;
    }
}
