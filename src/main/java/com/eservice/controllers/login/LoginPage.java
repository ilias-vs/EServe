package com.eservice.controllers.login;

import com.eservice.MainPanelController;
import com.eservice.controllers.register.RegisterPage;
import com.eservice.model.service.FireBaseService;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginPage{
    private Stage primaryStage;
    private ImageView imageView;
    public LoginPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXPasswordField password;
    @FXML
    void login(ActionEvent event) {
        imageView = new ImageView();
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);

        Image img = new Image(Objects.requireNonNull(this.getClass().getResource("/com/eservice/Images/LoadingFix.gif")).toExternalForm());

        imageView.setImage(img);

        Service<Void> loginTask = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        loginButton.setDisable(true);
                        doLogin();
                        return null;
                    }
                };
            }
        };

        loginTask.setOnSucceeded(workerStateEvent -> imageView.setVisible(false));

        loginTask.start();

    }
    void doLogin(){

        try {
            Platform.runLater(() -> {
                imageView.setVisible(true);
                loginButton.setGraphic(imageView);
                loginButton.setText("");
            });
            String userid=FireBaseService.loginClient(email.getText(),password.getText());
            FXMLLoader loader = new FXMLLoader(LoginPage.class.getResource("/com/eservice/mainPanel.fxml"));
            MainPanelController mainPanelController=new MainPanelController(primaryStage,userid);
            loader.setController(mainPanelController);
            Parent root = loader.load();
            Scene scene=new Scene(root, 800, 600);
            Platform.runLater(() -> {
                primaryStage.setScene(scene);}
            );
        } catch (IOException e) {
            // Restore the text on the login button and remove the image
            Platform.runLater(() -> {
                loginButton.setDisable(false);
                loginButton.setGraphic(null);
                loginButton.setText("Login");
            });
        } catch (Exception e) {
            Platform.runLater(() -> {
                // Create an Alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wrong Input");
                alert.setHeaderText("Wrong password or email!");

                loginButton.setDisable(false);
                loginButton.setGraphic(null);
                loginButton.setText("Login");
                // Show the alert
                alert.showAndWait();
            });
        }
    }
    @FXML
    void singUp(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(LoginPage.class.getResource("/com/eservice/controllers/RegisterPage.fxml"));
            RegisterPage mainPanelController = new RegisterPage(primaryStage);
            loader.setController(mainPanelController);
            Parent root = loader.load();
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
