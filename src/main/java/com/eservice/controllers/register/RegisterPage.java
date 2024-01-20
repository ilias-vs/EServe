package com.eservice.controllers.register;

import com.eservice.controllers.login.LoginPage;
import com.eservice.model.service.FireBaseService;
import com.eservice.model.service.ImageEncryption;
import com.eservice.model.service.StoreService;
import com.jfoenix.controls.JFXPasswordField;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class RegisterPage  {
    @FXML
    private JFXTextField storeAddress,ownerName,ownerLast,ownerEmail,storeName;

    @FXML
    private JFXPasswordField ownerPassConfirm,ownerPassword;

    @FXML
    private JFXButton registerButton;
    @FXML
    private Circle storeImage;
    private Stage primaryStage;
    private String storeIMG;
    private ImageView imageView;
    public RegisterPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    void register(ActionEvent event) {
        imageView = new ImageView();
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);

        Image img = new Image(Objects.requireNonNull(this.getClass().getResource("/com/eservice/Images/LoadingFix.gif")).toExternalForm());

        imageView.setImage(img);

        Service<Void> registerTask = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        registerButton.setDisable(true);
                        doRegister();
                        return null;
                    }
                };
            }
        };

        registerTask.setOnSucceeded(workerStateEvent -> imageView.setVisible(false));

        registerTask.start();



    }

    void doRegister(){
        Platform.runLater(() -> {
            imageView.setVisible(true);
            registerButton.setGraphic(imageView);
            registerButton.setText("");
        });
            if(!ownerEmail.getText().isBlank() &&
                    !ownerLast.getText().isBlank() &&
                    !ownerPassword.getText().isBlank() &&
                    !ownerName.getText().isBlank() &&
                    !ownerPassConfirm.getText().isBlank() &&
                    !storeAddress.getText().isBlank() &&
                    !storeName.getText().isBlank() &&
                    storeIMG!=null &&
                    ownerPassword.getText().equals(ownerPassConfirm.getText())
            ){
                try {
                    String storeID=StoreService.createStore(storeName.getText(),storeIMG,storeAddress.getText());
                    String userID=FireBaseService.registerClient(ownerEmail.getText(),ownerPassword.getText(),ownerName.getText(),ownerLast.getText(),storeID);
                    StoreService.updateStoreOwner(userID,storeID);
                    try{
                        FXMLLoader loader = new FXMLLoader(RegisterPage.class.getResource("/com/eservice/LoginPage.fxml"));
                        LoginPage loginPage = new LoginPage(primaryStage);
                        loader.setController(loginPage);
                        Parent root = loader.load();
                        Scene scene = new Scene(root, 800, 600);
                        Platform.runLater(() -> {
                            primaryStage.setScene(scene);}
                        );
                    } catch (IOException e) {
                        Platform.runLater(() -> {
                            registerButton.setDisable(false);
                            registerButton.setGraphic(null);
                            registerButton.setText("Register");
                        });
                        throw new RuntimeException(e);
                    }
                } catch (ExecutionException e) {
                    Platform.runLater(() -> {
                        registerButton.setDisable(false);
                        registerButton.setGraphic(null);
                        registerButton.setText("Register");
                    });
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    Platform.runLater(() -> {
                        registerButton.setDisable(false);
                        registerButton.setGraphic(null);
                        registerButton.setText("Register");
                    });
                    throw new RuntimeException(e);
                }
            }else {
                Platform.runLater(() -> {
                    // Create an Alert
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("EMPTY");
                    alert.setHeaderText("Fill all fields and choose a picture");
                    registerButton.setDisable(false);
                    registerButton.setGraphic(null);
                    registerButton.setText("Register");
                    alert.showAndWait();
                });

            }
    }

    @FXML
    void setNewImage(MouseEvent event) {
        System.out.println("Clicked");
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);
        // Show the file chooser dialog
        java.io.File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Print the chosen file's path
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            // Load the selected image and store it
            Image selectedImage = new Image(selectedFile.toURI().toString());
            storeImage.setFill(new ImagePattern(selectedImage));
            storeIMG= ImageEncryption.encodeImage(selectedFile);
        } else {
            System.out.println("No file selected.");
        }
    }

    @FXML
    void signIn(MouseEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(RegisterPage.class.getResource("/com/eservice/LoginPage.fxml"));
            LoginPage loginPage = new LoginPage(primaryStage);
            loader.setController(loginPage);
            Parent root = loader.load();
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
