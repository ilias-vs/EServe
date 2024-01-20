package com.eservice;

import com.eservice.controllers.home.HomePage;
import com.eservice.controllers.menu.MenuPage;
import com.eservice.controllers.orders.OrdersPage;
import com.eservice.controllers.profile.ProfilePage;
import com.eservice.controllers.tables.TablePage;
import com.eservice.model.Factory;
import com.eservice.model.IUser;
import com.eservice.model.User;
import com.eservice.model.repository.MainFrame;
import com.eservice.model.repository.StoreSingleton;
import com.eservice.model.repository.UserSingleton;
import com.eservice.model.service.FireBaseService;
import com.eservice.model.service.OrderService;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;


public class MainPanelController implements Initializable{

    @FXML
    private BorderPane borderPane;
    private double x,y = 0;
    private Stage primaryStage;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private AnchorPane slider;
    private String userid;
    public MainPanelController(Stage primaryStage,String userid) {
        this.primaryStage = primaryStage;
        this.userid=userid;
    }

    @FXML
    void getHomePage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/eservice/controllers/HomePage.fxml"));
            HomePage homeController=new HomePage();
            loader.setController(homeController);
            GridPane view=loader.load();
            borderPane.setCenter(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void getMenuPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/eservice/controllers/MenuPage.fxml"));
            MenuPage homeController=new MenuPage();
            loader.setController(homeController);
            GridPane view=loader.load();
            borderPane.setCenter(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void getOrderPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/eservice/controllers/OrdersPage.fxml"));
            OrdersPage ordersPage=new OrdersPage();
            loader.setController(ordersPage);
            GridPane view=loader.load();
            borderPane.setCenter(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void getProfilePage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/eservice/controllers/ProfilePage.fxml"));
            ProfilePage profilePage=new ProfilePage();
            loader.setController(profilePage);
            GridPane view=loader.load();
            borderPane.setCenter(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void getTablePage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/eservice/controllers/TablePage.fxml"));
            TablePage tableController=new TablePage();
            loader.setController(tableController);
            GridPane view=loader.load();
            borderPane.setCenter(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserSingleton.getInstance().setUser(Factory.createUser());
        try {
            FireBaseService.getUser(userid);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        MainFrame.getInstance().setMainFrame(borderPane);
        MenuClose.setVisible(false);
        slider.setTranslateX(-177);


        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-177);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });
        });

        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-177);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
            });
        });
    }
}