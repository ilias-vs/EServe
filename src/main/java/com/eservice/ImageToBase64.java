package com.eservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageToBase64 {
    public static void main(String[] args) {
        // Step 1: Read the image file and convert it to a byte array
        byte[] imageBytes = null;
        try {
            File imageFile = new File("C:\\working directory\\EService\\src\\main\\resources\\com\\eservice\\Images\\Eservice.png"); // Replace with the path to your image file
            FileInputStream fileInputStream = new FileInputStream(imageFile);
            imageBytes = new byte[(int) imageFile.length()];
            fileInputStream.read(imageBytes);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Step 2: Encode the byte array to a Base64 string
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // Step 3: Store the Base64 string in your database
        // You would typically use database-specific code to perform this step.
        // Here's a simple print statement to show the result.
        System.out.println("Base64 Image:\n" + base64Image);
    }
}
