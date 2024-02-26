package com.eservice;

import com.eservice.controllers.login.LoginPage;
import com.eservice.model.service.FireBaseService;
import com.eservice.model.service.OrderService;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
//TODO create Service that connects the database 
//TODO NEW VIEWS ADVANCED, POS,SALES AND BUYS
public class Main extends Application {
    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FireBaseService.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //load fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        //create controller of fxml
        LoginPage controller=new LoginPage(stage);
        //set controller
        loader.setController(controller);
        //load on main scene
        Parent root = loader.load();
        //remove windows borders
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/com/eservice/Images/logo.png")));
        //title of app
        stage.setTitle("E-Service");
        //size of window
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);

        stage.show();
    }
    public static void main(String[] args) {

        launch();
    }
}
