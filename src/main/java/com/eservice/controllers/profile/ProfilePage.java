package com.eservice.controllers.profile;

import com.eservice.model.IStore;
import com.eservice.model.repository.StoreSingleton;
import com.eservice.model.repository.UserSingleton;
import com.eservice.model.service.FireBaseService;
import com.eservice.model.service.ImageEncryption;
import com.eservice.model.service.StoreService;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class ProfilePage implements Initializable {
    @FXML
    private StackPane mainFrame;
    @FXML
    private Circle storeImage;
    @FXML
    private JFXTextField storeName,storeAddress,ownerName,ownerLast;
    private String storeIMG;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Firestore db = FirestoreClient.getFirestore();

        IStore store= null;
        try {
            store = StoreService.getStoresByOwner(db,UserSingleton.getInstance().getUser().getId());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(!store.getName().isBlank()){
            storeName.setText(store.getName());
        }

        if(!store.getAddress().isBlank()){
            storeAddress.setText(store.getAddress());
        }
        if(!store.getImageData().isBlank()){
            storeImage.setFill(new ImagePattern(ImageEncryption.decodeImage(store)));
            storeIMG=store.getImageData();
        }
        if(!UserSingleton.getInstance().getUser().getName().isBlank()){
            ownerName.setText(UserSingleton.getInstance().getUser().getName());
        };
        if(!UserSingleton.getInstance().getUser().getLastName().isBlank()){
            ownerLast.setText(UserSingleton.getInstance().getUser().getLastName());
        };

    }

    @FXML
    void setNewImage(MouseEvent event) {
        System.out.println("Clicked");
        FileChooser fileChooser = new FileChooser();
        ExtensionFilter imageFilter = new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);
        // Show the file chooser dialog
        java.io.File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Print the chosen file's path
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            Image selectedImage = new Image(selectedFile.toURI().toString());
            storeImage.setFill(new ImagePattern(selectedImage));
            storeIMG= ImageEncryption.encodeImage(selectedFile);
        } else {
            System.out.println("No file selected.");
        }
    }

    @FXML
    void save2Firebase(ActionEvent event) {
        System.out.println("Clicked");
        try {
            FireBaseService.updateUser(ownerName.getText(),ownerLast.getText());
            StoreService.updateStore(storeName.getText(),storeAddress.getText(),storeIMG);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
