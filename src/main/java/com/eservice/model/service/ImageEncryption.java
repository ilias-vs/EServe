package com.eservice.model.service;

import com.eservice.model.IStore;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageEncryption {
    public static Image decodeImage(IStore store){
        // Assuming you have retrieved the Base64 string from the database
        String base64ImageString = store.getImageData();

        // Step 2: Decode the Base64 string back into a byte array
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageString);

        // Step 3: Create an Image object from the byte array
        Image image = new Image(new ByteArrayInputStream(imageBytes));
        return image;
    }

    public static String encodeImage(File imageFile){
        byte[] imageBytes = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(imageFile);
            imageBytes = new byte[(int) imageFile.length()];
            fileInputStream.read(imageBytes);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Step 2: Encode the byte array to a Base64 string
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // Step 3: Store the Base64 string in your database
        // You would typically use database-specific code to perform this step.
        // Here's a simple print statement to show the result.
        return base64Image;
    }
}
