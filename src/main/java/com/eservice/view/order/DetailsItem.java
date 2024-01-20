package com.eservice.view.order;

import com.eservice.model.IMenuItem;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DetailsItem {
    public static void create(VBox mainBox, IMenuItem item){
        Label itemName= new Label(item.getName());
        VBox.setMargin(itemName,new Insets(20,0,0,10));
        itemName.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        Label cost=new Label("Cost : "+item.getCost() + " €");
        VBox.setMargin(cost,new Insets(5,0,0,10));
        Separator separator=new Separator();
        VBox.setMargin(separator,new Insets(5,20,0,20));

        mainBox.getChildren().addAll(itemName,cost,separator);
    }
}
