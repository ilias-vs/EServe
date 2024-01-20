package com.eservice.view.table;

import com.eservice.controllers.tables.buttonController.Table;
import com.eservice.model.ITables;
import com.eservice.view.menu.DialogAddMenuItem;
import com.jfoenix.controls.JFXToggleButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TableContainer {
    public static VBox create(ITables table, Image image, Image reservedStatus, Image notReservedImage,int index) {
        VBox mainBox=new VBox();// main container of tables
        //mainBox.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(mainBox ,new Insets(0, 0, 40, 0));
        mainBox.getStyleClass().add("table-box");
        mainBox.setPrefWidth(300); // Set the preferred width of the button
        mainBox.setMinHeight(120); // Set the preferred height of the button
        mainBox.setMaxHeight(120); // Set the preferred width of the button
        mainBox.setMaxWidth(300); // Set the preferred height of the button


        HBox titleBox=new HBox();
        titleBox.setPrefWidth(300); // Set the preferred width of the button
        titleBox.setMinHeight(40); // Set the preferred height of the button
        titleBox.setMaxHeight(40); // Set the preferred width of the button
        titleBox.setMaxWidth(300); // Set the preferred height of the button
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getStyleClass().add("table-title");


        Label title=new Label(table.getName());
        HBox.setMargin(title ,new Insets(0, 0, 0, 15));
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Region expand =new Region();
        HBox.setHgrow(expand, Priority.ALWAYS);

        ImageView deleteTable=new ImageView();
        deleteTable.prefWidth(25);
        deleteTable.prefHeight(25);
        deleteTable.maxHeight(25);
        deleteTable.maxWidth(25);
        deleteTable.setFitHeight(25);
        deleteTable.setFitWidth(25);
        deleteTable.getStyleClass().add("table-delete");
        deleteTable.setOnMouseClicked(event -> {
            DialogDeleteTable.create(table,index);
        });

        deleteTable.setImage(image);


        ImageView status=new ImageView();
        status.prefWidth(15);
        status.prefHeight(15);
        status.maxHeight(15);
        status.maxWidth(15);
        status.setFitHeight(15);
        status.setFitWidth(15);

        if(table.getIs_reserved()){
            status.setImage(reservedStatus);
        }else {
            status.setImage(notReservedImage);
        }

        HBox.setMargin(status,new Insets(0,10,0,10));

        titleBox.getChildren().addAll(title,expand,deleteTable,status);

        JFXToggleButton eating=new JFXToggleButton();
        eating.getStyleClass().add("toggle-table");
        eating.setText("Eating now");
        eating.setContentDisplay(ContentDisplay.RIGHT);
        eating.setSize(9);

        mainBox.getChildren().addAll(titleBox,eating);
        return mainBox;
    }
}
