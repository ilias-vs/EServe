package com.eservice.controllers.orders;

import com.eservice.model.IMenuItem;
import com.eservice.model.IOrders;
import com.eservice.model.IStore;
import com.eservice.view.order.DetailsItem;
import com.eservice.view.order.DetailsMainBox;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailPage implements Initializable {
    private IOrders order;
    private IStore store;
    @FXML
    private StackPane mainPanel;

    @FXML
    private Label orderID;

    public DetailPage(IOrders order, IStore store) {
        this.order = order;
        this.store=store;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderID.setText("Order " + order.getOrder_id().toString());
        VBox mainBox=DetailsMainBox.create(order,store);
        for (IMenuItem item:order.getMenuItems()) {
            DetailsItem.create(mainBox,item);
        }
        Label totalCost= new Label("Total Cost :"+order.getCost().toString()+" €");
        VBox.setMargin(totalCost,new Insets(20,0,0,40));
        totalCost.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        mainBox.getChildren().add(totalCost);
        ScrollPane scrollPane = new ScrollPane(mainBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        mainPanel.getChildren().add(scrollPane);
    }
}
