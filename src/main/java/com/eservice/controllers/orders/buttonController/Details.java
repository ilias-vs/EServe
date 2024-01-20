package com.eservice.controllers.orders.buttonController;

import com.eservice.controllers.orders.DetailPage;
import com.eservice.model.IOrders;
import com.eservice.model.IStore;
import com.eservice.model.repository.MainFrame;
import com.eservice.view.menu.DialogAddCategory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class Details {
    public static void view(IOrders order, IStore store){
        try {
            FXMLLoader loader = new FXMLLoader(DialogAddCategory.class.getResource("/com/eservice/controllers/OrderDetails.fxml"));
            DetailPage homeController=new DetailPage(order,store);
            loader.setController(homeController);
            GridPane view = loader.load();
            MainFrame.getInstance().getMainFrame().setCenter(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
