package com.eservice.controllers.home;

import com.eservice.model.IStore;
import com.eservice.model.ITables;
import com.eservice.model.repository.UserSingleton;
import com.eservice.model.service.ImageEncryption;
import com.eservice.model.service.StoreService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class HomePage implements Initializable {
    @FXML
    private Label location,shopName,eatingCounter;

    @FXML
    private JFXToggleButton onlineToggle;

    @FXML
    private ImageView onlineIMG,storeIcon;
    private Image imgOffline ,imgOnline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgOffline = new Image(this.getClass().getResource("/com/eservice/Images/icons8-offline-48.png").toExternalForm());
        imgOnline = new Image(this.getClass().getResource("/com/eservice/Images/icons8-online-96.png").toExternalForm());
        Firestore db = FirestoreClient.getFirestore();

        IStore store= null;
        try {
            store = StoreService.getStoresByOwner(db, UserSingleton.getInstance().getUser().getId());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        shopName.setText(store.getName());
        location.setText(store.getAdress());
        int counter=0;
        List<ITables> tablesList=store.getTablesList();
        for (ITables table: tablesList
             ) {
            if(table.getIs_reserved())
                counter++;
        }
        eatingCounter.setText(String.valueOf(counter));

        if(store.getOnline()){
            onlineIMG.setImage(imgOnline);
            onlineToggle.setText("Online");
            onlineToggle.setSelected(true);
        }
        else {
            onlineIMG.setImage(imgOffline);
            onlineToggle.setText("Offline");
            onlineToggle.setSelected(false);
        }
        if(!store.getImageData().isBlank()){
            storeIcon.setImage(ImageEncryption.decodeImage(store));
        }
    }

    @FXML
    void setOnline(ActionEvent event) {
        Firestore db = FirestoreClient.getFirestore();

        // Define the document path for the store where you want to update the "online" field
        String storeDocumentPath = "stores/"+UserSingleton.getInstance().getUser().getStoreID(); // Change to your store's document path

        // Retrieve the existing store document
        DocumentReference storeDocRef = db.document(storeDocumentPath);
        DocumentSnapshot storeDocument = null;
        try {
            storeDocument = storeDocRef.get().get();
            // Check if the store document exists
            if (storeDocument.exists()) {
                // Extract the existing data as a map
                Map<String, Object> existingStoreData = storeDocument.getData();

                if(onlineToggle.isSelected()){
                    onlineIMG.setImage(imgOnline);
                    onlineToggle.setText("Online");
                    // Update the "online" field to false
                    existingStoreData.put("online", true);
                    onlineToggle.setSelected(true);
                }
                else {
                    onlineIMG.setImage(imgOffline);
                    onlineToggle.setText("Offline");
                    existingStoreData.put("online", false);
                    onlineToggle.setSelected(false);
                }


                // Update the Firestore document with the modified data
                ApiFuture<WriteResult> future = storeDocRef.set(existingStoreData, SetOptions.merge());
                WriteResult writeResult = future.get();
                System.out.println("Store document updated. Document updated at: " + writeResult.getUpdateTime());
            } else {
                System.out.println("Store document does not exist: " + storeDocumentPath);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }



    }

}
